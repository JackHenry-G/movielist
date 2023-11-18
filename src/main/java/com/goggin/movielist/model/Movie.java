package com.goggin.movielist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

// entities are POJOs that represent a table. So this will represent the Movie table
// an instance of the entity correlates to a row in the table

@Entity
@Table(name = "Movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String title;

    private String genre;

    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 10, message = "Score must be at most 10")
    private double rating;

    // In order to be compatible with certain frameworks or tools that use
    // reflection to create instances of classes, it's a convention to provide a
    // default (empty) constructor in addition to any parameterized constructors.
    // Hibernate expects entities to have a no-arg constructor
    public Movie() {

    }

    public Movie(Integer id, String title, String genre, double rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
