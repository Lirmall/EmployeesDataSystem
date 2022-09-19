package ru.klokov.employeesdatasystem.exceptions;

public class AlreadyCreatedException extends RuntimeException{
    public AlreadyCreatedException(String message) {
        super(message);
    }
}
