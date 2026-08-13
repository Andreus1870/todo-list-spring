package org.example.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(NoHandlerFoundException exception, Model model) {

        model.addAttribute("status", 404);
        model.addAttribute("message", "Page not found");

        return "error-page";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, Model model) {

        exception.printStackTrace();

        model.addAttribute("status", 500);
        model.addAttribute("message", "Internal server error");

        return "error-page";
    }
}