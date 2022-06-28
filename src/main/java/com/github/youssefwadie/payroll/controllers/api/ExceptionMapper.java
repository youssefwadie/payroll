package com.github.youssefwadie.payroll.controllers.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.youssefwadie.payroll.attendance.AttendanceNotRegisteredYetException;
import com.github.youssefwadie.payroll.attendance.AttendanceRegisteredBeforeException;
import com.github.youssefwadie.payroll.deprtment.DepartmentNotFoundException;
import com.github.youssefwadie.payroll.employee.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(MethodArgumentNotValidException ex) throws NoSuchFieldException {
        Map<String, String> constraintViolations = new HashMap<>();
        List<ConstraintViolation> requestViolations = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError.unwrap(ConstraintViolation.class))
                .toList();

        for (ConstraintViolation<?> cv : requestViolations) {
            String path = cv.getPropertyPath().toString();
            JsonProperty jsonProperty = cv.getRootBean().getClass().getDeclaredField(path).getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                path = jsonProperty.value();
            }
            constraintViolations.put(path, cv.getMessage());
        }
        return new ResponseEntity<>(constraintViolations, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler({EmployeeNotFoundException.class, DepartmentNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, ?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = ex.getName().equals("id") ? "out of range: " : ex.getMessage();
        Map<String, ?> map = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", HttpStatus.BAD_REQUEST.value());
            put("message", message);
        }};

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, Object> response = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        }};
        if (ex.getSupportedHttpMethods() != null && !ex.getSupportedHttpMethods().isEmpty()) {
            response.put("supported-methods", ex.getSupportedHttpMethods());
        }
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({AttendanceRegisteredBeforeException.class})
    public ResponseEntity<Map<String, Object>> handleAttendanceRegisteredBeforeException(AttendanceRegisteredBeforeException ex) {
        Map<String, Object> response = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", HttpStatus.CONFLICT.value());
            put("message", ex.getMessage());
        }};
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AttendanceNotRegisteredYetException.class)
    public ResponseEntity<Map<String, Object>> handleAttendanceNotRegisteredYetException(AttendanceNotRegisteredYetException ex) {
        Map<String, Object> response = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", HttpStatus.CONFLICT.value());
            put("message", ex.getMessage());
        }};
        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        Map<String, Object> response = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        }};
        if (!ex.getSupportedMediaTypes().isEmpty()) {
            response.put("supported-types", ex.getSupportedMediaTypes());
        }

        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }
}
