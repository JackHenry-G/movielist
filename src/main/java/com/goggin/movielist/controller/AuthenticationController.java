package com.goggin.movielist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.goggin.movielist.model.Movie;
import com.goggin.movielist.model.User;
import com.goggin.movielist.service.MovieService;
import com.goggin.movielist.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignupPage(
            RedirectAttributes redirectAttributes) {

        // !!!!!!!!!!!!!!!!! PRE-REGISTER A USER TO MAKE TESTING QUICKER
        try {
            User user = new User(1, "test", "pwd");
            userService.saveUser(user);

            // save default movie to the database (need to change this all around)
            Movie initialMovie1 = new Movie(230423, "La La Land", "2016", 128, "Ryan Gosling is the man", "Romance");
            Movie initialMovie2 = new Movie(230426, "Hunger Games", "2010", 128, "May the odds be ever in your favour",
                    "Action");
            Movie initialMovie3 = new Movie(230493, "Goodfellas", "2000", 128, "Mobb bosses", "Crime");

            movieService.addMovieToUsersList(user, initialMovie1, 8);
            movieService.addMovieToUsersList(user, initialMovie2, 9);
            movieService.addMovieToUsersList(user, initialMovie3, 5);

            redirectAttributes.addFlashAttribute("registerSuccess", "User successfully registered!");
            return "redirect:/login";
        } catch (Exception e) {
            log.info(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/signup";
        }
        // !!!!!!!!!!!!!!!!! PRE-REGISTER A USER TO MAKE TESTING QUICKER

        // return "signup.html";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error",
                    "Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/signup";
        } else {
            try {
                userService.saveUser(user);

                // save default movie to the database so user has one in place already
                Movie initialMovie = new Movie(230423, "La La Land", "2016", 128, "Ryan Gosling is the man", "Romance");
                movieService.addMovieToUsersList(user, initialMovie, 8);

                redirectAttributes.addFlashAttribute("registerSuccess", "User successfully registered!");
                return "redirect:/login";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/signup";
            }
        }

    }

}
