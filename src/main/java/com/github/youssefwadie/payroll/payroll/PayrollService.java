package com.github.youssefwadie.payroll.payroll;

import com.github.youssefwadie.payroll.attendance.AttendanceRepository;
import com.github.youssefwadie.payroll.employee.EmployeeService;
import com.github.youssefwadie.payroll.entities.Allowance;
import com.github.youssefwadie.payroll.entities.Attendance;
import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.entities.PayPeriod;
import com.github.youssefwadie.payroll.payroll.reports.EmployeeReport;
import com.github.youssefwadie.payroll.payroll.reports.PayrollReport;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@AllArgsConstructor
public class PayrollService {
    private final EmployeeService employeeService;
    private final AttendanceRepository attendanceRepository;

    private final static LocalTime STANDARD_TIME_IN = LocalTime.of(8, 0);
    private final static LocalTime STANDARD_TIME_OUT = LocalTime.of(14, 0);

    /**
     * Generate a payroll report in any time period.
     * Only handle the time-in delay
     *
     * @param startDate start time of the payroll calculation, not null
     * @param endDate   end time of th payroll calculation, nullable - defaults to the last day in the current month
     * @return The computed {@link PayrollReport}
     * @throws InterruptedException when the thread in which the calculation is taking place gets interrupted for any reason
     * @throws NullPointerException when the start date is null
     */

    @Async
    public CompletableFuture<PayrollReport> payroll(LocalDateTime startDate,
                                                    LocalDateTime endDate) throws InterruptedException {
        Objects.requireNonNull(startDate);
        if (endDate == null) {
            LocalDateTime today = LocalDateTime.now();
            endDate = LocalDateTime.of(today.getYear(), today.getMonth(), today.getMonth().maxLength(), 23, 59, 59);
        }

        List<Employee> employees = employeeService.findAllWithAllowances();
        PayrollReport report = new PayrollReport();

        for (Employee employee : employees) {
            double totalWithholding = 0.0d;
            double salaryPerDay = employee.getBasicSalary() / 30;

            List<Attendance> attendanceList =
                    attendanceRepository.findAttendanceByEmployeeIdAndTimeInPeriod(employee.getId(), startDate, endDate);
            if (attendanceList.isEmpty()) continue;

            for (Attendance attendance : attendanceList) {
                Duration betweenIn = Duration.between(STANDARD_TIME_IN, attendance.getTimeIn().toLocalTime());
                long inDelay = betweenIn.toMinutes();
                if (inDelay < 15) {
                    totalWithholding += salaryPerDay * 0.15;
                } else if (inDelay <= 60) {
                    totalWithholding += salaryPerDay * 0.3;
                } else if (inDelay > 61) {
                    // more than one time hour
                }
            }
            double totalAllowances = employee.getEmployeeAllowances().stream().map(Allowance::getAmount).reduce(0.0d, Double::sum);
            EmployeeReport employeeReport =
                    new EmployeeReport(employee.getFullName(), employee.getDepartment().getName(), totalAllowances + employee.getBasicSalary(), totalWithholding);
            report.addEmployeeReport(employeeReport);
        }

        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(report);
    }

    /**
     * @param payPeriod the pay period for the payroll calculation, not null
     * @return The computed {@link PayrollReport}
     * @throws InterruptedException when the thread in which the calculation is taking place gets interrupted for any reason
     * @throws NullPointerException when the payPeriod is null
     */
    public CompletableFuture<PayrollReport> payroll(PayPeriod payPeriod) throws InterruptedException {
        Objects.requireNonNull(payPeriod);
        LocalDate payPeriodStartDate = payPeriod.getStartDate();
        LocalDateTime startTime =
                LocalDateTime.of(payPeriodStartDate.getYear(), payPeriodStartDate.getMonth(), payPeriodStartDate.getDayOfMonth()
                        , STANDARD_TIME_IN.getHour(), STANDARD_TIME_IN.getMinute(), STANDARD_TIME_IN.getSecond());
        LocalDate payPeriodEndDate = payPeriod.getEndDate();
        LocalDateTime endTime = null;
        if (payPeriodEndDate != null) {
            endTime = LocalDateTime.of(payPeriodEndDate.getYear(), payPeriodEndDate.getMonth(),
                    payPeriodEndDate.getDayOfMonth(), 23, 59, 59);
        }
        return payroll(startTime, endTime);
    }
}
