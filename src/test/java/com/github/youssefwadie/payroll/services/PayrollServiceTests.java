package com.github.youssefwadie.payroll.services;

import com.github.youssefwadie.payroll.payroll.PayrollService;
import com.github.youssefwadie.payroll.payroll.reports.EmployeeReport;
import com.github.youssefwadie.payroll.payroll.reports.PayrollReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = {"com.github.youssefwadie.payroll"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PayrollServiceTests {

    @Autowired
    private PayrollService payrollService;

    @Test
    public void testPayroll() throws ExecutionException, InterruptedException {
        LocalDateTime startTime = LocalDateTime.of(2022, 6, 1, 8, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 6, 30, 23, 59, 59);
        CompletableFuture<PayrollReport> payroll = payrollService.payroll(startTime, endTime);
        payroll.get();
        PayrollReport payrollReport = payroll.get();
        for (EmployeeReport report : payrollReport.getEmployeeReports()) {
            System.out.println(report);
        }
    }

}
