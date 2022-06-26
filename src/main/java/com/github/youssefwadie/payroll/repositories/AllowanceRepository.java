package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowanceRepository extends JpaRepository<Allowance, Long> {
}
