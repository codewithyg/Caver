package com.example.Caver.service;

import com.example.Caver.model.Weather;
import com.example.Caver.model.Forecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.text.SimpleDateFormat;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final String CURRENT_WEATHER_API_URL  = "https://api.openweathermap.org/data/2.5/weather?q=";
    private String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?";

    // 도시 이름을 인코딩하는 헬퍼 메서드 추가
    private String encodeCityName(String cityName) {
        return cityName.replace(" ", "+");
    }

    public Weather getCurrentWeather(String cityName) {
        String encodedCityName = encodeCityName(cityName);
        String urlWithApiKey = CURRENT_WEATHER_API_URL + encodedCityName + "&lang=kr&appid=" + apiKey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> response = restTemplate.getForObject(urlWithApiKey, Map.class);
            Weather weatherData = new Weather();
            weatherData.setLocation((String) response.get("name"));

            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
            if (weatherList != null && !weatherList.isEmpty()) {
                weatherData.setDescription((String) weatherList.get(0).get("description"));
                weatherData.setIconCode((String) weatherList.get(0).get("icon"));
            }

            Map<String, Object> main = (Map<String, Object>) response.get("main");
            if (main != null) {
                weatherData.setTemperature((Double) main.get("temp"));
                weatherData.setHumidity((Integer) main.get("humidity"));
                weatherData.setTempMin((Double) main.get("temp_min"));
                weatherData.setTempMax((Double) main.get("temp_max"));
            }

            Map<String, Object> wind = (Map<String, Object>) response.get("wind");
            if (wind != null) {
                weatherData.setWindSpeed((Double) wind.get("speed"));
            }

            return weatherData;
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("City not found");
        }
    }

    public List<Forecast> getFiveDayForecast(String cityName) {
        String encodedCityName = encodeCityName(cityName);

        String url = UriComponentsBuilder.fromHttpUrl(FORECAST_API_URL)
                .queryParam("q", encodedCityName)
                .queryParam("cnt", 40)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Forecast> forecasts = new ArrayList<>();
            List<Map<String, Object>> forecastList = (List<Map<String, Object>>) response.get("list");

            String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            for (Map<String, Object> forecastData : forecastList) {
                String dateTimeText = (String) forecastData.get("dt_txt");

                if (!dateTimeText.startsWith(todayDate) && dateTimeText.endsWith("12:00:00")) {
                    Forecast forecast = new Forecast();
                    forecast.setDateTime(dateTimeText.split(" ")[0]);

                    Map<String, Object> mainData = (Map<String, Object>) forecastData.get("main");
                    if (mainData != null) {
                        forecast.setTemperature(((Number) mainData.get("temp")).doubleValue());
                        forecast.setTempMin(((Number) mainData.get("temp_min")).doubleValue());
                        forecast.setTempMax(((Number) mainData.get("temp_max")).doubleValue());
                        forecast.setHumidity(((Number) mainData.get("humidity")).intValue());
                    }

                    List<Map<String, Object>> weatherList = (List<Map<String, Object>>) forecastData.get("weather");
                    if (weatherList != null && !weatherList.isEmpty()) {
                        forecast.setDescription((String) weatherList.get(0).get("description"));
                        forecast.setIconCode((String) weatherList.get(0).get("icon"));
                    }

                    forecasts.add(forecast);

                    if (forecasts.size() == 5) {
                        break;
                    }
                }
            }

            return forecasts;
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("City not found");
        }
    }
}