package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.project.Project;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.dto.projectmember.ProjectMemberDto;

public class ProjectMemberMapper {
    public static ProjectMember getModelFromDto(ProjectMemberDto projectMemberDto) {
        return ProjectMember.builder()
                .projectMemberId(projectMemberDto.getId())
                .projectMemberRole(projectMemberDto.getProjectMemberRole())
                .project(Project.builder().projectId(projectMemberDto.getProjectId()).build())
                .employee(Employee.builder().employeeId(projectMemberDto.getEmployeeId()).build())
                .build();
    }

    public static ProjectMemberDto getDtoFromModel(ProjectMember projectMember) {
        return ProjectMemberDto.builder()
                .id(projectMember.getProjectMemberId())
                .projectId(projectMember.getProject().getProjectId())
                .employeeId(projectMember.getEmployee().getEmployeeId())
                .projectMemberRole(projectMember.getProjectMemberRole())
                .build();
    }
}
