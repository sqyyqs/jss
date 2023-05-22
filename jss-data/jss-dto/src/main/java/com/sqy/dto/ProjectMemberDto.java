package com.sqy.dto;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.project.Project;
import com.sqy.domain.projectmember.ProjectMemberRole;

public record ProjectMemberDto(
        Long id,
        Project project,
        Employee employee,
        ProjectMemberRole projectMemberRole
) {
}
