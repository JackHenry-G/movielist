package com.goggin.movielist.model;

import java.util.List;

import lombok.ToString;

@ToString
public class PlaceResponse {

    private List<Place> places;

    public PlaceResponse() {
    }

    public PlaceResponse(List<Place> places) {
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
