package com.sqy.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.task.TaskStatus;

import java.util.Objects;

public record TaskNewStatusDto(
        @JsonProperty("id") long id,
        @JsonProperty("new_task_status") TaskStatus newTaskStatus
) {
    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskNewStatusDto(long id, TaskStatus newTaskStatus) {
        this.id = id;
        this.newTaskStatus = Objects.requireNonNull(newTaskStatus);
    }
}
