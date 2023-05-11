package com.sqy.domain;

public class ProjectMember {
    private Long id;
    private Project project;
    private Employee employee;
    private Role role;

    public ProjectMember(
            Employee employee,
            Role role

    ) {
        this.employee = employee;
        this.role = role;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
        return "ProjectMember{" +
                "id=" + id +
                ", project=" + project +
                ", employee=" + employee +
                ", role=" + role +
                '}';
    }

    public enum Role {
        LEAD,
        ANALYST,
        DEVELOPER,
        TESTER
    }
}
