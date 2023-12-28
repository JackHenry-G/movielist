package com.goggin.movielist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goggin.movielist.model.User;
import com.goggin.movielist.service.GooglePlacesApiService;

@SpringBootTest
public class GooglePlacesApiServiceTest {

    @Autowired
    private GooglePlacesApiService googlePlacesApiService;

    @Test
    public void testGetPlaceUrlFromGooglePlacesApiTextSearch() {
        User user = new User(1, "jackhenryg@hotmail.co.uk", "test", "pwd", 51.5074, -0.1278);
        googlePlacesApiService.getPlaceUrlFromGooglePlacesApiTextSearch("Vue Cinema", 8, user);
    }
}
