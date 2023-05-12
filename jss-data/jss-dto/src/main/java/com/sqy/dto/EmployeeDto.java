package com.sqy.dto;

import com.sqy.domain.employee.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class EmployeeDto {
    private Long id;
    private String lastName;
    private String firstName;
    /*todo @Nullable*/ private String middleName;
    /*todo @Nullable*/ private String position;
    /*todo @Nullable*/ private Object account;
    /*todo @Nullable*/ private String email;
    private EmployeeStatus status;
}
