package com.sqy.domain.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
