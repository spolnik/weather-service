package com.nprogramming.weather;

import com.nprogramming.weather.data.OpenWeatherMapRepository;
import com.nprogramming.weather.data.WeatherRepository;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;
import java.util.EnumSet;

public class WeatherSearchApplication extends Application<WeatherSearchConfiguration> {

    public static void main(String[] args) throws Exception {
        new WeatherSearchApplication().run(args);
    }

    @Override
    public void run(
            WeatherSearchConfiguration config,
            Environment environment) throws Exception {

        configureCors(environment);

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

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS,PATCH");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }


    @Override
    public String getName() {
        return "weather-app";
    }
}
