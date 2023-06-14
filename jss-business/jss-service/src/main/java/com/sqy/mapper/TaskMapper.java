package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.task.Task;
import com.sqy.dto.task.TaskDto;

public class TaskMapper {

    public static Task getModelFromDto(TaskDto taskDto) {
        Task task = new Task();
        task.setTaskId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setEstimatedHours(taskDto.getEstimatedHours());
        task.setDeadline(taskDto.getDeadline());
        task.setStatus(taskDto.getStatus());
        task.setCreationDate(null);
        task.setLastUpdateDate(null);

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
        taskDto.setEstimatedHours(task.getEstimatedHours());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setStatus(task.getStatus());
        taskDto.setAuthorId(task.getAuthor().getProjectMemberId());
        taskDto.setCreationDate(task.getCreationDate());
        taskDto.setLastUpdateDate(task.getLastUpdateDate());

        return taskDto;
    }
}
