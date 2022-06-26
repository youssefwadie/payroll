package com.github.youssefwadie.payroll.services;

import com.github.youssefwadie.payroll.reports.EmployeeReport;
import com.github.youssefwadie.payroll.reports.PayrollReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@DataJpaTest
@ComponentScan(basePackages = {"com.github.youssefwadie.payroll"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PayrollServiceTests {

    @Autowired
    private PayrollService payrollService;

    @Test
    public void testPayroll() throws ExecutionException, InterruptedException {
        CompletableFuture<PayrollReport> payroll =
                payrollService.payroll(LocalDate.of(2022, 6, 1),
                        LocalDate.of(2022, 10, 30));

        PayrollReport payrollReport = payroll.get();
        for (EmployeeReport report : payrollReport.getEmployeeReports()) {
            System.out.println(report);
        }
    }

}
