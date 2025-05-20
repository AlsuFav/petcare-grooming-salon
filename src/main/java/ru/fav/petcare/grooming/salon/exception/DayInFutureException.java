package ru.fav.petcare.grooming.salon.exception;

public class DayInFutureException extends BadRequestException {
    public DayInFutureException() {
        super("Этот день еще не наступил");
    }
}