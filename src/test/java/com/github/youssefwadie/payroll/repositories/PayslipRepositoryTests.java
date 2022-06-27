package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.PayPeriod;
import com.github.youssefwadie.payroll.entities.Payslip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class PayslipRepositoryTests {
    @Autowired
    private PayslipRepository payslipRepository;

    // better to create parameterized tests
    @Test
    public void testCreateFirstPayslipForITDep() {
        Payslip payslip = new Payslip();
        payslip.setDepartmentName("IT101M");
        payslip.setBasicSalary(6000d);
        PayPeriod payPeriod = new PayPeriod();
        payPeriod.setStartDate(LocalDate.of(2022, 8, 1));
        payslip.setPayPeriod(payPeriod);
        Payslip savedPayslip = payslipRepository.save(payslip);
        assertThat(savedPayslip.getId()).isGreaterThan(0);
        assertThat(savedPayslip.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    public void testCreateSecondPayslipForITDep() {
        Payslip payslip = new Payslip();
        payslip.setDepartmentName("IT101M");
        payslip.setBasicSalary(8000d);
        PayPeriod payPeriod = new PayPeriod();
        payPeriod.setStartDate(LocalDate.of(2022, 8, 1));
        payslip.setPayPeriod(payPeriod);
        Payslip savedPayslip = payslipRepository.save(payslip);
        assertThat(savedPayslip.getId()).isGreaterThan(0);
        assertThat(savedPayslip.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
    }


    @Test
    public void testCreateThirdPayslip() {
        Payslip payslip = new Payslip();
        payslip.setDepartmentName("HR101S");
        payslip.setBasicSalary(10000d);
        PayPeriod payPeriod = new PayPeriod();
        payPeriod.setStartDate(LocalDate.of(2022, 8, 1));
        payslip.setPayPeriod(payPeriod);
        Payslip savedPayslip = payslipRepository.save(payslip);
        assertThat(savedPayslip.getId()).isGreaterThan(0);
        assertThat(savedPayslip.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedPayslip.getBasicSalary()).isEqualTo("10000");
    }
}
