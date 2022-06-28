package com.github.youssefwadie.payroll.payroll.reports;

import lombok.Data;

@Data
public class EmployeeReport {
    private final String employeeName;
    private final String employeeDepartment;
    private final Double grossPay;
    private final Double totalWithholding;
    private final Double netAmountPayable;

    public EmployeeReport(String employeeName, String employeeDepartment, Double grossPay, Double totalWithholding) {
        this.employeeName = employeeName;
        this.employeeDepartment = employeeDepartment;
        this.grossPay = grossPay;
        this.totalWithholding = totalWithholding;
        this.netAmountPayable = grossPay - totalWithholding;
    }

}
