package com.github.youssefwadie.payroll.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public record EmployeeAttendanceRegistration(@NotNull @JsonProperty(value = "employee_id") Long employeeId,
                                             @NotNull @JsonProperty(value = "registration_type")
                                             RegistrationType registrationType) {
}
