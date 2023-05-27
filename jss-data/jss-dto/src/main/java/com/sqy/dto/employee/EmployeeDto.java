package com.sqy.dto.employee;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.employee.EmployeeStatus;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static java.util.Objects.requireNonNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeDto {
    @Nullable
    private Long id;

    private String firstName;

    private String lastName;

    @Nullable
    private String middleName;

    @Nullable
    private String position;

    @Nullable
    private Object account;

    @Nullable
    private String email;

    @Nullable
    private EmployeeStatus employeeStatus;


    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public EmployeeDto(@JsonProperty("id") @Nullable Long id,
                       @JsonProperty("first_name") String firstName,
                       @JsonProperty("last_name") String lastName,
                       @JsonProperty("middle_name") @Nullable String middleName,
                       @JsonProperty("position") @Nullable String position,
                       @JsonProperty("account") @Nullable Object account,
                       @JsonProperty("email") @Nullable String email,
                       @JsonProperty("employee_status") @Nullable EmployeeStatus employeeStatus) {
        this.id = id;
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
        this.middleName = middleName;
        this.position = position;
        this.account = account;
        this.email = email;
        this.employeeStatus = employeeStatus;
    }

}
