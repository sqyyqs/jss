package com.sqy.dto.employee;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.employee.EmployeeStatus;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@Data
@NoArgsConstructor
@Builder
public class EmployeeDto {
    @Nullable
    @JsonProperty("id")
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @Nullable
    @JsonProperty("middle_name")
    private String middleName;

    @Nullable
    @JsonProperty("position")
    private String position;

    @Nullable
    @JsonProperty("account")
    private String account;

    @Nullable
    @JsonProperty("email")
    private String email;

    @Nullable
    @JsonProperty("employee_status")
    private EmployeeStatus employeeStatus;


    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public EmployeeDto(@Nullable Long id, String firstName, String lastName, @Nullable String middleName, @Nullable String position,
                       @Nullable String account, @Nullable String email, @Nullable EmployeeStatus employeeStatus) {
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
