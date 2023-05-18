package com.sqy.mapper;

import com.sqy.domain.project.Project;
import com.sqy.dto.project.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper implements Mapper<ProjectDto, Project> {
    @Override
    public Project getModelFromDto(ProjectDto projectDto) {
        Project project = new Project();
        project.setId(projectDto.id());
        project.setName(projectDto.name());
        project.setDescription(projectDto.description());
        project.setProjectStatus(projectDto.projectStatus());
        return project;
    }

    @Override
    public ProjectDto getDtoFromModel(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getCode(),
                project.getName(),
                project.getDescription(),
                project.getProjectStatus()
        );
    }
}
