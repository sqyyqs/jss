package com.sqy.service;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectFile;
import com.sqy.domain.project.ProjectStatus;
import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectFileDto;
import com.sqy.dto.project.ProjectNewStatusDto;
import com.sqy.dto.project.ProjectSearchDto;
import com.sqy.mapper.ProjectFileMapper;
import com.sqy.mapper.ProjectMapper;
import com.sqy.repository.ProjectFileRepository;
import com.sqy.repository.ProjectRepository;
import com.sqy.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.sqy.mapper.ProjectMapper.getModelFromDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectFileRepository projectFileRepository;

    @Override
    public List<ProjectDto> getAll() {
        log.info("Invoke getAll().");
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    @Override
    public ProjectDto getById(Long id) {
        log.info("Invoke getById({}).", id);
        return projectRepository.findById(id)
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    @Nullable
    @Override
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

    @Override
    public boolean update(ProjectDto projectDto) {
        log.info("Invoke update({}).", projectDto);
        if (projectDto.getId() == null || projectRepository.existsByCodeAndProjectIdNot(projectDto.getCode(), projectDto.getId())) {
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

    @Override
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

    @Override
    public List<ProjectDto> search(ProjectSearchDto projectSearchDto) {
        log.info("Invoke search({}).", projectSearchDto);
        return projectRepository
                .findByFieldsContainingWithStatuses(projectSearchDto.value(), projectSearchDto.statuses())
                .stream()
                .map(ProjectMapper::getDtoFromModel)
                .toList();
    }

    @Override
    public boolean uploadFileToProjectId(MultipartFile file, long projectId) {
        log.info("Invoke uploadFileToProjectId({}, {}).", file, projectId);
        ProjectFile result = ProjectFileMapper.getFromMultipartFile(file, projectId);
        if (result == null) {
            return false;
        }
        try {
            projectFileRepository.save(result);
            return true;
        } catch (DataIntegrityViolationException e) {
            log.info("Invoke uploadFileToProjectId({}, {}) with exception.", file, projectId, e);
        }
        return false;
    }

    @Override
    public ProjectFileDto getFileFromRelatedProject(long projectId) {
        log.info("Invoke getFileFromRelatedProject({}).", projectId);
        return ProjectFileMapper.getDtoFromModel(projectFileRepository.getProjectFileByProject_ProjectId(projectId));
    }

    @Override
    public boolean deleteFileFromRelatedProject(long projectId) {
        log.info("Invoke deleteFileFromRelatedProject({}).", projectId);
        projectFileRepository.deleteProjectFileByProject_ProjectId(projectId);
        return true;
    }
}
