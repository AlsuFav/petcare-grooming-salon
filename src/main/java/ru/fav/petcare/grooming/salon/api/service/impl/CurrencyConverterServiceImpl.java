package ru.fav.petcare.grooming.salon.api.service.impl;

import org.springframework.stereotype.Service;
import ru.fav.petcare.grooming.salon.api.dto.CbrCurrencyResponse;
import ru.fav.petcare.grooming.salon.api.service.CurrencyConverterService;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.InputStream;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private static final String CBR_API_URL = "https://www.cbr.ru/scripts/XML_daily.asp";
    private final OkHttpClient httpClient = new OkHttpClient();

    @Override
    public int convertRubToCurrency(int rubles, String currencyCode) {
        try {
            Request request = new Request.Builder().url(CBR_API_URL).build();
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    throw new NotFoundException("Ошибка получения данных от ЦБ РФ");
                }

                InputStream xmlStream = response.body().byteStream();
                CbrCurrencyResponse cbrResponse = CbrCurrencyResponse.parse(xmlStream);

                double rate = cbrResponse.getRateForCurrency(currencyCode)
                        .orElseThrow(() -> new NotFoundException("Валюта " + currencyCode + " не найдена"));

                return (int) Math.round(rubles / rate);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при конвертации валюты", e);
        }
    }
}
