package com.sqy.dto.projectmember;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.projectmember.ProjectMemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

import static java.util.Objects.requireNonNull;

@Data
@Builder
public class ProjectMemberDto {
    @Nullable
    @JsonProperty("id")
    @Schema(description = "Идентификационный номер(необязательно).")
    private Long id;

    @JsonProperty("project_id")
    @Schema(description = "Идентификационный номер проекта.")
    private Long projectId;

    @JsonProperty("employee_id")
    @Schema(description = "Идентификационный номер сотрудника.")
    private Long employeeId;

    @JsonProperty("project_member_role")
    @Schema(description = "Роль сотрудника в проекте.")
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
