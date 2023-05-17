package com.sqy.service;

import com.sqy.domain.project.Project;
import com.sqy.dto.EmployeeDto;
import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectSearchParametersDto;
import com.sqy.mapper.ProjectMapper;
import com.sqy.repository.ProjectJdbcRepository;
import com.sqy.repository.ProjectRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectService {
    ProjectRepository projectRepository = new ProjectJdbcRepository();
    ProjectMapper projectMapper = new ProjectMapper();

    public ProjectService() throws IOException {
    }

    public void save(ProjectDto projectDto) {
        projectRepository.create(projectMapper.fromDto(projectDto));
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public void update(ProjectDto projectDto) {
        projectRepository.update(projectMapper.fromDto(projectDto));
    }

    public List<Project> getAll() {
        return projectRepository.getAll();
    }

    public Project get(Long id) {
        return projectRepository.getById(id);
    }

    public List<Project> search(ProjectSearchParametersDto pssd) {
        Map<String, Object> properties = new HashMap<>();
        if (pssd.employeeDto() != null) {
            EmployeeDto employeeDto = pssd.employeeDto();
            if (employeeDto.id() != null) {
                properties.put("employeeId", employeeDto.id());
            }
            if (employeeDto.firstName() != null) {
                properties.put("employeeFirstName", employeeDto.firstName());
            }
            if (employeeDto.email() != null) {
                properties.put("employeeAccount", employeeDto.email());
            }
            if (employeeDto.middleName() != null) {
                properties.put("employeeMiddleName", employeeDto.middleName());
            }
            if (employeeDto.position() != null) {
                properties.put("employeePosition", employeeDto.position());
            }
            if (employeeDto.account() != null) {
                properties.put("employeeAccount", employeeDto.account());
            }
            if (employeeDto.email() != null) {
                properties.put("employeeEmail", employeeDto.email());
            }
            if (employeeDto.status() != null) {
                properties.put("employeeStatus", employeeDto.status());
            }
        }
        if (pssd.projectDto() != null) {
            ProjectDto projectDto = pssd.projectDto();
            if (projectDto.code() != null) {
                properties.put("projectCode", projectDto.code());
            }
            if (projectDto.name() != null) {
                properties.put("projectName", projectDto.name());
            }
            if (projectDto.description() != null) {
                properties.put("projectDescription", projectDto.description());
            }
            if (projectDto.projectStatus() != null) {
                properties.put("projectStatus", projectDto.projectStatus());
            }
        }
        return projectRepository.searchByProps(properties);
    }
}
