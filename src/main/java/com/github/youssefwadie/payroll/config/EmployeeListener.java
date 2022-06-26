package com.github.youssefwadie.payroll.config;


import com.github.youssefwadie.payroll.entities.Employee;

import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.Period;

public class EmployeeListener {

    @PrePersist
    public void calculateEmployeeAge(Employee employee) {
        employee.setAge(Period.between(employee.getDateOfBirth(), LocalDate.now()).getYears());
    }
}
