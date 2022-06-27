package com.github.youssefwadie.payroll.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@JsonIgnoreProperties("employee")
@Entity
@Table(name = "payslips")
public class Payslip extends AbstractEntity {

    // name , dept, pay period, earnings, deductions
    @Column(name = "department_name")
    private String departmentName;

    @Embedded
    private PayPeriod payPeriod;

    @Column(name = "total_allowances")
    private Double totalAllowances;

    @Column(name = "total_deductions")
    private Double totalDeductions;

    @Column(name = "basic_salary")
    private Double basicSalary;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;
}
