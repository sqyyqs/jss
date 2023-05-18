package com.sqy.domain.projectmember;

import jakarta.annotation.Nullable;

import java.util.Arrays;

public enum ProjectMemberRole {
    LEAD,
    ANALYST,
    DEVELOPER,
    TESTER;

    @Nullable
    public static ProjectMemberRole getProjectMemberRole(@Nullable String projectMemberRoleName) {
        if (projectMemberRoleName == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(role -> role.name().equals(projectMemberRoleName))
                .findAny()
                .orElse(null);
    }
}
