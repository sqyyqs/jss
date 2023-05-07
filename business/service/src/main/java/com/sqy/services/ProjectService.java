package com.sqy.services;

import com.sqy.domain.Project;
import com.sqy.repository.ProjectRepository;

public class ProjectService {
    ProjectRepository repository = new ProjectRepository();

    public Project fromName(String name) {
        return repository.fromString(name);
    }
}
