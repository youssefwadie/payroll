package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Address;
import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.entities.Payslip;
import com.github.youssefwadie.payroll.reports.EmployeeReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee();

        employee.setFullName("Youssef Wadie");
        employee.setEmail("youssefwadie14@gmail.com");
        employee.setDateOfBirth(LocalDate.of(2000, 7, 28));
        employee.setHiredDate(LocalDate.of(2021, 8, 25));
        employee.setBasicSalary(new BigDecimal("8000"));

        Address address = new Address();
        address.setStreetAddress("31");
        address.setState("Cairo");
        address.setCountry("Egypt");
        employee.setAddress(address);


        Payslip payslip = new Payslip();
        payslip.setBasicSalary(new BigDecimal("6000"));
        payslip.setDepartmentName("com");
    }

    @Test
    public void setSuperVisor() {
        Employee supervisor1 = employeeRepository.findById(59L).get();
        Employee supervisor2 = employeeRepository.findById(6L).get();
        Employee supervisor3 = employeeRepository.findById(94L).get();
        Employee supervisor4 = employeeRepository.findById(63L).get();

        List<Employee> employees = employeeRepository.findAll();
        Random random = new Random();
        for (Employee employee : employees) {
            Long employeeId = employee.getId();
            if (employeeId.equals(supervisor1.getId()) || employeeId.equals(supervisor2.getId()) || employeeId.equals(supervisor3.getId())
                    || employeeId.equals(supervisor4.getId())) {
                continue;
            }
            if (random.nextBoolean()) {
                int rand = random.nextInt(1, 5);
                switch (rand) {
                    case 1 -> employee.setSupervisor(supervisor1);
                    case 2 -> employee.setSupervisor(supervisor2);
                    case 3 -> employee.setSupervisor(supervisor3);
                    case 4 -> employee.setSupervisor(supervisor4);
                }
            }
        }
    }


    @Test
    public void testGetEmployeesWithPayslipsInPeriod() {
        List<Employee> employees = employeeRepository.getEmployeesWithPayslipsInPeriod(LocalDate.of(2022, 6, 1),
                LocalDate.of(2022, 6, 30));

        List<EmployeeReport> reports = new LinkedList<>();
        for (Employee employee : employees) {
            BigDecimal grossPay = BigDecimal.ZERO;
            BigDecimal totalWithholding = BigDecimal.ZERO;
            for (Payslip payslip : employee.getPastPayslips()) {
                totalWithholding = totalWithholding.add(payslip.getTotalDeductions());
                grossPay = grossPay.add(payslip.getBasicSalary()).add(payslip.getTotalAllowances());
            }
            reports.add(new EmployeeReport(employee.getFullName(), employee.getDepartment().getName(),
                    grossPay, totalWithholding));
        }

        for (EmployeeReport report : reports) {
            System.out.println("===================================");
            System.out.println(report.getEmployeeName());
            System.out.println(report.getNetAmountPayable());
            System.out.println("===================================");
        }
    }
}
