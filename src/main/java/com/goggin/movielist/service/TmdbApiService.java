package com.goggin.movielist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.goggin.movielist.exception.MovieNotFoundInTmdbException;
import com.goggin.movielist.model.Movie;
import com.goggin.movielist.model.TmdbResponse;
import com.goggin.movielist.model.TmdbResponseResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TmdbApiService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base.url}")
    private String baseUrl;

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
     * @throws MovieNotFoundInTmdbException
     * @throws Exception
     */
    public Movie getMovieDetailsFromTmdbById(Integer tmdbMovieId) throws MovieNotFoundInTmdbException {
        // configure details
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/movie")
                .pathSegment(tmdbMovieId.toString())
                .build()
                .toUriString();

        // setup headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            ResponseEntity<Movie> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Movie.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("Movie retrieved successfully from tmdb: {}", responseEntity.getBody());
                return responseEntity.getBody();
            } else {
                log.warn("Failed to retrieve movie, status code: {}", responseEntity.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Movie not found for ID: {} due to {}", tmdbMovieId, e.getMessage());
                throw new MovieNotFoundInTmdbException("Movie with ID " + tmdbMovieId + " not found.");
            } else {
                log.error("HttpClientErrorExcpetion when getting movie from tMDB: {}", e.getMessage());
            }
        } catch (RestClientException e) {
            log.error("Error getting movie from tMDB: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error getting movie from tMDB for ID {}. Details: {}", tmdbMovieId, e.getMessage());
        }

        return null;
    }

    public List<TmdbResponseResult> getMoviesFromTmdbSearchByName(String title) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search/movie")
                .queryParam("query", title)
                .toUriString();

        // setup headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            ResponseEntity<TmdbResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
                    TmdbResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("Movies retrieved successfully from tmdb: {}", responseEntity.getBody().getResults());
                return responseEntity.getBody().getResults();
            } else {
                log.warn("Failed to retrieve movie, status code: {}", responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Unexpected error getting movies from tMDB search. Details: {}", e.getMessage());
        }

        return null;
    }

}