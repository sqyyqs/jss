package com.sqy.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.task.TaskStatus;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class TaskDto {
    @Nullable
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @Nullable
    @JsonProperty("description")
    private String description;

    @JsonProperty("performer_id")
    private Long performerId;

    @JsonProperty("estimated_hours")
    private Long estimatedHours;

    @JsonProperty("deadline")
    private LocalDateTime deadline;

    @JsonProperty("status")
    private TaskStatus status;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    @JsonProperty("last_update_date")
    private LocalDateTime lastUpdateDate;


    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskDto(@Nullable Long id, String name, @Nullable String description, Long performerId,
                   Long estimatedHours, LocalDateTime deadline, TaskStatus status, Long authorId,
                   LocalDateTime creationDate, LocalDateTime lastUpdateDate
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.performerId = performerId;
        this.estimatedHours = estimatedHours;
        this.deadline = deadline;
        this.status = status;
        this.authorId = authorId;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
    }

}
