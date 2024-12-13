package com.example.Caver.service;

import com.example.Caver.model.Weather;
import com.example.Caver.model.Forecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * 날씨 API 호출 및 데이터 처리
 * OpenWeatherMap API 사용
 */

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    private final String CURRENT_WEATHER_API_URL  = "https://api.openweathermap.org/data/2.5/weather?";
    private final String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?";

    private final String KAKAO_GEOCODING_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    // 카카오 Geocoding API를 사용하여 도시의 x, y 좌표 얻기
    public Map<String, Double> getCoordinates(String city) {
        String url = KAKAO_GEOCODING_API_URL + "?query=" + city;
    
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
    
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
    
        List<Map<String, Object>> documents = (List<Map<String, Object>>) ((Map<String, Object>) response.getBody()).get("documents");
    
        if (!documents.isEmpty()) {
            Map<String, Object> address = documents.get(0);
            Double lon = Double.parseDouble(address.get("x").toString()); // 경도
            Double lat = Double.parseDouble(address.get("y").toString()); // 위도
    
            Map<String, Double> coordinates = new HashMap<>();
            coordinates.put("lat", lat);
            coordinates.put("lon", lon);
    
            return coordinates;
        } else {
            return null;
        }
    }

    // 현재 날씨 정보를 가져와 Weather 객체로 반환 (도시명 기준)
    public Weather getCurrentWeather(String cityName) {
        Map<String, Double> coordinates = getCoordinates(cityName);
        if (coordinates == null) {
            throw new RuntimeException("도시 정보를 찾을 수 없습니다.");
        }

        // 위도 경도 가져오기
        double lat = coordinates.get("lat").doubleValue();
        double lon = coordinates.get("lon").doubleValue();

        String url = UriComponentsBuilder.fromHttpUrl(CURRENT_WEATHER_API_URL)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("lang", "kr")
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
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
                weatherData.setFeelsLike((Double) main.get("feels_like"));
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

    // 5일간의 일기 예보를 가져와 Forecast 리스트로 반환
    public List<Forecast> getForecast(String cityName) {
        // 1. 도시 좌표 가져오기
        Map<String, Double> coordinates = getCoordinates(cityName);
        if (coordinates == null) {
            throw new RuntimeException("도시 정보를 찾을 수 없습니다.");
        }
    
        double lat = coordinates.get("lat").doubleValue();
        double lon = coordinates.get("lon").doubleValue();
    
        // 2. API URL 생성
        String url = UriComponentsBuilder.fromHttpUrl(FORECAST_API_URL)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("lang", "kr")
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();
    
        // 3. API 호출
        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> forecastList = (List<Map<String, Object>>) response.get("list");
    
            List<Forecast> forecasts = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul")); // 한국 표준시로 설정
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
    
            // 다음 4일 날짜 계산
            calendar.add(Calendar.DATE, 1);
            List<String> targetDates = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                targetDates.add(sdf.format(calendar.getTime()));
                calendar.add(Calendar.DATE, 1);
            }
    
            // 4. 데이터 추출 로직
            for (String targetDate : targetDates) {
                double temp = Double.NaN;
                double windSpeed = Double.NaN;
                int humidity = -1;
                String description = "";
                String iconCode = "";
    
                for (Map<String, Object> forecastData : forecastList) {
                    String dateTimeText = (String) forecastData.get("dt_txt");
    
                    // 12:00:00 시간 데이터에서 정보 추출
                    if (dateTimeText.startsWith(targetDate) && dateTimeText.endsWith("12:00:00")) {
                        Map<String, Object> mainData = (Map<String, Object>) forecastData.get("main");
                        temp = ((Number) mainData.get("temp")).doubleValue();
                        humidity = ((Number) mainData.get("humidity")).intValue();
    
                        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) forecastData.get("weather");
                        if (weatherList != null && !weatherList.isEmpty()) {
                            description = (String) weatherList.get(0).get("description");
                            iconCode = (String) weatherList.get(0).get("icon");
                        }
    
                        // wind data 추출
                        Map<String, Object> windData = (Map<String, Object>) forecastData.get("wind");
                        windSpeed = ((Number) windData.get("speed")).doubleValue();
                    }
                }
    
                // 데이터가 모두 채워졌을 경우 Forecast 객체 생성
                if (!Double.isNaN(temp) && windSpeed != Double.NaN && humidity != -1) {
                    Forecast forecast = new Forecast();
                    forecast.setDateTime(targetDate);
                    forecast.setTemp(temp);
                    forecast.setWindSpeed(windSpeed);
                    forecast.setHumidity(humidity);
                    forecast.setDescription(description);
                    forecast.setIconCode(iconCode);
                    forecasts.add(forecast);
                }
            }
    
            return forecasts;
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("City not found");
        }
    }

}