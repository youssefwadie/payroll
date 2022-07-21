package com.github.youssefwadie.payroll.controllers.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.youssefwadie.payroll.attendance.AttendanceNotRegisteredYetException;
import com.github.youssefwadie.payroll.attendance.AttendanceRegisteredBeforeException;
import com.github.youssefwadie.payroll.deprtment.DepartmentNotFoundException;
import com.github.youssefwadie.payroll.employee.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(MethodArgumentNotValidException ex) throws NoSuchFieldException {
        Map<String, Object> constraintViolations = responseHeader(HttpStatus.PRECONDITION_REQUIRED);

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
        return new ResponseEntity<>(constraintViolations, HttpStatus.PRECONDITION_REQUIRED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({EmployeeNotFoundException.class, DepartmentNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = ex.getName().equals("id") ? "out of range: " : ex.getMessage();
        Map<String, Object> map = responseHeader(HttpStatus.BAD_REQUEST);
        map.put("message", message);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {

        Map<String, Object> response = responseHeader(HttpStatus.METHOD_NOT_ALLOWED);

        if (ex.getSupportedHttpMethods() != null && !ex.getSupportedHttpMethods().isEmpty()) {
            response.put("supported-methods", ex.getSupportedHttpMethods());
        }
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({AttendanceRegisteredBeforeException.class})
    public ResponseEntity<Map<String, Object>> handleAttendanceRegisteredBeforeException(AttendanceRegisteredBeforeException ex) {
        Map<String, Object> response = new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", HttpStatus.CONFLICT.value());
            put("message", ex.getMessage());
        }};
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AttendanceNotRegisteredYetException.class)
    public ResponseEntity<Map<String, Object>> handleAttendanceNotRegisteredYetException(AttendanceNotRegisteredYetException ex) {
        Map<String, Object> response = responseHeader(HttpStatus.PRECONDITION_FAILED);
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        Map<String, Object> response = responseHeader(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        if (!ex.getSupportedMediaTypes().isEmpty()) {
            response.put("supported-types", ex.getSupportedMediaTypes());
        }

        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }

    // TODO
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return null;
    }

    private Map<String, Object> responseHeader(HttpStatus httpStatus) {
        return new LinkedHashMap<>() {{
            put("timestamp", LocalDateTime.now().toString());
            put("status", httpStatus.value());
            put("error", httpStatus.getReasonPhrase());
        }};
    }
}
