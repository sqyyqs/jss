package com.sqy.dto.project;

import com.sqy.dto.EmployeeDto;

public record ProjectSearchParametersDto(
        ProjectDto projectDto,
        EmployeeDto employeeDto
) {
}
