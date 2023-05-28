package com.sqy.repository;

import com.sqy.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e "
            + "WHERE (e.status = 'ACTIVE') AND ("
            + " e.firstName  LIKE %:value% OR  "
            + " e.lastName   LIKE %:value% OR  "
            + " e.middleName LIKE %:value% OR  "
            + " e.position   LIKE %:value% OR"
            + " e.email      LIKE %:value%)")
    List<Employee> findByFieldsContaining(@Param("value") String value);
}

