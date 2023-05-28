package com.sqy.service;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.employee.EmployeeStatus;
import com.sqy.dto.employee.EmployeeDto;
import com.sqy.mapper.EmployeeMapper;
import com.sqy.repository.EmployeeRepository;
import com.sqy.service.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.mapper.EmployeeMapper.getModelFromDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> getAll() {
        log.info("Invoke getAll().");
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public EmployeeDto getById(Long id) {
        log.info("Invoke getById({}).", id);
        return employeeRepository.findById(id)
                .stream()
                .map(EmployeeMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public Long save(EmployeeDto employeeDto) {
        log.info("Invoke save({}).", employeeDto);
        if (employeeDto.getId() != null) {
            employeeDto.setId(null);
        }
        return employeeRepository.save(getModelFromDto(employeeDto)).getEmployeeId();
    }

    public boolean update(EmployeeDto employeeDto) {
        log.info("Invoke update({}).", employeeDto);
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
        log.info("Invoke delete({}).", id);
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setStatus(EmployeeStatus.DELETED);
                    employeeRepository.save(employee);
                    return true;
                })
                .orElse(false);
    }

    public List<EmployeeDto> search(String value) {
        log.info("Invoke search({}).", value);
        return employeeRepository.findByFieldsContaining(value)
                .stream()
                .map(EmployeeMapper::getDtoFromModel)
                .toList();
    }
}
