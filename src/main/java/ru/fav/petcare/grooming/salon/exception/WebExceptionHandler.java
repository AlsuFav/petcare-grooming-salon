package ru.fav.petcare.grooming.salon.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Hidden
public class WebExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "redirect:/error/404";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "redirect:/error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleOtherErrors(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "redirect:/error/500";
    }
}