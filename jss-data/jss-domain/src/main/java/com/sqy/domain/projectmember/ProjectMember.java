package com.sqy.domain.projectmember;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.project.Project;
import com.sqy.domain.task.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "project_member")
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private Long projectMemberId;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectMemberRole projectMemberRole;

    @OneToMany(mappedBy = "author")
    private Set<Task> authorTasks;

    @OneToMany(mappedBy = "performer")
    private Set<Task> performerTasks;

    @Override
    public String toString() {
        return "ProjectMember{" +
                "projectMemberId=" + projectMemberId +
                ", projectMemberRole=" + projectMemberRole +
                '}';
    }
}
