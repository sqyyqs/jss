package com.sqy.domain.project;

public enum ProjectStatus {
    DRAFT("draft"),
    IN_PROGRESS("inProgress"),
    IN_TESTING("inTesting"),
    COMPLETED("completed");

    private final String statusString;

    ProjectStatus(String statusString) {
        this.statusString = statusString;
    }

    public String getStatusString() {
        return statusString;
    }
}
