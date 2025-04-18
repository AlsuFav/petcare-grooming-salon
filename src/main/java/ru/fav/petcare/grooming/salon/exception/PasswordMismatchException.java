package ru.fav.petcare.grooming.salon.exception;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException() {
        super("Пароли не совпадают");
    }
}