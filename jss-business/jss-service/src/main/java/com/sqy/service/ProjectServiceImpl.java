package com.sqy.service;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectStatus;
import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectNewStatusDto;
import com.sqy.dto.project.ProjectSearchDto;
import com.sqy.mapper.ProjectMapper;
import com.sqy.repository.ProjectRepository;
import com.sqy.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.mapper.ProjectMapper.getModelFromDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectDto> getAll() {
        log.info("Invoke getAll().");
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public ProjectDto getById(Long id) {
        log.info("Invoke getById({}).", id);
        return projectRepository.findById(id)
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public Long save(ProjectDto projectDto) {
        log.info("Invoke save({}).", projectDto);
        if (projectRepository.existsByCode(projectDto.getCode())) {
            return null;
        }
        if (projectDto.getId() != null) {
            projectDto.setId(null);
        }
        return projectRepository.save(getModelFromDto(projectDto)).getProjectId();
    }

    public boolean update(ProjectDto projectDto) {
        log.info("Invoke update({}).", projectDto);
        if (projectDto.getId() == null || projectDto.getCode() == null
                || projectRepository.existsByCode(projectDto.getCode())) {
            return false;
        }
        Project project = projectRepository.findById(projectDto.getId()).orElse(null);
        if (project == null) {
            return false;
        }
        Project modelFromDto = getModelFromDto(projectDto);
        modelFromDto.setProjectStatus(project.getProjectStatus());

        projectRepository.save(modelFromDto);
        return true;
    }

    public boolean updateState(ProjectNewStatusDto projectNewStatusDto) {
        log.info("Invoke updateState({}).", projectNewStatusDto);
        Project project = projectRepository.findById(projectNewStatusDto.id()).orElse(null);
        if (project == null) {
            return false;
        }
        switch (project.getProjectStatus()) {
            case DRAFT -> {
                if (projectNewStatusDto.newProjectStatus() != ProjectStatus.IN_PROGRESS) {
                    return false;
                }
            }
            case IN_PROGRESS -> {
                if (projectNewStatusDto.newProjectStatus() != ProjectStatus.IN_TESTING) {
                    return false;
                }
            }
            case IN_TESTING -> {
                if (projectNewStatusDto.newProjectStatus() != ProjectStatus.COMPLETED) {
                    return false;
                }
            }
            case COMPLETED -> {
                return false;
            }
        }
        project.setProjectStatus(projectNewStatusDto.newProjectStatus());
        projectRepository.save(project);
        return true;
    }

    public List<ProjectDto> search(ProjectSearchDto projectSearchDto) {
        log.info("Invoke search({}).", projectSearchDto);
        return projectRepository
                .findByFieldsContainingWithStatuses(projectSearchDto.value(), projectSearchDto.statuses())
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .toList();
    }
}
