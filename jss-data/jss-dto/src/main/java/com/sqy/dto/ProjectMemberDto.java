package com.sqy.dto;

import com.sqy.domain.ProjectMember;

public class ProjectMemberDto {
    private Long id;
    private ProjectDto project;
    private EmployeeDto employee;
    private ProjectMember.Role role;

    public ProjectMemberDto(Long id,
                            ProjectDto project,
                            EmployeeDto employee,
                            ProjectMember.Role role) {
        this.id = id;
        this.project = project;
        this.employee = employee;
        this.role = role;
    }

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    public ProjectMember.Role getRole() {
        return role;
    }

    public void setRole(ProjectMember.Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProjectMemberDto{" +
                "id=" + id +
                ", project=" + project +
                ", employee=" + employee +
                ", role=" + role +
                '}';
    }
}
