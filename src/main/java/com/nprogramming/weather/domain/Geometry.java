package com.nprogramming.weather.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry {
    
    private double longitude;
    private double latitude;

    public Geometry(double longitude, double latitude) {
        super();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @JsonProperty
    public double getLongitude() {
        return longitude;
    }

    @JsonProperty
    public double getLatitude() {
        return latitude;
    }
}
