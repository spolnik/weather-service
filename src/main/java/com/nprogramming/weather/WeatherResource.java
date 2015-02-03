package com.nprogramming.weather;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.nprogramming.weather.data.WeatherRepository;
import com.nprogramming.weather.domain.WeatherData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
public class WeatherResource {

    private final String defaultCity;
    private final WeatherRepository repository;

    public WeatherResource(String defaultCity, WeatherRepository repository) {
        this.defaultCity = defaultCity;
        this.repository = repository;
    }

    @GET
    @Timed
    public WeatherData getWeather(@QueryParam("city") Optional<String> city) {

        try {
            return repository.queryByCityName(city.or(defaultCity));
        } catch (IOException e) {
            e.printStackTrace();
            return new WeatherData();
        }
    }
}
