package ru.fav.petcare.grooming.salon.exception;

public class UnauthorizedAccessException extends BadRequestException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}