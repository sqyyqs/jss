package com.sqy.domain.projectmember;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProjectMember {
    private Long id;
    private Project project;
    private Employee employee;
    private ProjectMemberRole projectMemberRole;
}
