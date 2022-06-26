package com.github.youssefwadie.payroll.controllers.api;

import com.github.youssefwadie.payroll.exceptions.DepartmentNotFoundException;
import com.github.youssefwadie.payroll.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionMapper {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> constraintViolations = new HashMap<>();
        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            String path = cv.getPropertyPath().toString();
            constraintViolations.put(path, cv.getMessage());
        }
        return new ResponseEntity<>(constraintViolations, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler({EmployeeNotFoundException.class, DepartmentNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = ex.getName().equals("id") ? "out of range: " : ex.getMessage();
        final Map<String, String> map = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", "400");
            put("message", message);
        }};

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
