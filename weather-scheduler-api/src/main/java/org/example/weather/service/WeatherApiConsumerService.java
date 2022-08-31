package org.example.weather.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class WeatherApiConsumerService {

    @Value("${api.openweathermap.key}")
    private String apiKey;

    //TODO change to async rest client
    @Autowired
    RestTemplate restTemplate;

//    @Autowired
//    WeatherRepository weatherRepository;

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&APPID={key}&units=metric";

    public void fetchWeatherByCityName(String cityName) {

        URI url = new UriTemplate(WEATHER_URL).expand(cityName, apiKey);

        LOG.info("Job has begun...");

        String response = restTemplate.getForEntity(url, String.class).getBody();

        LOG.info(String.valueOf(response));
        LOG.info("Job has finished...");

    }
}