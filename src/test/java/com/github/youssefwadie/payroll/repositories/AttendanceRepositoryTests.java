package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Attendance;
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
}
