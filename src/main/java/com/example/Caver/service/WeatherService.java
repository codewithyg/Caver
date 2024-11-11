package com.example.Caver.service;

import com.example.Caver.model.Weather;
import com.example.Caver.model.Forecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.text.SimpleDateFormat;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final String CURRENT_WEATHER_API_URL  = "https://api.openweathermap.org/data/2.5/weather?q=Busan&lang=kr&appid=";
    private String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?";

    public Weather getCurrentWeather(String cityName) {
        String urlWithApiKey = CURRENT_WEATHER_API_URL.replace("Busan", cityName) + apiKey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
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
    }

    public List<Forecast> getFiveDayForecast(String cityName) {
        String url = UriComponentsBuilder.fromHttpUrl(FORECAST_API_URL)
            .queryParam("q", cityName)
            .queryParam("cnt", 40)
            .queryParam("appid", apiKey)
            .queryParam("units", "metric")
            .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        List<Forecast> forecasts = new ArrayList<>();
        List<Map<String, Object>> forecastList = (List<Map<String, Object>>) response.get("list");

        // 오늘 날짜 구하기
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // 각 날의 12시 예보만 선택
        for (Map<String, Object> forecastData : forecastList) {
            String dateTimeText = (String) forecastData.get("dt_txt");
            
            // 오늘을 제외하고 "12:00:00"인 데이터만 선택
            if (!dateTimeText.startsWith(todayDate) && dateTimeText.endsWith("12:00:00")) {
                Forecast forecast = new Forecast();
                
                // 날짜 정보 설정
                forecast.setDateTime(dateTimeText.split(" ")[0]); // "yyyy-MM-dd" 형식으로 저장

                // 온도 및 습도 정보 설정
                Map<String, Object> mainData = (Map<String, Object>) forecastData.get("main");
                if (mainData != null) {
                    forecast.setTemperature(((Number) mainData.get("temp")).doubleValue());
                    forecast.setTempMin(((Number) mainData.get("temp_min")).doubleValue());
                    forecast.setTempMax(((Number) mainData.get("temp_max")).doubleValue());
                    forecast.setHumidity(((Number) mainData.get("humidity")).intValue());
                }

                // 날씨 설명 및 아이콘 코드 설정
                List<Map<String, Object>> weatherList = (List<Map<String, Object>>) forecastData.get("weather");
                if (weatherList != null && !weatherList.isEmpty()) {
                    forecast.setDescription((String) weatherList.get(0).get("description"));
                    forecast.setIconCode((String) weatherList.get(0).get("icon"));
                }

                forecasts.add(forecast);

                // 5일치 데이터만 가져오기 위해 종료
                if (forecasts.size() == 5) {
                    break;
                }
            }
        }
        
        return forecasts;
    }
}