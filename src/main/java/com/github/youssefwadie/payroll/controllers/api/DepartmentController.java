package com.github.youssefwadie.payroll.controllers.api;

import com.github.youssefwadie.payroll.deprtment.DepartmentNotFoundException;
import com.github.youssefwadie.payroll.deprtment.DepartmentService;
import com.github.youssefwadie.payroll.dto.DepartmentBasicDetails;
import com.github.youssefwadie.payroll.entities.Department;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<List<DepartmentBasicDetails>> getDepartments() {
        return new ResponseEntity<>(departmentService.findAllBasicDetails(), HttpStatus.OK);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Department> getDepartmentsWithEmployees(@PathVariable("id") Long id) throws DepartmentNotFoundException {
        return new ResponseEntity<>(departmentService.findDepartmentWithEmployees(id), HttpStatus.OK);
    }
}
