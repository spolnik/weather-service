package com.nprogramming.weather.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherData {
    
    private String city;
    private String country;
    private double temperature;
    private double pressure;
    private int humidity;
    private int windSpeed;
    private Geometry geometry;

    public WeatherData() {
    }

    public WeatherData(
            String city,
            String country,
            double temperature,
            double pressure,
            int humidity,
            int windSpeed,
            Geometry geometry) {
        
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.geometry = geometry;
    }

    @JsonProperty
    public String getCity() {
        return city;
    }

    @JsonProperty
    public double getTemperature() {
        return temperature;
    }

    @JsonProperty
    public String getCountry() {
        return country;
    }

    @JsonProperty
    public double getPressure() {
        return pressure;
    }

    @JsonProperty
    public int getHumidity() {
        return humidity;
    }

    @JsonProperty
    public int getWindSpeed() {
        return windSpeed;
    }

    @JsonProperty
    public Geometry getGeometry() {
        return geometry;
    }
}
