package com.sqy.domain.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskEmailInfo {
    private String to;
    private String performerFirstName;
    private String performerLastName;
    private String taskName;
    private String projectName;
    private LocalDateTime taskDeadline;
    private String authorFirstName;
    private String authorLastName;
}
