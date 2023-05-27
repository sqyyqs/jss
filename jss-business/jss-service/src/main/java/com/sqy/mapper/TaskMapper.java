package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.task.Task;
import com.sqy.dto.task.TaskDto;

import java.time.LocalDateTime;

public class TaskMapper {

    public static Task getModelFromDto(TaskDto taskDto) {
        Task task = new Task();
        task.setTaskId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setEstimatedHours(taskDto.getEstimatedHours());
        task.setDeadline(taskDto.getDeadline());
        task.setStatus(taskDto.getStatus());
        task.setCreationDate(LocalDateTime.of(2004, 7, 11, 20, 20));
        task.setLastUpdateDate(LocalDateTime.of(2004, 7, 11, 20, 20));

        ProjectMember author = new ProjectMember();
        author.setProjectMemberId(taskDto.getAuthorId());
        task.setAuthor(author);

        Employee employee = new Employee();
        employee.setEmployeeId(taskDto.getPerformerId());
        task.setPerformer(employee);
        return task;
    }

    public static TaskDto getDtoFromModel(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getTaskId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setPerformerId(task.getPerformer().getEmployeeId());
        taskDto.setEstimatedHours(task.getTaskId());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setStatus(task.getStatus());
        taskDto.setAuthorId(task.getAuthor().getProjectMemberId());
        taskDto.setCreationDate(task.getCreationDate());
        taskDto.setLastUpdateDate(task.getLastUpdateDate());
        return taskDto;
    }
}
