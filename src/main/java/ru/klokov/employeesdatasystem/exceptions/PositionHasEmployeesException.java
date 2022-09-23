package ru.klokov.employeesdatasystem.exceptions;

public class PositionHasEmployeesException extends RuntimeException {
    public PositionHasEmployeesException(String message) {
        super(message);
    }
}
