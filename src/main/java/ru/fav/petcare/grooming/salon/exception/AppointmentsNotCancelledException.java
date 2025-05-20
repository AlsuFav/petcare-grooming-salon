package ru.fav.petcare.grooming.salon.exception;

public class AppointmentsNotCancelledException extends BadRequestException {
    public AppointmentsNotCancelledException(String message) {
        super(message);
    }
}