package com.sqy.controller;

import com.sqy.dto.project.ProjectDto;
import com.sqy.service.ProjectService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    public List<ProjectDto> getAll() {
        return projectService.getAll();
    }

    @Nullable
    public ProjectDto getById(Long id) {
        return projectService.getById(id);
    }

    public void save(ProjectDto projectDto) {
        projectService.save(projectDto);
    }

    public void update(ProjectDto projectDto) {
        projectService.update(projectDto);
    }

    public void delete(Long id) {
        projectService.delete(id);
    }

    public List<ProjectDto> search() {
        return projectService.search();
    }
}
