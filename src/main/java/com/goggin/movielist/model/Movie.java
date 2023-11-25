package com.goggin.movielist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonProperty;

// entities are POJOs that represent a table. So this will represent the Movie table
// an instance of the entity correlates to a row in the table

@Entity
@Table(name = "Movies")
public class Movie {

    @Id // used to mark it as the primary key of the table
    @GeneratedValue(strategy = GenerationType.AUTO) // will generate a key unless one is provided. One would be provided
                                                    // in the event the user retrieves a movie from the TMDB API
                                                    // if they add one manually, an ID will be generated

    // --------------------------- RETURNED BY TMDB
    private Integer id;

    @Column(unique = true)
    private String title;

    @JsonProperty("release_date")
    private String releaseYear;

    private Integer runtime; // e.g. 157, (Minutes)
    private String tagline; // e.g. "Everyone hungers for something."

    // --------------------------- NOT RETURNED BY TMDB

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

    public Movie(Integer id, String title, String releaseYear, Integer runtime, String tagline, String genre,
            @Min(value = 0, message = "Score must be at least 0") @Max(value = 10, message = "Score must be at most 10") double rating) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.tagline = tagline;
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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {

        this.releaseYear = releaseYear.substring(0, 4);
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

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", runtime=" + runtime +
                ", tagline='" + tagline + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                '}';
    }

}
