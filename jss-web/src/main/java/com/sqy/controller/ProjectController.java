package com.sqy.controller;

import com.sqy.domain.project.Project;
import com.sqy.dto.ProjectDto;
import com.sqy.service.ProjectService;

import java.io.IOException;
import java.util.List;

public class ProjectController {
    private final ProjectService projectService;

    public ProjectController() {
        this.projectService = new ProjectService();
    }

    public void save(ProjectDto projectDto) throws IOException {
        projectService.save(projectDto);
    }

    public void delete(Long id) throws IOException {
        projectService.delete(id);
    }

    public List<Project> getAll() throws IOException {
        return projectService.getAll();
    }

    public Project getById(Long id) throws IOException {
        return projectService.get(id);
    }

    public void update(ProjectDto projectDto) throws IOException {
        projectService.update(projectDto);
    }
}
