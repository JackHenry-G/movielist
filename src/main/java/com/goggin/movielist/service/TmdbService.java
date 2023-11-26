package com.goggin.movielist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.goggin.movielist.model.Movie;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TmdbService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * This method communicates with the TMDB movie API, by executing a GET method
     * to retrieve
     * a singular movie and attached details, by the ID provided.
     * It executes the request using the RestTemplate bean setup in WebConfig.
     * 
     * @param tmdbMovieId - the movie ID to search the API for
     * @return Movie object with the relevant details mapped from the API response
     * @throws Exception
     */
    public Movie getMovieFromTmdb(Integer tmdbMovieId) throws Exception {
        // configure details
        String url = "https://api.themoviedb.org/3/movie/" + tmdbMovieId + "?language=en-US";
        String apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxY2ZlZDIxNjQ2NjY2Yzk5YjNlZjA2NDZlMjg5MTFkYyIsInN1YiI6IjY1NWZhMzQ5NzA2ZTU2MDEzOGMyMDk2YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vgOOTsReFyIncA0dEgC-LmyvvsniZrHQW7n0reCUPvc";

        // setup headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // make GET request to configured URL, with the correct headers within 'entity'
        // and map to Movie.class
        ResponseEntity<Movie> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Movie.class);
        Movie movie = responseEntity.getBody();

        // the advantage of using @Slf4j is that if movie is null, it would not throw a
        // nullpointerexception like a system.out.println would
        // instead it handles 'null' values gracefully and it would say 'null'
        log.info("Movie retrieved from tmdbService: {}", movie);

        return movie;
    }
}
