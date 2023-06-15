package com.sqy.mapper;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.employee.EmployeeStatus;
import com.sqy.dto.employee.EmployeeDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EmployeeMapper {

    public static Employee getModelFromDto(EmployeeDto employeeDto) {
        log.info("Invoke getModelFromDto({}).", employeeDto);
        return Employee.builder().employeeId(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .middleName(employeeDto.getMiddleName())
                .position(employeeDto.getPosition())
                .account(employeeDto.getAccount())
                .email(employeeDto.getEmail())
                .status(EmployeeStatus.ACTIVE).build();
    }

    public static EmployeeDto getDtoFromModel(Employee employee) {
        log.info("Invoke getDtoFromModel({}).", employee);
        return EmployeeDto.builder().id(employee.getEmployeeId())
                .email(employee.getEmail())
                .account(employee.getAccount())
                .position(employee.getPosition())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .employeeStatus(employee.getStatus()).build();
    }
}
