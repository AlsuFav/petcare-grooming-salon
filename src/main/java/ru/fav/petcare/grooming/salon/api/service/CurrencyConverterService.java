package ru.fav.petcare.grooming.salon.api.service;

import java.math.BigDecimal;

public interface CurrencyConverterService {
    int convertRubToCurrency(int rubles, String currencyCode);
}
