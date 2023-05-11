package com.sqy.mapper;

import com.sqy.domain.Project;
import com.sqy.dto.ProjectDto;

public class ProjectMapper {
    public Project fromDto(ProjectDto dto) {
        return new Project(
                dto.getId(),
                dto.getCode(),
                dto.getName(),
                dto.getDescription(),
                dto.getProjectStatus()
        );
    }
}
