package com.sqy.domain.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
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
