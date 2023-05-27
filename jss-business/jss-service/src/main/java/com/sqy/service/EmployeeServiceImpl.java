package com.sqy.service;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.employee.EmployeeStatus;
import com.sqy.dto.employee.EmployeeDto;
import com.sqy.mapper.EmployeeMapper;
import com.sqy.repository.EmployeeRepository;
import com.sqy.service.interfaces.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.mapper.EmployeeMapper.getModelFromDto;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> getAll() {
        logger.info("Invoke getAll().");
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public EmployeeDto getById(Long id) {
        logger.info("Invoke getById({}).", id);
        return employeeRepository.findById(id)
                .stream()
                .map(EmployeeMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public boolean save(EmployeeDto employeeDto) {
        logger.info("Invoke save({}).", employeeDto);
        if (employeeDto.getId() != null) {
            employeeDto.setId(null);
        }
        employeeRepository.save(getModelFromDto(employeeDto));
        return true;
    }

    public boolean update(EmployeeDto employeeDto) {
        logger.info("Invoke update({}).", employeeDto);
        if (employeeDto.getId() == null) {
            return false;
        }
        return employeeRepository.findById(employeeDto.getId())
                .filter(employee -> employee.getStatus() == EmployeeStatus.ACTIVE)
                .map(employee -> {
                    Employee modelFromDto = getModelFromDto(employeeDto);
                    modelFromDto.setStatus(employee.getStatus());
                    employeeRepository.save(modelFromDto);
                    return true;
                }).orElse(false);
    }

    public boolean delete(Long id) {
        logger.info("Invoke delete({}).", id);
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setStatus(EmployeeStatus.DELETED);
                    employeeRepository.save(employee);
                    return true;
                })
                .orElse(false);
    }

    public List<EmployeeDto> search(String value) {
        logger.info("Invoke search({}).", value);
        return employeeRepository.findByFieldsContaining(value)
                .stream()
                .map(EmployeeMapper::getDtoFromModel)
                .toList();
    }
}
