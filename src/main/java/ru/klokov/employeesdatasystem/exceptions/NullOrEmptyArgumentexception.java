package ru.klokov.employeesdatasystem.exceptions;

public class NullOrEmptyArgumentexception extends RuntimeException{
    public NullOrEmptyArgumentexception(String message) {
        super(message);
    }
}
