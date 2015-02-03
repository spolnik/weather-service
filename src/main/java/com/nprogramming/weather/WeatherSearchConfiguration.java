package com.nprogramming.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class WeatherSearchConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }
    
    
    @NotEmpty
    private String defaultCity = "Cracow";

    @NotEmpty
    private String apiKey = "59943e3ce6e663fb82340a9d99c28173";

    @NotEmpty
    private String openWeatherMapUrl = "http://api.openweathermap.org/data/2.5";
    
    @JsonProperty
    public String getDefaultCity() {
        return defaultCity;
    }
    
    @JsonProperty
    public String getApiKey() {
        return apiKey;
    }

    @JsonProperty
    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }

    @JsonProperty
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @JsonProperty
    public String getOpenWeatherMapUrl() {
        return openWeatherMapUrl;
    }

    @JsonProperty
    public void setOpenWeatherMapUrl(String openWeatherMapUrl) {
        this.openWeatherMapUrl = openWeatherMapUrl;
    }
}
