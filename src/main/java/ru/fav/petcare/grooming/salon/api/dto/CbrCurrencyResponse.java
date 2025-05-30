package ru.fav.petcare.grooming.salon.api.dto;


import java.util.List;
import lombok.Getter;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

public class CbrCurrencyResponse {

    private final List<Valute> valutes;

    public CbrCurrencyResponse(List<Valute> valutes) {
        this.valutes = valutes;
    }

    public Optional<Double> getRateForCurrency(String currencyCode) {
        return valutes.stream()
                .filter(v -> currencyCode.equalsIgnoreCase(v.charCode))
                .findFirst()
                .map(Valute::getNumericValue);
    }

    public static CbrCurrencyResponse parse(InputStream xmlInput) throws Exception {
        List<Valute> valutes = new ArrayList<>();

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(xmlInput);

        NodeList nodeList = doc.getElementsByTagName("Valute");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);

            String charCode = element.getElementsByTagName("CharCode").item(0).getTextContent();
            String valueStr = element.getElementsByTagName("Value").item(0).getTextContent().replace(',', '.');

            double value = Double.parseDouble(valueStr);
            valutes.add(new Valute(charCode, value));
        }

        return new CbrCurrencyResponse(valutes);
    }

    public static class Valute {
        @Getter
        private final String charCode;
        private final double value;

        public Valute(String charCode, double value) {
            this.charCode = charCode;
            this.value = value;
        }

        public Double getNumericValue() {
            return value;
        }
    }
}
