package ru.practicum.ewm.server.exceptionshandler;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
