package com.sqy.dto.projectmember;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.projectmember.ProjectMemberRole;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static java.util.Objects.requireNonNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ProjectMemberDto {
    @Nullable
    @JsonProperty("id")
    private Long id;

    @JsonProperty("project_id")
    private Long projectId;

    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("project_member_role")
    private ProjectMemberRole projectMemberRole;

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ProjectMemberDto(@Nullable Long id, Long projectId, Long employeeId, ProjectMemberRole projectMemberRole) {
        this.id = id;
        this.projectId = requireNonNull(projectId, "ProjectId can't be null.");
        this.employeeId = requireNonNull(employeeId, "EmployeeId can't be null.");
        this.projectMemberRole = requireNonNull(projectMemberRole, "ProjectMemberRole can't be null.");
    }

}
