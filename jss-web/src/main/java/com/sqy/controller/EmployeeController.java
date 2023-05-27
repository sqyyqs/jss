package com.sqy.controller;

import com.sqy.dto.employee.EmployeeDto;
import com.sqy.service.interfaces.EmployeeService;
import com.sqy.util.MappingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        logger.info("Invoke getAll().");
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable("id") Long id) {
        logger.info("Invoke getById({}).", id);
        EmployeeDto result = employeeService.getById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody EmployeeDto employeeDto) {
        logger.info("Invoke save({}).", employeeDto);
        boolean status = employeeService.save(employeeDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody EmployeeDto employeeDto) {
        logger.info("Invoke update({}).", employeeDto);
        boolean status = employeeService.update(employeeDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        logger.info("Invoke delete({}).", id);
        boolean status = employeeService.delete(id);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/search/{value}")
    public ResponseEntity<List<EmployeeDto>> search(@PathVariable("value") String value) {
        logger.info("Invoke search({}).", value);
        return ResponseEntity.ok(employeeService.search(value));
    }
}
