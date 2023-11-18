package com.goggin.movielist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.goggin.movielist.model.Movie;

import com.goggin.movielist.respositories.MovieRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class DataInitializer {

    private final MovieRepository movieRepository;

    public DataInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void initializeData() {
        // Check if the database is empty before adding an object
        if (this.movieRepository.count() == 0) {
            Movie movie = new Movie(10, "La La Land", "Romance", 9.2);
            this.movieRepository.save(movie);
            System.out.println("Default object added to the database for testing.");
        }
    }
}