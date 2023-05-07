package com.sqy.controller;

import com.sqy.domain.Project;
import com.sqy.services.ProjectService;

public class ProjectController {
    private final ProjectService projectService;

    public ProjectController() {
        this.projectService = new ProjectService();
    }

    public Project getProjectFromName(String name) {
        return projectService.fromName(name);
    }
}
