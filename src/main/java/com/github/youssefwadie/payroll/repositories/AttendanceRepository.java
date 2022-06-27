package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query("SELECT a FROM Attendance a WHERE a.employee.id = ?1 AND (a.timeIn >= ?2 AND a.timeIn <= ?3)")
    List<Attendance> findAttendanceByEmployeeIdAndTimeInPeriod(Long id, LocalDateTime startTime, LocalDateTime endTime);
}
