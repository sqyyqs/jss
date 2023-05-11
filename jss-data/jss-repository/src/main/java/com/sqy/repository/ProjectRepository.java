package com.sqy.repository;

import com.sqy.domain.Project;

import java.io.IOException;
import java.util.List;

public interface ProjectRepository {
    void create(Project project) throws IOException;

    void update(Project project) throws IOException;

    Project getById(Long id) throws IOException;

    List<Project> getAll() throws IOException;

    void deleteById(Long id) throws IOException;
}
