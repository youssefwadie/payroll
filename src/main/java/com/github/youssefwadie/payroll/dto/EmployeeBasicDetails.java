package com.github.youssefwadie.payroll.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.youssefwadie.payroll.entities.Employee;
import lombok.Getter;
import lombok.Setter;


@Getter
public class EmployeeBasicDetails {
    @JsonProperty("full_name")
    private String fullName;
    private int age;
    private Double basicSalary;
    private String email;

    @JsonProperty("supervisor")
    private SupervisorBasicDetails supervisor;
    private DepartmentBasicDetails department;

    public EmployeeBasicDetails(Employee employee) {
        this.fullName = employee.getFullName();
        this.basicSalary = employee.getBasicSalary();
        this.email = employee.getEmail();
        this.age = employee.getAge();
        if (this.supervisor != null) {
            this.supervisor = new SupervisorBasicDetails(employee.getSupervisor().getFullName(), employee.getSupervisor().getDepartment().getName());
        }
        this.department = new DepartmentBasicDetails(employee.getDepartment().getId(), employee.getDepartment().getName());
    }

}
