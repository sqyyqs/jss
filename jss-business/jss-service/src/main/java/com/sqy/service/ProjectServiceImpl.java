package com.sqy.service;

import com.sqy.domain.project.Project;
import com.sqy.dto.EmployeeDto;
import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectSearchParametersDto;
import com.sqy.mapper.Mapper;
import com.sqy.repository.ProjectRepository;
import com.sqy.service.interfaces.ProjectService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.specification.ProjectSpecification.hasCode;
import static com.sqy.specification.ProjectSpecification.hasDescription;
import static com.sqy.specification.ProjectSpecification.hasEmployeeAccount;
import static com.sqy.specification.ProjectSpecification.hasEmployeeEmail;
import static com.sqy.specification.ProjectSpecification.hasEmployeeFirstName;
import static com.sqy.specification.ProjectSpecification.hasEmployeeLastName;
import static com.sqy.specification.ProjectSpecification.hasEmployeeMiddleName;
import static com.sqy.specification.ProjectSpecification.hasEmployeePosition;
import static com.sqy.specification.ProjectSpecification.hasEmployeeStatus;
import static com.sqy.specification.ProjectSpecification.hasName;
import static com.sqy.specification.ProjectSpecification.hasProjectStatus;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final Mapper<ProjectDto, Project> projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, Mapper<ProjectDto, Project> projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public List<ProjectDto> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public ProjectDto getById(Long id) {
        return projectRepository.findById(id)
                .stream()
                .map(projectMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public void save(ProjectDto projectDto) {
        projectRepository.save(projectMapper.getModelFromDto(projectDto));
    }

    public void update(ProjectDto projectDto) {
        if (!projectRepository.existsById(projectDto.id())) {
            throw new IllegalArgumentException();
        }
        projectRepository.save(projectMapper.getModelFromDto(projectDto));
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public List<ProjectDto> search(@Nullable ProjectSearchParametersDto projectSearchParametersDto) {
        Specification<Project> specification = Specification.where(null);
        if (projectSearchParametersDto == null) {
            return getAll();
        }
        if (projectSearchParametersDto.projectDto() != null) {
            ProjectDto projectDto = projectSearchParametersDto.projectDto();
            if (projectDto.code() != null) {
                specification = specification.and(hasCode(projectDto.code()));
            }
            if (projectDto.name() != null) {
                specification = specification.and(hasName(projectDto.name()));
            }
            if (projectDto.description() != null) {
                specification = specification.and(hasDescription(projectDto.description()));
            }
            if (projectDto.projectStatus() != null) {
                specification = specification.and(hasProjectStatus(projectDto.projectStatus()));
            }
        }
        if (projectSearchParametersDto.employeeDto() != null) {
            EmployeeDto employeeDto = projectSearchParametersDto.employeeDto();
            if (employeeDto.firstName() != null) {
                specification = specification.and(hasEmployeeFirstName(employeeDto.firstName()));
            }
            if (employeeDto.lastName() != null) {
                specification = specification.and(hasEmployeeLastName(employeeDto.lastName()));
            }
            if (employeeDto.middleName() != null) {
                specification = specification.and(hasEmployeeMiddleName(employeeDto.middleName()));
            }
            if (employeeDto.position() != null) {
                specification = specification.and(hasEmployeePosition(employeeDto.position()));
            }
            if (employeeDto.account() != null) {
                specification = specification.and(hasEmployeeAccount(employeeDto.account()));
            }
            if (employeeDto.email() != null) {
                specification = specification.and(hasEmployeeEmail(employeeDto.email()));
            }
            if (employeeDto.status() != null) {
                specification = specification.and(hasEmployeeStatus(employeeDto.status()));
            }
        }

        return projectRepository.findAll(specification)
                .stream()
                .map(projectMapper::getDtoFromModel)
                .toList();
    }
}
