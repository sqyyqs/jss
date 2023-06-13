package com.sqy.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.project.ProjectStatus;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static java.util.Objects.requireNonNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class ProjectDto {
    @Nullable
    @JsonProperty("id")
    private Long id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @Nullable
    @JsonProperty("description")
    private String description;

    @Nullable
    @JsonProperty("project_status")
    private ProjectStatus projectStatus;

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectDto(@Nullable Long id, String code, String name,
                      @Nullable String description, @Nullable ProjectStatus projectStatus) {
        this.id = id;
        this.code = requireNonNull(code);
        this.name = requireNonNull(name);
        this.description = description;
        this.projectStatus = projectStatus;
    }
}
