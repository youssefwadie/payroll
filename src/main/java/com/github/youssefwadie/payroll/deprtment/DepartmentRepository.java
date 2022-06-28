package com.github.youssefwadie.payroll.deprtment;

import com.github.youssefwadie.payroll.dto.DepartmentBasicDetails;
import com.github.youssefwadie.payroll.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT d FROM Department d WHERE d.name = ?1")
    Department findByName(String name);

    @Query("SELECT DISTINCT (d) FROM Department d LEFT JOIN FETCH d.employees WHERE d.id = ?1")
    Optional<Department> findDepartmentWithEmployees(Long id);

    @Query("SELECT new com.github.youssefwadie.payroll.dto.DepartmentBasicDetails(d.id, d.name) FROM Department d")
    List<DepartmentBasicDetails> findAllBasicDetails();
}
