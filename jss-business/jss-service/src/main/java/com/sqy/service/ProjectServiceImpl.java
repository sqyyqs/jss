package com.sqy.service;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectStatus;
import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectNewStatusDto;
import com.sqy.dto.project.ProjectSearchDto;
import com.sqy.mapper.ProjectMapper;
import com.sqy.repository.ProjectRepository;
import com.sqy.service.interfaces.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.mapper.ProjectMapper.getModelFromDto;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectDto> getAll() {
        logger.info("Invoke getAll().");
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public ProjectDto getById(Long id) {
        logger.info("Invoke getById({}).", id);
        return projectRepository.findById(id)
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public boolean save(ProjectDto projectDto) {
        logger.info("Invoke save({}).", projectDto);
        if (projectRepository.existsByCode(projectDto.getCode())) {
            return false;
        }
        if (projectDto.getId() != null) {
            projectDto.setId(null);
        }
        projectRepository.save(getModelFromDto(projectDto));
        return true;
    }

    public boolean update(ProjectDto projectDto) {
        logger.info("Invoke update({}).", projectDto);
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
        logger.info("Invoke updateState({}).", projectNewStatusDto);
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
        return projectRepository
                .findByFieldsContainingWithStatuses(projectSearchDto.value(), projectSearchDto.statuses())
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .toList();
    }
}
