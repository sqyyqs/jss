package com.sqy.service;

import com.sqy.domain.project.Project;
import com.sqy.dto.project.ProjectDto;
import com.sqy.mapper.Mapper;
import com.sqy.repository.ProjectRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final Mapper<ProjectDto, Project> projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, Mapper<ProjectDto, Project> projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public List<ProjectDto> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public ProjectDto getById(Long id) {
        return projectRepository.findById(id)
                .stream()
                .map(projectMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public void save(ProjectDto projectDto) {
        projectRepository.save(projectMapper.getModelFromDto(projectDto));
    }

    public void update(ProjectDto projectDto) {
        if (!projectRepository.existsById(projectDto.id())) {
            throw new IllegalArgumentException();
        }
        projectRepository.save(projectMapper.getModelFromDto(projectDto));
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public List<ProjectDto> search() {
        return Collections.emptyList();
        // TODO: 17.05.2023
    }
}
