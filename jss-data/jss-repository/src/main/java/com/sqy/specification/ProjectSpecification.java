package com.sqy.specification;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.employee.EmployeeStatus;
import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectStatus;
import com.sqy.domain.projectmember.ProjectMember;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {
    public static Specification<Project> hasCode(String code) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("code"), code);
    }

    public static Specification<Project> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Project> hasDescription(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("description"), description);
    }

    public static Specification<Project> hasProjectStatus(ProjectStatus projectStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("projectStatus"), projectStatus.name());
    }

    public static Specification<Project> hasEmployeeFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
            Join<ProjectMember, Employee> employeeJoin = memberJoin.join("projectMembers");
            return criteriaBuilder.equal(employeeJoin.get("firstName"), firstName);
        };
    }

    public static Specification<Project> hasEmployeeLastName(String lastName) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
            Join<ProjectMember, Employee> employeeJoin = memberJoin.join("projectMembers");
            return criteriaBuilder.equal(employeeJoin.get("lastName"), lastName);
        };
    }

    public static Specification<Project> hasEmployeeMiddleName(String middleName) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
            Join<ProjectMember, Employee> employeeJoin = memberJoin.join("projectMembers");
            return criteriaBuilder.equal(employeeJoin.get("middleName"), middleName);
        };
    }

    public static Specification<Project> hasEmployeePosition(String position) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
            Join<ProjectMember, Employee> employeeJoin = memberJoin.join("projectMembers");
            return criteriaBuilder.equal(employeeJoin.get("position"), position);
        };
    }

    public static Specification<Project> hasEmployeeAccount(Object account) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
            Join<ProjectMember, Employee> employeeJoin = memberJoin.join("projectMembers");
            return criteriaBuilder.equal(employeeJoin.get("account"), account.toString());
        };
    }

    public static Specification<Project> hasEmployeeEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
            Join<ProjectMember, Employee> employeeJoin = memberJoin.join("projectMembers");
            return criteriaBuilder.equal(employeeJoin.get("email"), email);
        };
    }

    public static Specification<Project> hasEmployeeStatus(EmployeeStatus employeeStatus) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, ProjectMember> memberJoin = root.join("projectMembers");
            Join<ProjectMember, Employee> employeeJoin = memberJoin.join("projectMembers");
            return criteriaBuilder.equal(employeeJoin.get("employeeStatus"), employeeStatus);
        };
    }
}
