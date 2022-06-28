package com.github.youssefwadie.payroll.deprtment;

import com.github.youssefwadie.payroll.entities.Department;
import com.github.youssefwadie.payroll.deprtment.DepartmentNotFoundException;
import com.github.youssefwadie.payroll.deprtment.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public Department findByName(String name) {
        return departmentRepository.findByName(name);
    }


    public Department findDepartmentWithEmployees(Long id) throws DepartmentNotFoundException {
        return departmentRepository.findDepartmentWithEmployees(id).
                orElseThrow(() -> new DepartmentNotFoundException(String.format("No department with id: %d was found.", id)));
    }
}
