package com.sqy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.projectmember.ProjectMemberRole;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static java.util.Objects.requireNonNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjectMemberDto {
    @Nullable
    private Long id;
    private Long projectId;
    private Long employeeId;
    private ProjectMemberRole projectMemberRole;

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectMemberDto(@JsonProperty("id") @Nullable Long id,
                            @JsonProperty("project_id") Long projectId,
                            @JsonProperty("employee_id") Long employeeId,
                            @JsonProperty("project_member_role") ProjectMemberRole projectMemberRole) {
        this.id = id;
        this.projectId = requireNonNull(projectId);
        this.employeeId = requireNonNull(employeeId);
        this.projectMemberRole = requireNonNull(projectMemberRole);
    }

}
