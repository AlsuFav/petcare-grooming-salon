package ru.fav.petcare.grooming.salon.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String showServicesPage(Model model) {
        List<Service> services = serviceService.findAll();
        List<ServicePrice> servicePrices = servicePriceService.findAll();

        model.addAttribute("services", services);
        model.addAttribute("servicePrices", servicePrices);

        return "services";
    }
}
