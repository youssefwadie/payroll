package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.attendance.AttendanceRepository;
import com.github.youssefwadie.payroll.entities.Attendance;
import com.github.youssefwadie.payroll.entities.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AttendanceRepositoryTests {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Test
    void testGetEmployeeAttendance() {
        LocalDateTime startTime = LocalDateTime.of(2022, 7, 4, 23, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 8, 20, 23, 59, 59);

        List<Attendance> attendanceList = attendanceRepository.findAttendanceByEmployeeIdAndTimeInPeriod(2L, startTime, endTime);
        assertThat(attendanceList.size()).isEqualTo(0);

        String expectedEmployeeName = attendanceList.get(0).getEmployee().getFullName();
        for (Attendance attendance : attendanceList) {
            String employeeName = attendance.getEmployee().getFullName();
            System.out.println(attendance.getTimeIn());
            assertThat(employeeName).isEqualTo(expectedEmployeeName);
            assertThat(attendance.getTimeIn()).isAfterOrEqualTo(startTime);
            assertThat(attendance.getTimeIn()).isBeforeOrEqualTo(endTime);
        }
    }

    @Test
    void testFindAttendanceByEmployeeAndTimeBefore() {
        Employee employee = new Employee();
        employee.setId(5L);
        LocalDateTime today = LocalDateTime.of(2022, 6, 27, 0, 0, 0);
        List<Attendance> attendanceList = attendanceRepository.findAttendanceByEmployeeIdAndTimeInPeriod(employee.getId(), today, LocalDateTime.now());
        assertThat(attendanceList).isNotNull();

        for (Attendance attendance : attendanceList) {
            assertThat(attendance.getTimeIn()).isAfterOrEqualTo(today);
            System.out.println(attendance.getTimeIn());
        }
    }
}
