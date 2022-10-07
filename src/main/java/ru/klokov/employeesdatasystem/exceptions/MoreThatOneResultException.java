package ru.klokov.employeesdatasystem.exceptions;

public class MoreThatOneResultException extends RuntimeException{
    public MoreThatOneResultException(String message) {
        super(message);
    }
}
