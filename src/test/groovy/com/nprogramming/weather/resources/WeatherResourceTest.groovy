package com.nprogramming.weather.resources

import com.google.common.base.Optional
import com.nprogramming.weather.domain.WeatherData
import com.nprogramming.weather.WeatherResource
import com.nprogramming.weather.data.WeatherRepository
import spock.lang.Specification

class WeatherResourceTest extends Specification {

    def repository = Mock(WeatherRepository)
    
    def "should return temperature for Cracow"() {
        given: "instance of resource created"
        repository.queryByCityName("Cracow") >> new WeatherData("Cracow", 12.1)
        def resource = new WeatherResource("Cracow", repository);

        expect: "it returns the proper message"
        resource.getWeather(Optional.absent()) &&
                resource.getWeather(Optional.absent()).city == "Cracow" &&
                resource.getWeather(Optional.absent()).temperature > -50 &&
                resource.getWeather(Optional.absent()).temperature < 50
    }
}
