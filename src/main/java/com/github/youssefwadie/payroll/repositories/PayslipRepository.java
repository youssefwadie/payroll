package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {
    @Query("SELECT p FROM Payslip p WHERE p.employee.fullName = ?1")
    List<Payslip> getPayslipByEmployeeName(String employeeName);

    @Query("SELECT p FROM Payslip p WHERE p.id = ?1")
    List<Payslip> getPayslipByEmployeeId(Long id);
}
