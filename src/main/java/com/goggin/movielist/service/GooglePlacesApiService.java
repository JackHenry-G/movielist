package com.goggin.movielist.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.goggin.movielist.model.Place;
import com.goggin.movielist.model.PlaceResponse;
import com.goggin.movielist.model.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GooglePlacesApiService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Place> getPlaceUrlFromGooglePlacesApiTextSearch(String textQuery, Integer maxResultCount, User user) {
        // configure API details
        String url = "https://places.googleapis.com/v1/places:searchText";
        String apiKey = "AIzaSyAuEBLhs17AGsxqD2ttmekY7Q0Fa3Vb6Ns";

        // setup headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("X-Goog-Api-Key", apiKey);
        headers.add("X-Goog-FieldMask", "places.displayName,places.websiteUri");
        // HttpEntity<String> entity = new HttpEntity<String>(headers);

        // request body parameters
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("textQuery", textQuery);
        requestBody.put("maxResultCount", maxResultCount);

        Map<String, Object> locationBias = new HashMap<String, Object>();
        Map<String, Object> circle = new HashMap<String, Object>();
        Map<String, Object> center = new HashMap<String, Object>();
        center.put("latitude", user.getLatitude());
        center.put("longitude", user.getLongitude());
        circle.put("center", center);
        locationBias.put("circle", circle);
        requestBody.put("locationBias", locationBias);

        HttpEntity<Map<String, Object>> requestData = new HttpEntity<>(requestBody, headers);

        ResponseEntity<PlaceResponse> responseEntity = restTemplate.postForEntity(url, requestData,
                PlaceResponse.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            PlaceResponse responsebody = responseEntity.getBody();
            log.info("Sucessful Google Places APi requestcode: {}",
                    responseEntity.getStatusCode());

            return responsebody.getPlaces();
        } else {
            log.error("Error while making the Google Places API request. Status code: {}",
                    responseEntity.getStatusCode());
            return null;
        }

    }

}
