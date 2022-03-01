package ru.netology.exception;

public class AlreadyRegisteredException extends RuntimeException {

    public AlreadyRegisteredException(String message) {
        super(message);
    }
}