package com.github.youssefwadie.payroll.attendance;

public class AttendanceRegisteredBeforeException extends Exception {
    public AttendanceRegisteredBeforeException(String message) {
        super(message);
    }
}
