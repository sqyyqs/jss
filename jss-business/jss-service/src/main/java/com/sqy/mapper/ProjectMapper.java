package com.sqy.mapper;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectStatus;
import com.sqy.dto.project.ProjectDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProjectMapper {

    public static Project getModelFromDto(ProjectDto projectDto) {
        log.info("Invoke getModelFromDto({}).", projectDto);
        return Project.builder().projectId(projectDto.getId())
                .code(projectDto.getCode())
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .projectStatus(ProjectStatus.DRAFT).build();
    }

    public static ProjectDto getDtoFromModel(Project project) {
        log.info("Invoke getDtoFromModel({}).", project);
        return ProjectDto.builder().id(project.getProjectId())
                .code(project.getCode())
                .name(project.getName())
                .description(project.getDescription())
                .projectStatus(project.getProjectStatus()).build();
    }
}
