package com.sqy.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;


@Data
@NoArgsConstructor
@Builder
public class TaskDto {
    @Nullable
    @JsonProperty("id")
    @Schema(description = "Идентификационный номер(обязателен прим update, будет проигнорирован при save).")
    private Long id;

    @JsonProperty("name")
    @Schema(description = "Название задачи.")
    private String name;

    @Nullable
    @JsonProperty("description")
    @Schema(description = "Описание задачи(необязательно).")
    private String description;

    @JsonProperty("performer_id")
    @Schema(description = "Идентификационный номер исполнителя.")
    private Long performerId;

    @JsonProperty("estimated_hours")
    @Schema(description = "Оценка в количестве часов, предположительное количество необходимых для выполнения часов.")
    private Long estimatedHours;

    @JsonProperty("deadline")
    @Schema(description = "Крайний срок в формате ISO 8601.")
    private LocalDateTime deadline;

    @JsonProperty("status")
    @Schema(description = "Статус задачи(новая / в процессе / завершена / закрыта).")
    private TaskStatus status;

    @JsonProperty("author_id")
    @Schema(description = "Идентификатор автора(ProjectMember).")
    private Long authorId;

    @Nullable
    @JsonProperty("creation_date")
    @Schema(description = "Дата создания, будет проигнорирована, заполняется автоматически на стороне бд(необязательно).")
    private LocalDateTime creationDate;

    @Nullable
    @JsonProperty("last_update_date")
    @Schema(description = "Дата последнего изменения, будет проигнорирована," +
            " заполняется автоматически на стороне бд(необязательно).")
    private LocalDateTime lastUpdateDate;


    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskDto(@Nullable Long id, String name, @Nullable String description, Long performerId,
                   Long estimatedHours, LocalDateTime deadline, TaskStatus status, Long authorId,
                   @Nullable LocalDateTime creationDate, @Nullable LocalDateTime lastUpdateDate
    ) {
        this.id = id;
        this.name = requireNonNull(name);
        this.description = description;
        this.performerId = requireNonNull(performerId);
        this.estimatedHours = requireNonNull(estimatedHours);
        this.deadline = requireNonNull(deadline);
        this.status = requireNonNull(status);
        this.authorId = requireNonNull(authorId);
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
    }

}
