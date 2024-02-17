package com.goggin.movielist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.ToString;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

// entities are POJOs that represent a table. So this will represent the Movie table
// an instance of the entity correlates to a row in the table

@Entity
@Table(name = "movies")
@ToString // use Lombok to generate a ToString method without having to write it ourselves
public class Movie {

    @Id // used to mark it as the primary key of the table
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private Integer movie_id; // only integer as takes less storage and am not anticipating loads of accounts

    @JsonProperty("id")
    @Column(name = "tmdb_id")
    private Integer tmdb_id;

    @Column(unique = true)
    private String title;

    @JsonProperty("release_date") // api passes back as release date
    private String releaseYear;

    private Integer runtime; // e.g. 157, (Minutes)
    private String tagline; // e.g. "Everyone hungers for something."

    private String genre; // returned by TMDB

    private String backdrop_path; // image backdrop URL
    private String poster_path; // image post URL

    @Column(columnDefinition = "TEXT") // this string is a long piece of text. In SQL 'Text' is used to store bigger
                                       // bits of text data (over 255 characters)
    private String overview; // rundown of movie

    // cascadetype means if i delete a movieconnection record, all connections to a
    // user will be deleted too. OrphanRemoval means if a movie has no connections,
    // it will be removed too.
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieConnection> userRatings;

    // In order to be compatible with certain frameworks or tools that use
    // reflection to create instances of classes, it's a convention to provide a
    // default (empty) constructor in addition to any parameterized constructors.
    // Hibernate expects entities to have a no-arg constructor
    public Movie() {

    }

    public Movie(Integer movie_id, String title, String releaseYear, Integer runtime, String tagline, String genre,
            String backdrop_path, String poster_path, String overview) {
        this.movie_id = movie_id;
        this.title = title;
        this.releaseYear = releaseYear.substring(0, 4);
        this.runtime = runtime;
        this.tagline = tagline;
        this.genre = genre;
        this.backdrop_path = "https://image.tmdb.org/t/p/w500" + backdrop_path;
        this.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
        this.overview = overview;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Integer getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(Integer tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public Set<MovieConnection> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(Set<MovieConnection> userRatings) {
        this.userRatings = userRatings;
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
        this.releaseYear = releaseYear.substring(0, 4); // convert full date into just the year
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        // e.g. https://image.tmdb.org/t/p/w500//vQGo5VjJcHxpzIa8lMBFzpAth1w.jpg
        String tmdbUrl = "https://image.tmdb.org/t/p/w500" + backdrop_path;
        this.backdrop_path = tmdbUrl;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        String tmdbUrl = "https://image.tmdb.org/t/p/w500" + poster_path;
        this.poster_path = tmdbUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
