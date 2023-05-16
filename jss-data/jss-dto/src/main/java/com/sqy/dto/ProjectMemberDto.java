package com.sqy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.management.relation.Role;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProjectMemberDto {
    private Long id;
    private ProjectDto project;
    private EmployeeDto employee;
    private Role role;
}
