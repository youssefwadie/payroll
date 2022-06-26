package com.github.youssefwadie.payroll.reports;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeReport {
    private final String employeeName;
    private final String employeeDepartment;
    private final BigDecimal grossPay;
    private final BigDecimal totalWithholding;
    private final BigDecimal netAmountPayable;

    public EmployeeReport(String employeeName, String employeeDepartment, BigDecimal grossPay, BigDecimal totalWithholding) {
        this.employeeName = employeeName;
        this.employeeDepartment = employeeDepartment;
        this.grossPay = grossPay;
        this.totalWithholding = totalWithholding;
        this.netAmountPayable = grossPay.subtract(totalWithholding);
    }
}
