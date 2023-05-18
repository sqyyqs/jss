package com.sqy.controller;

import com.sqy.dto.EmployeeDto;
import com.sqy.service.EmployeeService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }

    public EmployeeDto getById(Long id) {
        return employeeService.getById(id);
    }

    public void save(EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
    }

    public void update(EmployeeDto employeeDto) {
        employeeService.update(employeeDto);
    }

    public void delete(Long id) {
        employeeService.delete(id);
    }

    public List<EmployeeDto> search() {
        return employeeService.search();
    }
}
