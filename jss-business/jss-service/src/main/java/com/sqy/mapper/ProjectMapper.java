package com.sqy.mapper;

import com.sqy.domain.project.Project;
import com.sqy.dto.project.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public Project fromDto(ProjectDto dto) {
        return new Project(
                dto.id(),
                dto.code(),
                dto.name(),
                dto.description(),
                dto.projectStatus()
        );
    }
}
