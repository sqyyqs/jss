package com.sqy.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.project.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public record ProjectSearchDto(
        @JsonProperty("value")
        @Schema(description = "Значение для поиска в полях.")
        String value,

        @JsonProperty("statuses")
        @Schema(description = "Набор допустимых для поиска статусов проектов.")
        Set<ProjectStatus> statuses
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectSearchDto(String value, Set<ProjectStatus> statuses) {
        this.value = requireNonNull(value);
        this.statuses = requireNonNull(statuses);
    }
}
