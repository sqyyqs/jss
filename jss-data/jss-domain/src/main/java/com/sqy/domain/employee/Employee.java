package com.sqy.domain.employee;

import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.task.Task;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    @Nullable
    private String middleName;

    @Column(name = "position")
    @Nullable
    private String position;

    @Column(name = "account")
    @Nullable
    private Object account;

    @Column(name = "email")
    @Nullable
    private String email;

    @Column(name = "status", nullable = false)
    private EmployeeStatus status;

    @OneToMany(mappedBy = "employee")
    private Set<ProjectMember> projectMembers;

    @OneToOne(mappedBy = "performer")
    private Task task;
}
