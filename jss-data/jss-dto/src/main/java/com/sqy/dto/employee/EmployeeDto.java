package com.sqy.dto.employee;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sqy.domain.employee.EmployeeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Идентификационный номер(обязателен при update, будет проигнорирован при save).")
    private Long id;

    @JsonProperty("first_name")
    @Schema(description = "Имя.")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "Фамилия.")
    private String lastName;

    @Nullable
    @JsonProperty("middle_name")
    @Schema(description = "Отчество(необязательно).")
    private String middleName;

    @Nullable
    @JsonProperty("position")
    @Schema(description = "Должность(необязательно).")
    private String position;

    @Nullable
    @JsonProperty("account")
    @Schema(description = "Имя пользователя(необязательно).")
    private String account;

    @Nullable
    @JsonProperty("email")
    @Schema(description = "Почта(необязательно).")
    private String email;

    @Nullable
    @JsonProperty("employee_status")
    @Schema(description = "Статус(активный / удаленный), при создании не влияет, всегда active(необязательно).")
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
