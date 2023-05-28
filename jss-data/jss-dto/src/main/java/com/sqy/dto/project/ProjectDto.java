package com.sqy.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.project.ProjectStatus;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static java.util.Objects.requireNonNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjectDto {
    @Nullable
    private Long id;
    private String code;
    private String name;
    @Nullable
    private String description;
    @Nullable
    private ProjectStatus projectStatus;

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectDto(@JsonProperty("id") @Nullable Long id,
                      @JsonProperty("code") String code,
                      @JsonProperty("name") String name,
                      @JsonProperty("description") @Nullable String description,
                      @JsonProperty("project_status") @Nullable ProjectStatus projectStatus) {
        this.id = id;
        this.code = requireNonNull(code);
        this.name = requireNonNull(name);
        this.description = description;
        this.projectStatus = projectStatus;
    }
}
