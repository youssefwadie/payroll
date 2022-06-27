package com.github.youssefwadie.payroll.reports;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class PayrollReport {
    private final List<EmployeeReport> employeeReports;
    private Double netAmountPayable;

    public PayrollReport() {
        this.employeeReports = new LinkedList<>();
        netAmountPayable = 0.0d;
    }

    public void addEmployeeReport(EmployeeReport report) {
        employeeReports.add(report);
        netAmountPayable += report.getNetAmountPayable();
    }

    public boolean isEmpty() {
        return employeeReports.isEmpty();
    }
}
