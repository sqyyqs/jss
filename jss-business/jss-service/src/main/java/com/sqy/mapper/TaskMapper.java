package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskStatus;
import com.sqy.dto.task.TaskDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
                .performer(Employee.builder().employeeId(taskDto.getPerformerId()).build()).build();
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
                .performerId(task.getPerformer().getEmployeeId())
                .creationDate(task.getCreationDate())
                .lastUpdateDate(task.getLastUpdateDate())
                .build();
    }
}
