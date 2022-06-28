package com.github.youssefwadie.payroll.services;

import com.github.youssefwadie.payroll.attendance.AttendanceNotRegisteredYetException;
import com.github.youssefwadie.payroll.attendance.AttendanceRegisteredBeforeException;
import com.github.youssefwadie.payroll.dto.EmployeeAttendanceRegistration;
import com.github.youssefwadie.payroll.dto.RegistrationType;
import com.github.youssefwadie.payroll.employee.EmployeeNotFoundException;
import com.github.youssefwadie.payroll.employee.EmployeeService;
import com.github.youssefwadie.payroll.entities.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.github.youssefwadie.payroll")
public class EmployeeServiceIT {
    @Autowired
    private EmployeeService employeeService;

    @Test
    void testRegisterEmployeeAttendanceThrowsAttendanceRegisteredBeforeException() throws EmployeeNotFoundException {
        Long employeeId = 30L;
        Employee employee = employeeService.findById(employeeId);
        EmployeeAttendanceRegistration registration =
                new EmployeeAttendanceRegistration(employee.getId(), RegistrationType.IN);

        assertThatExceptionOfType(AttendanceRegisteredBeforeException.class)
                .isThrownBy(() -> {
                    employeeService.registerEmployeeAttendance(registration);
                }).withMessage("Attendance %s for %s is already registered",
                        registration.registrationType(), employee.getFullName());

    }
    @Test
    void testRegisterEmployeeAttendanceThrowsAttendanceNotRegisteredYetException() throws EmployeeNotFoundException {
        Long employeeId = 15L;
        Employee employee = employeeService.findById(employeeId);
        EmployeeAttendanceRegistration registration =
                new EmployeeAttendanceRegistration(employee.getId(), RegistrationType.OUT);

        assertThatExceptionOfType(AttendanceNotRegisteredYetException.class)
                .isThrownBy(() -> {
                    employeeService.registerEmployeeAttendance(registration);
                }).withMessage("Attendance IN for %s is not registered", employee.getFullName());

    }
}
