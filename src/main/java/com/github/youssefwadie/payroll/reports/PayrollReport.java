package com.github.youssefwadie.payroll.reports;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Getter
public class PayrollReport {
    private final List<EmployeeReport> employeeReports;
    private BigDecimal netAmountPayable;

    public PayrollReport() {
        this.employeeReports = new LinkedList<>();
        netAmountPayable = BigDecimal.ZERO;
    }

    public void addEmployeeReport(EmployeeReport report) {
        employeeReports.add(report);
        netAmountPayable = netAmountPayable.add(report.getNetAmountPayable());
    }
}
