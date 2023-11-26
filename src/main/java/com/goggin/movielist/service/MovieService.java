package com.goggin.movielist.service;

import org.springframework.stereotype.Service;
import com.goggin.movielist.model.Movie;
import com.goggin.movielist.respositories.MovieRepository;

import com.goggin.movielist.exception.MovieWithThisTitleAlreadyExistsException;

import io.micrometer.common.util.StringUtils;;

// not using a MovieServiceImpl (Implementation) because this application is not complex enough to need different implementations in the future
// therefore there is no need for the benefits that MovieService interface and MovieServiceImpl would bring like:
// - modularity and seperation of concerns
// - testability
// - flexibiliy and extensibility to add new things in the future without harming existing impls
// - code organization
// - dependency injeciton

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // CREATE operations ---------------
    public Movie addMovieToList(Movie movie) throws Exception {
        if (this.movieRepository.existsByTitle(movie.getTitle())) {
            throw new MovieWithThisTitleAlreadyExistsException("Movie with this title already exists in your list!");
        } else {
            return this.movieRepository.save(movie);
        }
    }

    public Iterable<Movie> addMultipleMoviesToList(Iterable<Movie> movies) {
        return this.movieRepository.saveAll(movies);
    }

    // READ operations ---------------
    public Iterable<Movie> getAllMovies() {
        return this.movieRepository.findAllByOrderByRatingDesc();
    }

    public Movie getMovieById(Integer id) {
        return this.movieRepository.findById(id).orElseThrow();
    }

    // UPDATE operations ---------------
    public Movie updateMovie(Movie movie) {
        Movie dbMovie = this.movieRepository.findById(movie.getId()).get();

        if (!StringUtils.isBlank(movie.getTitle())) {
            dbMovie.setTitle(movie.getTitle());
        }
        if (!StringUtils.isBlank(movie.getGenre())) {
            dbMovie.setGenre(movie.getGenre());
        }
        if (movie.getRating() != 0.0) {
            dbMovie.setRating(movie.getRating());
        }

        return this.movieRepository.save(dbMovie);
    }

    // DELETE operations ---------------
    public boolean deleteMovieById(Integer id) {
        if (this.movieRepository.existsById(id)) {
            this.movieRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // public Movie findById(Integer id) {
    // return new Movie(id, "Jurassic Park", "Thriller");
    // }
}
