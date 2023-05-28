package com.sqy.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.project.ProjectStatus;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public record ProjectSearchDto(
        String value,
        Set<ProjectStatus> statuses
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectSearchDto(@JsonProperty("value") String value,
                            @JsonProperty("statuses") Set<ProjectStatus> statuses) {
        this.value = requireNonNull(value);
        this.statuses = requireNonNull(statuses);
    }
}
