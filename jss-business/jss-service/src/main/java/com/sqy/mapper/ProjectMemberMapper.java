package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.project.Project;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.dto.ProjectMemberDto;

public class ProjectMemberMapper {
    public static ProjectMember getModelFromDto(ProjectMemberDto projectMemberDto) {
        ProjectMember projectMember = new ProjectMember();

        projectMember.setProjectMemberId(projectMemberDto.getId());
        projectMember.setProjectMemberRole(projectMemberDto.getProjectMemberRole());

        Project project = new Project();
        project.setProjectId(projectMemberDto.getProjectId());
        projectMember.setProject(project);

        Employee employee = new Employee();
        employee.setEmployeeId(projectMemberDto.getEmployeeId());
        projectMember.setEmployee(employee);

        return projectMember;
    }

    public static ProjectMemberDto getDtoFromModel(ProjectMember projectMember) {
        return new ProjectMemberDto(
                projectMember.getProjectMemberId(),
                projectMember.getProject().getProjectId(),
                projectMember.getEmployee().getEmployeeId(),
                projectMember.getProjectMemberRole()
        );
    }
}
