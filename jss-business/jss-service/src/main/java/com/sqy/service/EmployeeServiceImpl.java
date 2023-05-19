package com.sqy.service;

import com.sqy.domain.employee.Employee;
import com.sqy.dto.EmployeeDto;
import com.sqy.mapper.Mapper;
import com.sqy.repository.EmployeeRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final Mapper<EmployeeDto, Employee> employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, Mapper<EmployeeDto, Employee> employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public EmployeeDto getById(Long id) {
        return employeeRepository.findById(id)
                .stream()
                .map(employeeMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public void save(EmployeeDto employeeDto) {
        employeeRepository.save(employeeMapper.getModelFromDto(employeeDto));
    }

    public void update(EmployeeDto employeeDto) {
        if (!employeeRepository.existsById(employeeDto.id())) {
            throw new IllegalArgumentException();
        }
        employeeRepository.save(employeeMapper.getModelFromDto(employeeDto));
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<EmployeeDto> search() {
        return Collections.emptyList();
        // TODO: 17.05.2023
    }
}
