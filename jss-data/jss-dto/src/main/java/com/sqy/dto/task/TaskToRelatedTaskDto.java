package com.sqy.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskToRelatedTaskDto {
    @Nullable
    @JsonProperty("relationship_id")
    @Schema(description = "Идентификационный номер отношения, будет проигнорирован.")
    Long relationshipId;

    @JsonProperty("task_id")
    @Schema(description = "Идентификационный номер задачи, к которой будет линковка.")
    long taskId;

    @JsonProperty("related_task_id")
    @Schema(description = "Идентификационный номер задачи, которая линкуется.")
    long relatedTaskId;

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public TaskToRelatedTaskDto(@Nullable Long relationshipId, long taskId, long relatedTaskId) {
        this.relationshipId = relationshipId;
        this.taskId = taskId;
        this.relatedTaskId = relatedTaskId;
    }
}
