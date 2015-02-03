package com.nprogramming.weather.utils;

import java.math.BigDecimal;

public final class TemperatureHelper {
    
    public static double scaleTemperature(double value) {

        return new BigDecimal(value)
                .setScale(1, BigDecimal.ROUND_HALF_EVEN)
                .intValue();
    }

    public static double kelvinToCelsius(double temperature) {
        return scaleTemperature(temperature - 273.15);
    }
}

