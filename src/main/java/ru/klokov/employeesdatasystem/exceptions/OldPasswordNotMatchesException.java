package ru.klokov.employeesdatasystem.exceptions;

public class OldPasswordNotMatchesException extends RuntimeException{
    public OldPasswordNotMatchesException(String message) {
        super(message);
    }
}
