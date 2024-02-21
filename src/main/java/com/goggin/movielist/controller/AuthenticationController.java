package com.goggin.movielist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.goggin.movielist.exception.UsernameAlreadyExistsException;
import com.goggin.movielist.model.Movie;
import com.goggin.movielist.model.User;
import com.goggin.movielist.service.MovieService;
import com.goggin.movielist.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Value("${show.test:false}") // :false is a default value. So if show.test isn't defined, it will be false
    private boolean showTest;

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been successfully logged out!");
        }

        model.addAttribute("showTest", showTest);
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup.html";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "profile.html";
    }

    // -------------------------- testing purposes, quickly signup test user

    @GetMapping("/signuptest")
    public String getSignupPage(
            RedirectAttributes redirectAttributes) {
        // !!!!!!!!!!!!!!!!! PRE-REGISTER A USER TO MAKE TESTING QUICKER
        try {
            User user = new User(1, "jackhenryg@hotmail.co.uk", "test", "pwd", 51.5074, -0.1278);
            userService.saveUser(user);

            // save default movie to the database (need to change this all around)
            Movie laLaLand = new Movie(230423, "La La Land", "2016-02-09", 128, "Ryan Gosling is the man",
                    "Romance", "/nlPCdZlHtRNcF6C9hzUH4ebmV1w.jpg", "/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg",
                    "Mia, an aspiring actress, serves lattes to movie stars in between auditions and Sebastian, a jazz musician, scrapes by playing cocktail party gigs in dingy bars, but as success mounts they are faced with decisions that begin to fray the fragile fabric of their love affair, and the dreams they worked so hard to maintain in each other threaten to rip them apart.");
            Movie hungerGames = new Movie(230426, "Hunger Games", "2010-10-02", 128,
                    "May the odds be ever in your favour", "Action", "/yDbyVT8tTOgXUrUXNkHEUqbxb1K.jpg",
                    "/yXCbOiVDCxO71zI7cuwBRXdftq8.jpg",
                    "Every year in the ruins of what was once North America, the nation of Panem forces each of its twelve districts to send a teenage boy and girl to compete in the Hunger Games.  Part twisted entertainment, part government intimidation tactic, the Hunger Games are a nationally televised event in which “Tributes” must fight with one another until one survivor remains.  Pitted against highly-trained Tributes who have prepared for these Games their entire lives, Katniss is forced to rely upon her sharp instincts as well as the mentorship of drunken former victor Haymitch Abernathy.  If she’s ever to return home to District 12, Katniss must make impossible choices in the arena that weigh survival against humanity and life against love. The world will be watching.");
            Movie goodfellas = new Movie(230493, "Goodfellas", "2000-10-01", 128, "Mobb bosses", "Crime",
                    "/sw7mordbZxgITU877yTpZCud90M.jpg", "/aKuFiU82s5ISJpGZp7YkIr3kCUd.jpg",
                    "The true story of Henry Hill, a half-Irish, half-Sicilian Brooklyn kid who is adopted by neighbourhood gangsters at an early age and climbs the ranks of a Mafia family under the guidance of Jimmy Conway.");
            Movie wonka = new Movie(230432, "Wonka", "2023-12-06", 117,
                    "Every good thing in this world started with a dream.", "Crime",
                    "/yOm993lsJyPmBodlYjgpPwBjXP9.jpg", "/qhb1qOilapbapxWQn9jtRCMwXJF.jpg",
                    "Willy Wonka – chock-full of ideas and determined to change the world one delectable bite at a time – is proof that the best things in life begin with a dream, and if you’re lucky enough to meet Willy Wonka, anything is possible.");
            Movie wish = new Movie(230478, "Wish", "2023-12-03", 95, "Be careful what you wish for.",
                    "Fantasy",
                    "/ehumsuIBbgAe1hg343oszCLrAfI.jpg", "/AcoVfiv1rrWOmAdpnAMnM56ki19.jpg",
                    "Asha, a sharp-witted idealist, makes a wish so powerful that it is answered by a cosmic force – a little ball of boundless energy called Star. Together, Asha and Star confront a most formidable foe - the ruler of Rosas, King Magnifico - to save her community and prove that when the will of one courageous human connects with the magic of the stars, wondrous things can happen.");

            movieService.addMovieToUsersList(user, laLaLand, 8);
            movieService.addMovieToUsersList(user, hungerGames, 8.9);
            // fav films
            movieService.addMovieToUsersList(user, goodfellas, 9.4); // not at cinema
            movieService.addMovieToUsersList(user, wonka, 9.1); // at cinema
            movieService.addMovieToUsersList(user, wish, 9.3); // at cinema

            log.info("Test user registered: " + user.toString());

            redirectAttributes.addFlashAttribute("registerSuccess", "User successfully registered!");

            if (showTest) {
                redirectAttributes.addFlashAttribute("testUname", "test");
                redirectAttributes.addFlashAttribute("testPwd", "pwd");
            }

            return "redirect:/login";
        } catch (Exception e) {
            log.error("Issue signing up test user: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
        // !!!!!!!!!!!!!!!!! PRE-REGISTER A USER TO MAKE TESTING QUICKER
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
                log.info("User registered: " + user.toString());

                // save default movie to the database so user has one in place already
                Movie initialMovie = new Movie(230423, "La La Land", "2016-02-09", 128, "Ryan Gosling is the man",
                        "Romance", "/nlPCdZlHtRNcF6C9hzUH4ebmV1w.jpg", "/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg",
                        "Mia, an aspiring actress, serves lattes to movie stars in between auditions and Sebastian, a jazz musician, scrapes by playing cocktail party gigs in dingy bars, but as success mounts they are faced with decisions that begin to fray the fragile fabric of their love affair, and the dreams they worked so hard to maintain in each other threaten to rip them apart.");
                movieService.addMovieToUsersList(user, initialMovie, 8);

                redirectAttributes.addFlashAttribute("registerSuccess", "User successfully registered!");
                return "redirect:/login";
            } catch (Exception e) {
                log.error("Issue signing up user {}", e.getMessage());
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/signup";
            }
        }

    }

    @PostMapping("/profile/edit")
    public String editProfile(@ModelAttribute User user, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editFailure",
                    "Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/profile";
        } else {
            try {
                userService.updateUser(user);
                log.info("User edited sucessfully: {}", user);

                redirectAttributes.addFlashAttribute("editSuccess", "User successfully edited!");
                return "redirect:/profile";
            } catch (UsernameAlreadyExistsException e) {
                log.error("Couldn't udpate the user as: {}", e.getMessage());
                redirectAttributes.addFlashAttribute("editFailure", e.getMessage());
                return "redirect:/profile";
            } catch (Exception e) {
                log.error("Issue updating user due to unexpected error: {}", e.getMessage());
                redirectAttributes.addFlashAttribute("editFailure", e.getMessage());
                return "redirect:/profile";
            }
        }
    }

}
