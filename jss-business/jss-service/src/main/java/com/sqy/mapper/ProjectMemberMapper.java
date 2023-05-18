package com.sqy.mapper;

import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.dto.ProjectMemberDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapper implements Mapper<ProjectMemberDto, ProjectMember> {
    @Override
    public ProjectMember getModelFromDto(ProjectMemberDto projectMemberDto) {
        ProjectMember projectMember = new ProjectMember();
        projectMember.setId(projectMemberDto.id());
        projectMember.setProject(projectMemberDto.project());
        projectMember.setEmployee(projectMemberDto.employee());
        projectMember.setProjectMemberRole(projectMemberDto.projectMemberRole());
        return projectMember;
    }

    @Override
    public ProjectMemberDto getDtoFromModel(ProjectMember projectMember) {
        return new ProjectMemberDto(
                projectMember.getId(),
                projectMember.getProject(),
                projectMember.getEmployee(),
                projectMember.getProjectMemberRole()
        );
    }
}
