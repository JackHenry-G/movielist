package com.goggin.movielist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.goggin.movielist.model.Movie;
import com.goggin.movielist.model.MovieConnection;
import com.goggin.movielist.service.MovieConnectionService;
import com.goggin.movielist.service.MovieService;
import com.goggin.movielist.service.TmdbService;
import com.goggin.movielist.service.UserService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TmdbService tmdbService;

    @Autowired
    private MovieConnectionService movieConnectionService;

    @GetMapping("/") // home page
    public String getMovie() {
        return "index.html";
    }

    @GetMapping("/movies")
    public String getAllMoviesForCurrentUser(Model model, Principal principal) {
        // principal.getName = currently logged in user's name

        List<MovieConnection> movieConnections = movieService
                .getMovieConnectionsByUsernameInRatingOrder(principal.getName());

        model.addAttribute("movieConnections", movieConnections);
        model.addAttribute("username", principal.getName());

        return "movieList.html";
    }

    // ------------------------ Search for movies page

    // return search page
    @GetMapping("/search")
    public String findAllMovies() {
        return "searchTmdb.html";
    }

    // ------------------------ Add a new movie -----------------------

    // return form for user to add a new movie based on their chosen TMDB movie
    @PostMapping("/movies/add")
    public String addTmdbMovieToMovieList(@RequestParam Integer tmdbMovieId, @RequestParam double rating,
            RedirectAttributes redirectAttributes) {

        try {
            // call tmdb to get all details of movie
            Movie tmdbMovie = tmdbService.getMovieFromTmdb(tmdbMovieId);

            Movie usersListMovie = movieService.addMovieToUsersList(userService.getCurrentUser(), tmdbMovie,
                    rating);
            redirectAttributes.addFlashAttribute("success",
                    usersListMovie.getTitle() + " has been added to your list!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unexpected error occurred, please contact the admin.");
        }

        return "redirect:/movies"; // Redirect back to the movies page
    }

    // ------------------------ Edit/update an existing movie ------------------

    // ---- Edit movie and return movielist screen
    @PostMapping("/movieConnections/{movieConnectionId}/rating")
    public String updateMovieConnectionRating(
            @PathVariable Integer movieConnectionId,
            @RequestParam double rating,
            RedirectAttributes redirectAttributes) {

        try {
            // call tmdb to get all details of movie
            MovieConnection movieConnection = movieConnectionService.findMovieConnectionById(movieConnectionId)
                    .orElseThrow(() -> new RuntimeException("MovieConnection not found"));

            movieConnection.setRating(rating);
            movieConnectionService.saveMovieConnection(movieConnection);

            redirectAttributes.addFlashAttribute("Rating updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unexpected error occurred, please contact the admin.");
        }

        return "redirect:/movies"; // Redirect back to the movies page
    }

    // ---------- Delete an existing movie
    @PostMapping("/movies/{id}/remove")
    public String removeMovieFromUsersList(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // find connection between current user and this movie and delete

        try {
            Movie movieToDeleteFromUsersList = movieService.getMovieById(id);

            MovieConnection movieConnection = movieConnectionService
                    .findMovieConnectionBetweenMovieAndConnection(userService.getCurrentUser(),
                            movieService.getMovieById(movieToDeleteFromUsersList.getMovie_id()))
                    .orElseThrow(() -> new RuntimeException("MovieConnection not found"));

            movieConnectionService.deleteMovieConnectionById(movieConnection.getMovie_connection_id());

            redirectAttributes.addFlashAttribute("success",
                    movieToDeleteFromUsersList.getTitle() + " - has been removed from your list!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Unexpected error occurred delete moving, please contact the admin.");
        }

        return "redirect:/movies"; // Redirect back to the movies page
    }

    // ------------------------------- TEST ---------------------------------------

    @GetMapping("/movies/{movieId}") // return specific movie to view
    public ResponseEntity<?> getMovie(@PathVariable Integer movieId) {
        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }

    @PostMapping("/multimovies") // add multiple movies in one request
    public ResponseEntity<?> addMultiMovies(@RequestBody Iterable<Movie> movies, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors and return an appropriate response
            // for example, if a score of > 10 is entered.
            return ResponseEntity.badRequest()
                    .body("Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            Iterable<Movie> savesMovies = movieService.addMultipleMoviesToList(movies);
            return ResponseEntity.ok(savesMovies);
        }
    }

}
