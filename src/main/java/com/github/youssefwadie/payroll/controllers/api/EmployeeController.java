package com.github.youssefwadie.payroll.controllers.api;

import com.github.youssefwadie.payroll.attendance.AttendanceNotRegisteredYetException;
import com.github.youssefwadie.payroll.attendance.AttendanceRegisteredBeforeException;
import com.github.youssefwadie.payroll.dto.EmployeeAttendanceRegistration;
import com.github.youssefwadie.payroll.employee.EmployeeNotFoundException;
import com.github.youssefwadie.payroll.employee.EmployeeService;
import com.github.youssefwadie.payroll.entities.Employee;
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
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) throws EmployeeNotFoundException {
        return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Map<String, String>> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        Map<String, String> response = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", "ok");
            // TODO: get the real host i,e: mimic UriInfo from JaxRS
            put("_self", "/api/v1/employees/" + savedEmployee.getId());
        }};

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id:\\d+}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Void> registerEmployeeAttendance(@Valid @RequestBody EmployeeAttendanceRegistration registration)
            throws AttendanceNotRegisteredYetException, AttendanceRegisteredBeforeException, EmployeeNotFoundException {
        employeeService.registerEmployeeAttendance(registration);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
