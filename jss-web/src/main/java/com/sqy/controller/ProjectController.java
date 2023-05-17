package com.sqy.controller;

import com.sqy.domain.project.Project;
import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectSearchParametersDto;
import com.sqy.service.ProjectService;

import java.io.IOException;
import java.util.List;

public class ProjectController {
    private final ProjectService projectService;

    public ProjectController() throws IOException {
        this.projectService = new ProjectService();
    }

    public void save(ProjectDto projectDto) {
        projectService.save(projectDto);
    }

    public void delete(Long id) {
        projectService.delete(id);
    }

    public List<Project> getAll() {
        return projectService.getAll();
    }

    public Project getById(Long id) {
        return projectService.get(id);
    }

    public void update(ProjectDto projectDto) {
        projectService.update(projectDto);
    }

    public List<Project> search(ProjectSearchParametersDto projectSearchParametersDto) {
        return projectService.search(projectSearchParametersDto);
    }
}
