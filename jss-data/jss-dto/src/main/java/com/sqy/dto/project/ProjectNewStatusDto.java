package com.sqy.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.project.ProjectStatus;

import java.util.Objects;

public record ProjectNewStatusDto(
        @JsonProperty("id") long id,
        @JsonProperty("new_status") ProjectStatus newProjectStatus
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectNewStatusDto(long id, ProjectStatus newProjectStatus) {
        this.id = id;
        this.newProjectStatus = Objects.requireNonNull(newProjectStatus);
    }
}
