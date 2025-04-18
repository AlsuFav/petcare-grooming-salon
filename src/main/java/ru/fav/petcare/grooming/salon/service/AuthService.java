package ru.fav.petcare.grooming.salon.service;

public interface  AuthService <T>{
    T login(String username, String password);
    T register(String firstName, String lastName, String username, String password, String confirmPassword);
}
