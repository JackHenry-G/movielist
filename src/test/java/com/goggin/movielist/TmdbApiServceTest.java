package com.goggin.movielist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;

import com.goggin.movielist.exception.MovieNotFoundInTmdbException;
import com.goggin.movielist.model.Movie;
import com.goggin.movielist.respositories.MovieRepository;
import com.goggin.movielist.service.TmdbApiService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class TmdbApiServceTest {

    @Autowired
    private TmdbApiService tmdbApiService;

    @Test
    public void testGetMovieDetailsFromTmdbById_validId() throws MovieNotFoundInTmdbException {
        // arrange
        Integer jurassicParkId = 329;
        log.info("Testing TMDB API by getting movie with valid ID for Jurassic Park");

        // act
        Movie movie = tmdbApiService.getMovieDetailsFromTmdbById(jurassicParkId);
        log.info("Received movie details = " + movie);

        // assert
        assertNotNull(movie);
        assertEquals(jurassicParkId, movie.getTmdb_id());
        assertEquals("Jurassic Park", movie.getTitle());
    }

    @Test
    public void testGetMovieDetailsFromTmdbById_invalidId() throws MovieNotFoundInTmdbException {
        // arrange
        Integer invalidMovieId = -1;

        // act
        assertThrows(MovieNotFoundInTmdbException.class, () -> {
            tmdbApiService.getMovieDetailsFromTmdbById(invalidMovieId);
        });

    }

}
