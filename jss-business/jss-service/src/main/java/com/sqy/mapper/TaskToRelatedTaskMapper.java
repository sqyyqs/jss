package com.sqy.mapper;

import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskToRelatedTask;
import com.sqy.dto.task.TaskToRelatedTaskDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TaskToRelatedTaskMapper {
    public static TaskToRelatedTask getModelFromDto(TaskToRelatedTaskDto taskToRelatedTaskDto) {
        log.info("Invoke getModelFromDto({}).", taskToRelatedTaskDto);
        return TaskToRelatedTask.builder().relationshipId(taskToRelatedTaskDto.getRelationshipId())
                .relatedTask(Task.builder().taskId(taskToRelatedTaskDto.getRelatedTaskId()).build())
                .task(Task.builder().taskId(taskToRelatedTaskDto.getTaskId()).build()).build();
    }

    public static TaskToRelatedTaskDto getDtoFromModel(TaskToRelatedTask taskToRelatedTask) {
        log.info("Invoke getDtoFromModel({}).", taskToRelatedTask);
        return new TaskToRelatedTaskDto(taskToRelatedTask.getRelationshipId(),
                taskToRelatedTask.getTask().getTaskId(),
                taskToRelatedTask.getRelatedTask().getTaskId());
    }
}
