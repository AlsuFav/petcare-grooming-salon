package ru.fav.petcare.grooming.salon.exception;

public class UnauthorizedException extends BadRequestException {
    public UnauthorizedException(String message) {
        super(message);
    }
}