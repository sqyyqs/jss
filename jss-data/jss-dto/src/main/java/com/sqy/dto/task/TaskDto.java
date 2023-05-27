package com.sqy.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.task.TaskStatus;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaskDto {
    @Nullable
    private Long id;

    private String name;

    @Nullable
    private String description;

    private Long performerId;

    private Long estimatedHours;

    private LocalDateTime deadline;

    private TaskStatus status;

    private Long authorId;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdateDate;


    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskDto(
            @JsonProperty("id") @Nullable Long id,
            @JsonProperty("name") String name,
            @JsonProperty("description") @Nullable String description,
            @JsonProperty("performer_id") Long performerId,
            @JsonProperty("estimated_hours") Long estimatedHours,
            @JsonProperty("deadline") LocalDateTime deadline,
            @JsonProperty("status") TaskStatus status,
            @JsonProperty("author_id") Long authorId,
            @JsonProperty("creation_date") LocalDateTime creationDate,
            @JsonProperty("last_update_date") LocalDateTime lastUpdateDate
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
