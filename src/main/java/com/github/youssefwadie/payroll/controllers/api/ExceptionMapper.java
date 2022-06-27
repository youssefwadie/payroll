package com.github.youssefwadie.payroll.controllers.api;

import com.github.youssefwadie.payroll.exceptions.DepartmentNotFoundException;
import com.github.youssefwadie.payroll.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionMapper {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        Map<String, String> constraintViolations = new HashMap<>();
        List<ConstraintViolation> requestViolations = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError.unwrap(ConstraintViolation.class))
                .toList();

        for (ConstraintViolation<?> cv : requestViolations) {
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
        Map<String, String> map = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", "400");
            put("message", message);
        }};

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
