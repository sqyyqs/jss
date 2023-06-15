package com.sqy.mapper;

import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskStatus;
import com.sqy.dto.task.TaskDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TaskMapper {

    public static Task getModelFromDto(TaskDto taskDto) {
        log.info("Invoke getModelFromDto({}).", taskDto);
        return Task.builder().taskId(taskDto.getId())
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .estimatedHours(taskDto.getEstimatedHours())
                .deadline(taskDto.getDeadline())
                .status(TaskStatus.NEW)
                .author(ProjectMember.builder().projectMemberId(taskDto.getAuthorId()).build())
                .performer(taskDto.getPerformerId() == null ?
                        null : ProjectMember.builder().projectMemberId(taskDto.getPerformerId()).build()).build();
    }

    public static TaskDto getDtoFromModel(Task task) {
        log.info("Invoke getDtoFromModel({}).", task);
        return TaskDto.builder().id(task.getTaskId())
                .name(task.getName())
                .description(task.getDescription())
                .estimatedHours(task.getEstimatedHours())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .authorId(task.getAuthor().getProjectMemberId())
                .performerId(task.getPerformer() == null ? null : task.getPerformer().getProjectMemberId())
                .creationDate(task.getCreationDate())
                .lastUpdateDate(task.getLastUpdateDate())
                .build();
    }
}
