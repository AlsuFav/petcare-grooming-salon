package ru.fav.petcare.grooming.salon.exception;

public class ClientAlreadyExistsException extends BadRequestException {
    public ClientAlreadyExistsException(String phone) {
        super("Пользователь с телефоном " + phone + " уже существует");
    }
}