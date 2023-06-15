package com.sqy.unit;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.employee.EmployeeStatus;
import com.sqy.dto.employee.EmployeeDto;
import com.sqy.repository.EmployeeRepository;
import com.sqy.service.EmployeeServiceImpl;
import com.sqy.service.interfaces.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = EmployeeServiceImpl.class)
public class EmployeeServiceImplTests {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;


    @Test
    public void getAll_returnsListOfEmployees() {
        List<EmployeeDto> expected = List.of(
                EmployeeDto.builder().id(1L).firstName("FIRST_firstName").lastName("FIRST_lastName").build(),
                EmployeeDto.builder().id(2L).firstName("SECOND_firstName").lastName("SECOND_lastName").build(),
                EmployeeDto.builder().id(3L).firstName("THIRD_firstName").lastName("THIRD_lastName").build()
        );

        when(employeeRepository.findAll()).thenReturn(List.of(
                Employee.builder()
                        .employeeId(1L)
                        .firstName("FIRST_firstName")
                        .lastName("FIRST_lastName")
                        .build(),

                Employee.builder()
                        .employeeId(2L)
                        .firstName("SECOND_firstName")
                        .lastName("SECOND_lastName")
                        .build(),

                Employee.builder()
                        .employeeId(3L)
                        .firstName("THIRD_firstName")
                        .lastName("THIRD_lastName")
                        .build()
        ));


        List<EmployeeDto> result = employeeService.getAll();

        assertEquals(expected, result);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void getById_ReturnsEmployeeDto() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(
                Employee.builder()
                        .employeeId(1L)
                        .firstName("first_name")
                        .lastName("last_name")
                        .build()
        ));

        EmployeeDto result = EmployeeDto.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();

        assertEquals(result, employeeService.getById(1L));
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getById_ReturnsNull() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(employeeService.getById(2L));
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void save_ReturnsSavedEntityId() {
        EmployeeDto input =
                EmployeeDto.builder().id(13L).firstName("first_name").lastName("last_name").build();

        Employee savedEntity = Employee.builder().employeeId(13L).build();
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEntity);

        Long result = employeeService.save(input);

        assertNotNull(result);
        assertEquals(savedEntity.getEmployeeId(), result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void update_WithoutId() {
        EmployeeDto employeeDto = EmployeeDto.builder().firstName("FN").lastName("LN").build();
        assertFalse(employeeService.update(employeeDto));
        verify(employeeRepository, never()).findById(anyLong());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void update_WithNonExistingId() {
        EmployeeDto employeeDto = EmployeeDto.builder().id(123L).firstName("FN").lastName("LN").build();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(employeeService.update(employeeDto));
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void update_WithExistingId_Active() {
        EmployeeDto employeeDto = EmployeeDto.builder().id(123L).firstName("FN").lastName("LN").build();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(
                Employee.builder().employeeId(123L).firstName("FN").lastName("LN").status(EmployeeStatus.ACTIVE).build()
        ));

        assertTrue(employeeService.update(employeeDto));
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    public void update_WithExistingId_Deleted() {
        EmployeeDto employeeDto = EmployeeDto.builder().id(123L).firstName("FN").lastName("LN").build();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(
                Employee.builder().employeeId(123L).firstName("FN").lastName("LN").status(EmployeeStatus.DELETED).build()
        ));

        assertFalse(employeeService.update(employeeDto));
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, never()).save(any());
    }


    @Test
    public void delete_WithExistingId_ReturnsTrue() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(
                Employee.builder().employeeId(1L).firstName("firstName").lastName("lastName").build()
        ));
        doNothing().when(employeeRepository).deleteById(anyLong());
        assertTrue(employeeService.delete(1L));
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void delete_WithNonExistingId_ReturnsFalse() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(employeeService.delete(1L));
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void search() {
        List<EmployeeDto> expected = List.of(
                EmployeeDto.builder().id(45L).firstName("ASDskdfskfdj").lastName("FIRST_lastName").build(),
                EmployeeDto.builder().id(46L).firstName("dofjsdopfjsp").lastName("ASD123").build(),
                EmployeeDto.builder().id(129L).firstName("ASD,,,").lastName("';';;'';';''").build()
        );

        when(employeeRepository.findByFieldsContaining(anyString())).thenReturn(List.of(
                Employee.builder().employeeId(45L).firstName("ASDskdfskfdj").lastName("FIRST_lastName").build(),
                Employee.builder().employeeId(46L).firstName("dofjsdopfjsp").lastName("ASD123").build(),
                Employee.builder().employeeId(129L).firstName("ASD,,,").lastName("';';;'';';''").build()
        ));

        List<EmployeeDto> result = employeeService.search("ASD");

        assertEquals(expected, result);
        verify(employeeRepository, times(1)).findByFieldsContaining(anyString());
    }

}
