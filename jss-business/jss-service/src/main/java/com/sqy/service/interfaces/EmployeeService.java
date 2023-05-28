package com.sqy.service.interfaces;

import com.sqy.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAll();

    EmployeeDto getById(Long id);

    Long save(EmployeeDto employeeDto);

    boolean update(EmployeeDto employeeDto);

    boolean delete(Long id);

    List<EmployeeDto> search(String value);
}
