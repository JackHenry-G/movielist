package com.goggin.movielist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.goggin.movielist.exception.MovieWithThisTitleAlreadyExistsException;
import com.goggin.movielist.model.Movie;
import com.goggin.movielist.service.MovieService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller // this is used for returning views, an interprets it as the name of the view
// @RestController // is tailored for JSON so treats it as a response body, not
// a view name
public class MovieController {

    /*
     * @Autowired creates an instance of the movie service to be used
     * essentially replaces having to use a constructor like
     * public MovieController(MovieService movieService) {
     * this.movieService = movieService;
     * }
     */
    @Autowired
    private MovieService movieService;

    // TEST ------------

    @GetMapping("/") // test endpoint
    public Movie getMovie(Model model) {
        Movie movie = new Movie(2, "Matrix", "Action", 9.5);
        // model.addAttribute("moviename", movie.getName());
        // return "index.html";
        return movie;
    }

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

    // -------------- Get all movies in user's list ----------------------

    @GetMapping("/movies") // return all movies in user's list
    public String findAllMovies(Model model) {
        // return movieService.getAllMovies();
        Iterable<Movie> movielist = movieService.getAllMovies();
        model.addAttribute("movieList", movielist);
        return "movieListHome.html";
    }

    // ------------------------ Add a new movie -----------------------

    @GetMapping("/movies/add") // return form in for user to add a new movie
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie()); // intitialize empty movie object so that Thymeleaf has Movie
        return "addMovieForm.html";
    }

    @PostMapping("/movies") // create/add a new movie
    public String addOneMovie(@ModelAttribute Movie movie, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors and return an appropriate response
            redirectAttributes.addFlashAttribute("error",
                    "Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                movieService.addMovieToList(movie);
                redirectAttributes.addFlashAttribute("success", "Movie added successfully!");
            } catch (MovieWithThisTitleAlreadyExistsException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Unexpected error occurred, please contact the admin.");
            }
        }
        return "redirect:/movies"; // Redirect back to the movies page

        /*
         * Flash attributes in Spring MVC are a way to store temporary attributes that
         * survive across a single redirect. They are typically used to pass data from
         * one request to another when you perform a redirect.
         * 
         * In the redirected controller or view, you can retrieve the flash attributes.
         * They are available as regular model attributes.
         * Example (in Thymeleaf): ${success} or ${error}.
         */
    }

    // ------------------------ Edit/update an existing movie ------------------

    // Edit/update a movie
    @PostMapping("/movies/{id}")
    public String updateMovie(
            @PathVariable Integer id,
            @ModelAttribute Movie updatedMovie, // Update this based on your entity fields
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Handle validation errors and return an appropriate response
            // for example, if a score of > 10 is entered.
            redirectAttributes.addFlashAttribute("error",
                    "Validation error: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            try {
                movieService.updateMovie(updatedMovie);
                redirectAttributes.addFlashAttribute("success", "Movie updated successfully!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
        }
        // Redirect back to the same page
        return "redirect:/movies/" + id + "/edit";
    }

    @GetMapping("/movies/{movieId}/edit") // return form for user to edit/update an existing movie
    public String showEditMovieForm(Model model, @PathVariable Integer movieId) {
        // pass existing movie object to the form, so info is pre-populated
        model.addAttribute("movie", movieService.getMovieById(movieId));
        return "editMovieForm.html";
    }

    // ------------------------ Delete an existing movie ------------------

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer id) {
        if (movieService.deleteMovieById(id)) {
            return ResponseEntity.ok("Movie deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }
    }
}
