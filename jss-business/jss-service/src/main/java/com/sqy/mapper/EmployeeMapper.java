package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.employee.EmployeeStatus;
import com.sqy.dto.employee.EmployeeDto;

public class EmployeeMapper {

    public static Employee getModelFromDto(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setMiddleName(employeeDto.getMiddleName());
        employee.setPosition(employeeDto.getPosition());
        employee.setAccount(employeeDto.getAccount());
        employee.setEmail(employeeDto.getEmail());
        employee.setStatus(EmployeeStatus.ACTIVE);
        return employee;
    }

    public static EmployeeDto getDtoFromModel(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getEmployeeId());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setAccount(employee.getAccount());
        employeeDto.setPosition(employee.getPosition());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setMiddleName(employee.getMiddleName());
        employeeDto.setEmployeeStatus(employee.getStatus());
        return employeeDto;
    }
}
