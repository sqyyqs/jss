package com.sqy.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.project.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@Data
@NoArgsConstructor
@Builder
public class ProjectDto {
    @Nullable
    @JsonProperty("id")
    @Schema(description = "Идентификационный номер(обязателен при update, будет проигнорирован при save).")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Уникальный код.")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Имя проекта.")
    private String name;

    @Nullable
    @JsonProperty("description")
    @Schema(description = "Описание проекта(необязательно).")
    private String description;

    @Nullable
    @JsonProperty("project_status")
    @Schema(description = "Статус проекта(черновик / в работе / в тестировании / завершен)(необязательно).")
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
