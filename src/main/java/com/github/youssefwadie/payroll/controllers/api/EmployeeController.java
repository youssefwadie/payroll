package com.github.youssefwadie.payroll.controllers.api;

import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.exceptions.EmployeeNotFoundException;
import com.github.youssefwadie.payroll.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/employees", produces = "application/json")
public class EmployeeController {
    private final EmployeeService employeeService;


    @GetMapping("")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("id") Long id) throws EmployeeNotFoundException {
        return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Map<String, String>> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("_self", "/api/v1/employees/" + savedEmployee.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id:\\d+}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable("id") Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployeeById(id);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("deleted", id.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
