package com.nprogramming.weather

import groovyx.net.http.RESTClient
import spock.lang.Specification

class WeatherSearchRestServiceTest extends Specification {

    def "'/weather' should return 200 and the temperature for the Cracow city"() {
        given: "application is running"
        new WeatherSearchApplication().run(["server"] as String[]);
        def endpoint = new RESTClient('http://localhost:8080/')

        when: "weather resource is accessed"
        def response = endpoint.get([path: 'weather'])

        then: "it returns 200 and proper message"
        with (response) {
            status == 200
            data.city == "Cracow"
            data.temperature > -50
            data.temperature < 50
        }
    }
}
