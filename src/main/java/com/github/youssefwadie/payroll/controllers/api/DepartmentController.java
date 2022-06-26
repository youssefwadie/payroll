package com.github.youssefwadie.payroll.controllers.api;

import com.github.youssefwadie.payroll.entities.Department;
import com.github.youssefwadie.payroll.exceptions.DepartmentNotFoundException;
import com.github.youssefwadie.payroll.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/departments", produces = "application/json")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Department> getDepartmentsWithEmployees(@PathVariable("id") Long id) throws DepartmentNotFoundException {
        return new ResponseEntity<>(departmentService.findDepartmentWithEmployees(id), HttpStatus.OK);
    }
}
