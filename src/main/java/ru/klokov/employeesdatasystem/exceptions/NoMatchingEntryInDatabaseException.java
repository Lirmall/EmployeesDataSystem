package ru.klokov.employeesdatasystem.exceptions;

public class NoMatchingEntryInDatabaseException extends RuntimeException{
    public NoMatchingEntryInDatabaseException(String message) {
        super(message);
    }
}
