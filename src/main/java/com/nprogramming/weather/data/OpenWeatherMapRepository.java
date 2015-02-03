package com.nprogramming.weather.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nprogramming.weather.domain.Geometry;
import com.nprogramming.weather.domain.WeatherData;
import com.nprogramming.weather.WeatherSearchConfiguration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.nprogramming.weather.utils.TemperatureHelper.kelvinToCelsius;

public class OpenWeatherMapRepository implements WeatherRepository {

    private final String openWeatherMapUrl;
    private final Client client;
    private final String apiKey;

    public OpenWeatherMapRepository(
            WeatherSearchConfiguration configuration, 
            Client client) {
        
        this.client = client;
        openWeatherMapUrl = configuration.getOpenWeatherMapUrl();
        apiKey = configuration.getApiKey();
    }

    @Override
    public WeatherData queryByCityName(String cityName) throws IOException {

        WebTarget webTarget = client
                .target(openWeatherMapUrl)
                .path("weather")
                .queryParam("q", cityName);
        
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("x-api-key", apiKey)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String responseAsJson = response.readEntity(String.class);

        return weatherData(responseAsJson);
    }

    private WeatherData weatherData(String responseAsJson) throws IOException {
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode weatherDataJson = mapper.readTree(responseAsJson);
        
        String city = weatherDataJson.get("name").asText();
        String country = weatherDataJson.get("sys").get("country").asText();

        JsonNode main = weatherDataJson.get("main");
        double temperature = main.get("temp").asDouble();
        double pressure = main.get("pressure").asDouble();
        int humidity = main.get("humidity").asInt();
        
        int windSpeed = weatherDataJson.get("wind").get("speed").intValue();
        
        JsonNode coordinates = weatherDataJson.get("coord");
        double longitude = coordinates.get("lon").asDouble();
        double latitude = coordinates.get("lat").asDouble();
        
        return new WeatherData(
                city,
                country,
                kelvinToCelsius(temperature),
                pressure,
                humidity,
                windSpeed,
                new Geometry(longitude, latitude)
        );
    }
}
