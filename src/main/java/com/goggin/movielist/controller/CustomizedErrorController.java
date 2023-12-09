package com.goggin.movielist.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomizedErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get error status code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

            // Set the error message to the standard HTTP reason phrase
            model.addAttribute("errorMessage", Integer.toString(statusCode) + " - " + httpStatus.getReasonPhrase());
        } else {
            // Default error message
            model.addAttribute("errorMessage", "Something went wrong");
        }

        return "error.html";
    }
}
