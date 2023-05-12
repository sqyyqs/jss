package com.sqy.dto;

import com.sqy.domain.project.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProjectDto {
    private Long id;
    private String code;
    private String name;
    /*todo @Nullable*/ private String description;
    private ProjectStatus projectStatus;
}
