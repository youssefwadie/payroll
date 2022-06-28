package com.github.youssefwadie.payroll.payslip;

import com.github.youssefwadie.payroll.entities.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {
    @Query("SELECT p FROM Payslip p WHERE p.employee.fullName = ?1")
    List<Payslip> getPayslipByEmployeeName(String employeeName);

    @Query("SELECT p FROM Payslip p WHERE p.id = ?1")
    List<Payslip> getPayslipByEmployeeId(Long id);

    @Query("SELECT p FROM Payslip p WHERE p.employee.id = ?1 AND p.payPeriod.startDate >= ?2")
    Payslip getPayslipByEmployeeAndPayPeriod(Long id, LocalDate startDate);

}
