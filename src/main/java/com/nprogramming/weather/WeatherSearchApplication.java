package com.nprogramming.weather;

import com.nprogramming.weather.data.OpenWeatherMapRepository;
import com.nprogramming.weather.data.WeatherRepository;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class WeatherSearchApplication extends Application<WeatherSearchConfiguration> {

    public static void main(String[] args) throws Exception {
        new WeatherSearchApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<WeatherSearchConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/app", "index.html", "assets"));
    }

    @Override
    public void run(
            WeatherSearchConfiguration config,
            Environment environment) throws Exception {

        final Client client = new JerseyClientBuilder(environment)
                .using(config.getJerseyClientConfiguration())
                .build(getName());
        
        final WeatherRepository repository = new OpenWeatherMapRepository(
                config, client
        );
        
        final WeatherResource resource = new WeatherResource(
                config.getDefaultCity(),
                repository
        );
        
        final CityHealthCheck healthCheck = new CityHealthCheck(
            config.getDefaultCity()
        );
        
        environment.healthChecks().register("city", healthCheck);
        environment.jersey().register(resource);
    }

    @Override
    public String getName() {
        return "weather-app";
    }
}
