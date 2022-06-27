package com.github.youssefwadie.payroll.seeders;

import com.github.youssefwadie.payroll.entities.Attendance;
import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.repositories.AttendanceRepository;
import com.github.youssefwadie.payroll.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class AttendanceSeeder {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    private final Random random = new Random();

    public void seed() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            for (int day = 1; day <= 30; ++day) {
                if (random.nextBoolean() || random.nextBoolean()) {
                    Attendance attendance = new Attendance();
                    attendance.setTimeIn(LocalDateTime.of(2022, 6, day, 8, random.nextInt(0, 60), 0));
                    attendance.setTimeOut(LocalDateTime.of(2022, 6, day, 14, 0, 0));
                    attendance.setEmployee(employee);
                    attendanceRepository.save(attendance);
                }
            }
        }
    }

    public Long getCount() {
        return attendanceRepository.count();
    }
}
