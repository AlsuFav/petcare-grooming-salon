package ru.fav.petcare.grooming.salon.exception;

public class InvalidCredentialsException extends BadRequestException {
    public InvalidCredentialsException() {
        super("Неверные учетные данные");
    }
}