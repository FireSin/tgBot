package ru.firesin.weather.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.firesin.weather.service.WeatherService;

/**
 * Author:    firesin
 * Date:      27.10.2023
 */

@Service
public class OpenWeatherMapService implements WeatherService {

    @Value("${OpenWeatherMap.url}")
    private String apiUrl;

    private int limit = 1;

    private final WebClient webClient;

    public OpenWeatherMapService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    public Mono<String> fetchCityData(String cityName, String apiKey) {
        String url = apiUrl.replace("{city name}", cityName)
                .replace("{limit}", String.valueOf(limit))
                .replace("{API key}", apiKey);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }
    @Override
    public String getWeather(String city) {
        return null;
    }
}
