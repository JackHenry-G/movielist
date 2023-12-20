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
            Movie initialMovie1 = new Movie(230423, "La La Land", "2016-02-09", 128, "Ryan Gosling is the man",
                    "Romance", "/nlPCdZlHtRNcF6C9hzUH4ebmV1w.jpg", "/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg",
                    "Mia, an aspiring actress, serves lattes to movie stars in between auditions and Sebastian, a jazz musician, scrapes by playing cocktail party gigs in dingy bars, but as success mounts they are faced with decisions that begin to fray the fragile fabric of their love affair, and the dreams they worked so hard to maintain in each other threaten to rip them apart.");
            Movie initialMovie2 = new Movie(230426, "Hunger Games", "2010-10-02", 128,
                    "May the odds be ever in your favour", "Action", "/yDbyVT8tTOgXUrUXNkHEUqbxb1K.jpg",
                    "/yXCbOiVDCxO71zI7cuwBRXdftq8.jpg",
                    "Every year in the ruins of what was once North America, the nation of Panem forces each of its twelve districts to send a teenage boy and girl to compete in the Hunger Games.  Part twisted entertainment, part government intimidation tactic, the Hunger Games are a nationally televised event in which “Tributes” must fight with one another until one survivor remains.  Pitted against highly-trained Tributes who have prepared for these Games their entire lives, Katniss is forced to rely upon her sharp instincts as well as the mentorship of drunken former victor Haymitch Abernathy.  If she’s ever to return home to District 12, Katniss must make impossible choices in the arena that weigh survival against humanity and life against love. The world will be watching.");
            Movie initialMovie3 = new Movie(230493, "Goodfellas", "2000-10-01", 128, "Mobb bosses", "Crime",
                    "/sw7mordbZxgITU877yTpZCud90M.jpg", "/aKuFiU82s5ISJpGZp7YkIr3kCUd.jpg",
                    "The true story of Henry Hill, a half-Irish, half-Sicilian Brooklyn kid who is adopted by neighbourhood gangsters at an early age and climbs the ranks of a Mafia family under the guidance of Jimmy Conway.");

            movieService.addMovieToUsersList(user, initialMovie1, 8);
            movieService.addMovieToUsersList(user, initialMovie2, 9);
            movieService.addMovieToUsersList(user, initialMovie3, 5);

            redirectAttributes.addFlashAttribute("registerSuccess", "User successfully registered!");
            return "redirect:/login";
        } catch (Exception e) {
            log.info(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
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
                Movie initialMovie = new Movie(230423, "La La Land", "2016-02-09", 128, "Ryan Gosling is the man",
                        "Romance", "/nlPCdZlHtRNcF6C9hzUH4ebmV1w.jpg", "/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg",
                        "Mia, an aspiring actress, serves lattes to movie stars in between auditions and Sebastian, a jazz musician, scrapes by playing cocktail party gigs in dingy bars, but as success mounts they are faced with decisions that begin to fray the fragile fabric of their love affair, and the dreams they worked so hard to maintain in each other threaten to rip them apart.");
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
