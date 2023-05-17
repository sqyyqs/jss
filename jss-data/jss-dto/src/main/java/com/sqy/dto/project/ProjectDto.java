package com.sqy.dto.project;

import com.sqy.domain.project.ProjectStatus;

public record ProjectDto(
        Long id,
        String code,
        String name,
        String description,
        ProjectStatus projectStatus
) {
}
