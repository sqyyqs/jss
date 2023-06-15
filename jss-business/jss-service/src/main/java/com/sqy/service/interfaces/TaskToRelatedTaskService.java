package com.sqy.service.interfaces;

import com.sqy.dto.task.TaskToRelatedTaskDto;

import java.util.List;

public interface TaskToRelatedTaskService {
    TaskToRelatedTaskDto getById(long relationshipId);

    List<TaskToRelatedTaskDto> getAllRelationshipsForTask(long taskId);

    Long addRelatedTask(TaskToRelatedTaskDto taskToRelatedTaskDto);

    boolean removeRelationshipById(long relationshipId);

    boolean removeAllRelationshipsForTask(long taskId);

}
