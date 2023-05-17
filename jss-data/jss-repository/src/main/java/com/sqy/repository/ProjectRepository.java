package com.sqy.repository;

import com.sqy.domain.project.Project;

import java.util.List;
import java.util.Map;

public interface ProjectRepository {
    void create(Project project);

    void update(Project project);

    Project getById(Long id);

    List<Project> getAll();

    void deleteById(Long id);

    List<Project> searchByProps(Map<String, Object> properties);
}
