package com.sqy.service.interfaces;

import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectSearchParametersDto;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAll();

    ProjectDto getById(Long id);

    void save(ProjectDto projectDto);

    void update(ProjectDto projectDto);

    void delete(Long id);

    List<ProjectDto> search(ProjectSearchParametersDto projectSearchParametersDto);
}
