package com.nprogramming.weather;

import com.codahale.metrics.health.HealthCheck;
import org.apache.commons.lang3.StringUtils;

public class CityHealthCheck extends HealthCheck {

    private final String city;

    public CityHealthCheck(String city) {
        this.city = city;
    }

    @Override
    protected Result check() throws Exception {

        boolean isAlpha = StringUtils.isAlpha(city);

        if (!isAlpha) {
            return Result.unhealthy("Incorrect city name");
        }
        
        return Result.healthy();
    }
}
