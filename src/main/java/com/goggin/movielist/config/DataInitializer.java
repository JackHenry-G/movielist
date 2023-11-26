package com.goggin.movielist.config;

import org.springframework.context.annotation.Configuration;
import com.goggin.movielist.model.Movie;

import com.goggin.movielist.respositories.MovieRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitializer {

    private final MovieRepository movieRepository;

    public DataInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void initializeData() {
        // Check if the database is empty before adding an object
        if (this.movieRepository.count() == 0) {
            Movie initialMovie = new Movie(230423, "La La Land", "2016", 128, "Ryan Gosling is the man", "Romance",
                    9.2);
            this.movieRepository.save(initialMovie);
            log.info(
                    "Default movie, {}, added to the database for testing so there is a list initiatlized for the user",
                    initialMovie);
        }
    }
}