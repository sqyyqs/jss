package com.sqy.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.project.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

public record ProjectNewStatusDto(
        @JsonProperty("id")
        @Schema(description = "Идентификационный номер.")
        long id,
        @JsonProperty("new_status")
        @Schema(description = "Новый статус.")
        ProjectStatus newProjectStatus
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectNewStatusDto(long id, ProjectStatus newProjectStatus) {
        this.id = id;
        this.newProjectStatus = Objects.requireNonNull(newProjectStatus);
    }
}
