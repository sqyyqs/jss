package com.sqy.dto;

import com.sqy.domain.project.ProjectStatus;

public class ProjectDto {
    private Long id;
    private String code;
    private String name;
    /*todo @Nullable*/ private String description;
    private ProjectStatus projectStatus;

    public ProjectDto(Long id,
                      String code,
                      String name,
                      String description,
                      ProjectStatus projectStatus) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.projectStatus = projectStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectStatus=" + projectStatus +
                '}';
    }
}
