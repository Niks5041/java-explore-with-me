package ru.practicum.exceptions;

public class DataIntegrity extends RuntimeException {
    public DataIntegrity(String message) {
        super(message);
    }
}
