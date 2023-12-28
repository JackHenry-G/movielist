package com.goggin.movielist.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.goggin.movielist.model.Movie;
import com.goggin.movielist.model.MovieConnection;
import com.goggin.movielist.model.User;
import com.goggin.movielist.respositories.MovieConnectionRepository;
import com.goggin.movielist.respositories.MovieRepository;

import io.micrometer.common.util.StringUtils;

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
    private final MovieConnectionRepository movieConnectionRepository;
    private final MovieConnectionService movieConnectionService;

    public MovieService(MovieRepository movieRepository, MovieConnectionRepository movieConnectionRepository,
            MovieConnectionService movieConnectionService) {
        this.movieRepository = movieRepository;
        this.movieConnectionRepository = movieConnectionRepository;
        this.movieConnectionService = movieConnectionService;
    }

    // CREATE operations ---------------
    public Movie addMovieToUsersList(User user, Movie movie, double rating) throws Exception {

        // if movie from TMDB API already exists in our DB, don't add again
        Movie dbMovie = this.movieRepository.findByTitle(movie.getTitle());
        if (dbMovie == null) {
            dbMovie = this.movieRepository.save(movie);
        }

        // create connection, assign a rating and associate with use
        MovieConnection movieConnection = new MovieConnection(user, dbMovie, rating);
        this.movieConnectionService.saveMovieConnection(movieConnection);

        return dbMovie;
    }

    public Iterable<Movie> addMultipleMoviesToList(Iterable<Movie> movies) {
        return this.movieRepository.saveAll(movies);
    }

    public List<MovieConnection> getMovieConnectionsByUsernameInRatingOrder(String username) {
        List<MovieConnection> movieConnections = movieConnectionRepository
                .findByUser_UsernameOrderByRatingDesc(username);
        return movieConnections;
    }

    public List<Movie> getMoviesWhereRatingIsGreaterThan(String username, double rating) {
        List<MovieConnection> movieConnections = movieConnectionRepository
                .findByUser_UsernameAndRatingGreaterThan(username, rating);

        return movieConnections.stream()
                .map(connection -> connection.getMovie())
                .filter(movie -> movie != null) // Filter out null movies, if any
                .collect(Collectors.toList());
    }

    public Movie getMovieById(Integer id) {
        return this.movieRepository.findById(id).orElseThrow();
    }

    // UPDATE operations ---------------
    public Movie updateMovie(Movie movie) {
        Movie dbMovie = this.movieRepository.findById(movie.getMovie_id()).get();

        if (!StringUtils.isBlank(movie.getTitle())) {
            dbMovie.setTitle(movie.getTitle());
        }
        if (!StringUtils.isBlank(movie.getGenre())) {
            dbMovie.setGenre(movie.getGenre());
        }

        // if (movie.getRating() != 0.0) {
        // dbMovie.setRating(movie.getRating());
        // }

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
}
