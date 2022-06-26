package com.github.youssefwadie.payroll.services;

import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.entities.Payslip;
import com.github.youssefwadie.payroll.exceptions.EmployeeNotFoundException;
import com.github.youssefwadie.payroll.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findEmployeeByDepartment(Long departmentId) {
        return employeeRepository.getEmployeeByDepartment(departmentId);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee deduct(Long id, BigDecimal amount) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("No employee with id: %d was found.", id)));
        Payslip currentPayslip = employee.getCurrentPayslip();
        BigDecimal newTotalDeductions = currentPayslip.getTotalDeductions().add(amount);
        currentPayslip.setTotalDeductions(newTotalDeductions);
        return this.save(employee);
    }

    public List<Employee> findAllWithAllowances() {
        return employeeRepository.findAllWithAllowances();
    }

    public List<Employee> getEmployeesWithPayslipsInPeriod(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.getEmployeesWithPayslipsInPeriod(startDate, endDate);
    }

    public Employee findById(Long id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(String.format("No employee with id: %d was found.", id)));
    }

    public void deleteEmployeeById(Long id) throws EmployeeNotFoundException {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(String.format("No employee with id: %d was found.", id));
        }
        employeeRepository.deleteById(id);
    }
}
