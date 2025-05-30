package ru.fav.petcare.grooming.salon.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fav.petcare.grooming.salon.entity.Service;
import ru.fav.petcare.grooming.salon.entity.ServicePrice;
import ru.fav.petcare.grooming.salon.service.ServicePriceService;
import ru.fav.petcare.grooming.salon.service.ServiceService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ServiceService serviceService;
    private final ServicePriceService servicePriceService;

    @GetMapping("/")
    public String showIndexPage(Model model) {
        List<Service> services = serviceService.findAll();
        model.addAttribute("services", services);
        return "index";
    }

    @GetMapping("/services")
    public String showServicesPage(
            @RequestParam(required = false, defaultValue = "RUB") String currency,
            Model model) {

        List<Service> services = serviceService.findAll();
        List<ServicePrice> servicePrices = servicePriceService.findAllInCurrency(currency);

        model.addAttribute("services", services);
        model.addAttribute("servicePrices", servicePrices);
        model.addAttribute("currency", currency);
        model.addAttribute("currencySymbol", getCurrencySymbol(currency));

        return "services";
    }

    private String getCurrencySymbol(String currency) {
        return switch (currency.toUpperCase()) {
            case "USD" -> "$";
            case "EUR" -> "€";
            default -> "₽";
        };
    }
}
