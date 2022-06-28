package com.github.youssefwadie.payroll.employee;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException() {
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
