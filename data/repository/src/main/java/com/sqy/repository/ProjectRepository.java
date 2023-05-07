package com.sqy.repository;

import com.sqy.domain.Project;

public class ProjectRepository {
    public Project fromString(String string) {
        return new Project(string);
    }
}