package com.sqy.dto;


import com.sqy.domain.employee.EmployeeStatus;

public record EmployeeDto(
        Long id,
        String lastName,
        String firstName,
        String middleName,
        String position,
        Object account,
        String email,
        EmployeeStatus status
) {
}
