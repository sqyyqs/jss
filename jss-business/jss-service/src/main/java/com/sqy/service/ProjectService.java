package com.sqy.service;

import com.sqy.domain.project.Project;
import com.sqy.dto.ProjectDto;
import com.sqy.mapper.ProjectMapper;
import com.sqy.repository.ProjectDataStorage;
import com.sqy.repository.ProjectRepository;

import java.io.IOException;
import java.util.List;

public class ProjectService {
    ProjectRepository projectRepository = new ProjectDataStorage();
    ProjectMapper projectMapper = new ProjectMapper();

    public void save(ProjectDto projectDto) throws IOException {
        projectRepository.create(projectMapper.fromDto(projectDto));
    }

    public void delete(Long id) throws IOException {
        projectRepository.deleteById(id);
    }

    public void update(ProjectDto projectDto) throws IOException {
        projectRepository.update(projectMapper.fromDto(projectDto));
    }

    public List<Project> getAll() throws IOException {
        return projectRepository.getAll();
    }

    public Project get(Long id) throws IOException {
        return projectRepository.getById(id);
    }
}
