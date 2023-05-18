package com.sqy.dto;


import com.sqy.domain.employee.EmployeeStatus;

public record EmployeeDto(
        Long id,
        String firstName,
        String lastName,
        String middleName,
        String position,
        /* TODO: 17.05.2023 change from object */ Object account,
        String email,
        EmployeeStatus status
) {
}
