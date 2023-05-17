package com.sqy.domain.task;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.projectmember.ProjectMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Task {
    private Long id;
    private String name;
    /*todo @Nullable*/ private String description;
    private Employee performer;
    private Long estimatedHours;
    private LocalDateTime deadline;
    private TaskStatus status;
    private ProjectMember author;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
}
