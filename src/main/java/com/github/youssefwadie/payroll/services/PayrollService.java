package com.github.youssefwadie.payroll.services;

import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.entities.Payslip;
import com.github.youssefwadie.payroll.reports.EmployeeReport;
import com.github.youssefwadie.payroll.reports.PayrollReport;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@AllArgsConstructor
public class PayrollService {
    private final EmployeeService employeeService;

    @Async
    public CompletableFuture<PayrollReport> payroll(LocalDate startDate,
                                                    LocalDate endDate) throws InterruptedException {
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        List<Employee> employees = employeeService.getEmployeesWithPayslipsInPeriod(startDate, endDate);
        PayrollReport report = new PayrollReport();

        for (Employee employee : employees) {
            BigDecimal grossPay = BigDecimal.ZERO;
            BigDecimal totalWithholding = BigDecimal.ZERO;
            for (Payslip payslip : employee.getPastPayslips()) {
                totalWithholding = totalWithholding.add(payslip.getTotalDeductions());
                grossPay = grossPay.add(payslip.getBasicSalary()).add(payslip.getTotalAllowances());
            }
            report.addEmployeeReport(new EmployeeReport(employee.getFullName(), employee.getDepartment().getName(), grossPay, totalWithholding));
        }

        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(report);
    }

}
