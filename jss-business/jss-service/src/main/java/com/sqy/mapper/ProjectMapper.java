package com.sqy.mapper;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectStatus;
import com.sqy.dto.project.ProjectDto;

public class ProjectMapper {

    public static Project getModelFromDto(ProjectDto projectDto) {
        Project project = new Project();
        project.setProjectId(projectDto.getId());
        project.setCode(projectDto.getCode());
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setProjectStatus(ProjectStatus.DRAFT);
        return project;
    }

    public static ProjectDto getDtoFromModel(Project project) {
        return new ProjectDto(
                project.getProjectId(),
                project.getCode(),
                project.getName(),
                project.getDescription(),
                project.getProjectStatus()
        );
    }
}
