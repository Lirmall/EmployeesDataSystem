package ru.klokov.employeesdatasystem.exceptions;

public class CannotCreateUserException extends RuntimeException{
    public CannotCreateUserException(String message) {
        super(message);
    }
}
