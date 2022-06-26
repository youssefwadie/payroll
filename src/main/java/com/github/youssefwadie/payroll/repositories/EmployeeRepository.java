package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT KEY(p), VALUE(p) FROM Employee e JOIN e.employeePhoneNumbers p WHERE e.id = ?1")
    List<Object[]> getPhoneNumbers(Long id);

    @Query("SELECT e FROM Employee e where e.basicSalary between :lowerBound and :upperBound")
    List<Employee> getEmployeeBySalaryInRange(@Param("lowerBound") BigDecimal lowerBound,
                                              @Param("upperBound") BigDecimal upperBound);

    @Query("SELECT e FROM Employee e JOIN FETCH e.employeeAllowances")
    List<Employee> findAllWithAllowances();

    @Query("SELECT e FROM Employee e WHERE e.fullName LIKE %?1%")
    List<Employee> getEmployeeByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.department.id = ?1")
    List<Employee> getEmployeeByDepartment(Long departmentId);

    @Query(value = "SELECT e FROM Employee e JOIN FETCH e.nickNames WHERE e.id = ?1")
    Employee getEmployeeWithNickNames(Long id);

    @Query("SELECT e FROM Employee e LEFT JOIN e.pastPayslips pp WHERE e.id = pp.employee.id AND pp.payPeriod.startDate >= ?1 AND pp.payPeriod.endDate <= ?2")
    List<Employee> getEmployeesWithPayslipsInPeriod(LocalDate startDate, LocalDate endDate);


}
