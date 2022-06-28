package com.github.youssefwadie.payroll.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;

public record EmployeeAttendanceRegistration(@NotNull @JsonProperty(value = "employee_id") Long employeeId,
                                             @NotNull @JsonProperty(value = "registration_type")
                                             RegistrationType registrationType) {
}
