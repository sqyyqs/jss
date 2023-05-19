package com.sqy.service;

import com.sqy.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAll();

    EmployeeDto getById(Long id);

    void save(EmployeeDto employeeDto);

    void update(EmployeeDto employeeDto);

    void delete(Long id);

    List<EmployeeDto> search();
}
