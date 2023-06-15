package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.project.Project;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.dto.projectmember.ProjectMemberDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProjectMemberMapper {
    public static ProjectMember getModelFromDto(ProjectMemberDto projectMemberDto) {
        log.info("Invoke getModelFromDto({}).", projectMemberDto);

        return ProjectMember.builder()
                .projectMemberId(projectMemberDto.getId())
                .projectMemberRole(projectMemberDto.getProjectMemberRole())
                .project(Project.builder().projectId(projectMemberDto.getProjectId()).build())
                .employee(Employee.builder().employeeId(projectMemberDto.getEmployeeId()).build())
                .build();
    }

    public static ProjectMemberDto getDtoFromModel(ProjectMember projectMember) {
        log.info("Invoke getDtoFromModel({}).", projectMember);
        return ProjectMemberDto.builder()
                .id(projectMember.getProjectMemberId())
                .projectId(projectMember.getProject().getProjectId())
                .employeeId(projectMember.getEmployee().getEmployeeId())
                .projectMemberRole(projectMember.getProjectMemberRole())
                .build();
    }
}
