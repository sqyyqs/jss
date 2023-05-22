package com.sqy.dto;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.task.TaskStatus;

import java.time.LocalDateTime;


public record TaskDto(
        Long id,
        String name,
        String description,
        Employee performer,
        Long estimatedHours,
        LocalDateTime deadline,
        TaskStatus status,
        ProjectMember author,
        LocalDateTime creationDate,
        LocalDateTime lastUpdateDate
) {
}
