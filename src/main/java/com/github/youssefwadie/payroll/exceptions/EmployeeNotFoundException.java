package com.github.youssefwadie.payroll.exceptions;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException() {
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
