package com.github.youssefwadie.payroll.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private BigDecimal totalAllowances;

    @Column(name = "total_deductions")
    private BigDecimal totalDeductions;

    @Column(name = "basic_salary")
    private BigDecimal basicSalary;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;
}
