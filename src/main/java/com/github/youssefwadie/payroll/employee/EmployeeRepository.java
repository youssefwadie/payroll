package com.github.youssefwadie.payroll.employee;

import com.github.youssefwadie.payroll.dto.EmployeeBasicDetails;
import com.github.youssefwadie.payroll.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT KEY(p), VALUE(p) FROM Employee e JOIN e.employeePhoneNumbers p WHERE e.id = ?1")
    List<Object[]> getPhoneNumbers(Long id);

    @Query("SELECT e FROM Employee e where e.basicSalary between :lowerBound and :upperBound")
    List<Employee> getEmployeeBySalaryInRange(@Param("lowerBound") Double lowerBound,
                                              @Param("upperBound") Double upperBound);

    @Query("SELECT DISTINCT (e) FROM Employee e LEFT JOIN FETCH e.employeeAllowances")
    List<Employee> findAllWithAllowances();

    @Query("SELECT e FROM Employee e WHERE e.fullName LIKE %?1%")
    List<Employee> getEmployeeByName(String name);

    @Query("SELECT e FROM Employee e WHERE e.department.id = ?1")
    List<Employee> findEmployeesInDepartment(Long departmentId);

    // More projections would increase the performance of the query, but it'll not be very readable
    @Query("SELECT new com.github.youssefwadie.payroll.dto.EmployeeBasicDetails(e) FROM Employee e WHERE e.id = ?1")
    EmployeeBasicDetails findByIdBasic(Long id);

    @Query("SELECT new com.github.youssefwadie.payroll.dto.EmployeeBasicDetails(e) FROM Employee e")
    List<EmployeeBasicDetails> findAllBasic();
}
