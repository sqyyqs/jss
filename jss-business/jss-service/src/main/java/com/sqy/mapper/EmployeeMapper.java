package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.dto.EmployeeDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements Mapper<EmployeeDto, Employee> {

    @Override
    public Employee getModelFromDto(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.id());
        employee.setFirstName(employeeDto.firstName());
        employee.setLastName(employeeDto.lastName());
        employee.setMiddleName(employeeDto.middleName());
        employee.setPosition(employeeDto.position());
        employee.setAccount(employeeDto.account());
        employee.setEmail(employeeDto.email());
        employee.setStatus(employeeDto.status());
        return employee;
    }

    @Override
    public EmployeeDto getDtoFromModel(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getMiddleName(),
                employee.getPosition(),
                employee.getAccount(),
                employee.getEmail(),
                employee.getStatus()
        );
    }
}
