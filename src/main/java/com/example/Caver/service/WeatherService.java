package com.example.Caver.service;

import com.example.Caver.model.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    private final String KAKAO_GEOCODING_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private final String CURRENT_WEATHER_API_URL = "https://apihub.kma.go.kr/api/typ02/openApi/VilageFcstInfoService_2.0/getVilageFcst";

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
            Map<String, Object> address = documents.get(0);  // 첫 번째 주소 정보 추출
            Double x = Double.parseDouble(address.get("x").toString());
            Double y = Double.parseDouble(address.get("y").toString());
            Map<String, Double> coordinates = new HashMap<>();
            coordinates.put("x", y);
            coordinates.put("y", x);
            return coordinates;
        } else {
            return null;
        }
    }

    // 기상청 API를 사용하여 날씨 정보 가져오기
    public Weather getWeather(String city) {
        Map<String, Double> coordinates = getCoordinates(city);
        if (coordinates == null) {
            throw new RuntimeException("도시 정보를 찾을 수 없습니다.");
        }
    
        int nx = coordinates.get("x").intValue();
        int ny = coordinates.get("y").intValue();

        // 현재 시간
        SimpleDateFormat currentTimeSdf = new SimpleDateFormat("HH00");
        currentTimeSdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String currentTime = currentTimeSdf.format(new Date());
        System.out.printf("currentTime = %s\n", currentTime);

        // 현재 날짜를 구하기 (yyyymmdd 형식)
        SimpleDateFormat baseDateSdf = new SimpleDateFormat("yyyyMMdd");
        baseDateSdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String baseDate = baseDateSdf.format(new Date());

        String url = UriComponentsBuilder.fromHttpUrl(CURRENT_WEATHER_API_URL)
                .queryParam("numOfRows", 1000)
                .queryParam("pageNo", 1)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", "0500")
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .queryParam("authKey", weatherApiKey)
                .build()
                .toString();

        System.out.printf("URL : %s \n", url);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);

        // 기상청 API 응답을 파싱하여 Weather 객체 생성
        Map<String, Object> responseMap = (Map<String, Object>) response.getBody();
        Map<String, Object> body = (Map<String, Object>) responseMap.get("response");
        Map<String, Object> bodyItems = (Map<String, Object>) body.get("body");
        Map<String, Object> items = (Map<String, Object>) bodyItems.get("items");

        List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

        Weather weather = new Weather();

        // items를 순회하며 날씨 정보를 Weather 객체에 세팅
        // fcstTime, fcstDate, baseDate가 모두 일치하는 날씨 데이터를 필터링
        for (Map<String, Object> item : itemList) {
            String fcstTime = item.get("fcstTime").toString(); // fcstTime 값을 가져옴
            String fcstDate = item.get("fcstDate").toString(); // fcstDate 값을 가져옴

            if (fcstTime.equals(currentTime) && fcstDate.equals(baseDate)) {  // fcstTime과 fcstDate가 모두 일치하는 경우
                String category = item.get("category").toString();
                String fcstValue = item.get("fcstValue").toString();

                // 각 category에 따라 날씨 정보를 설정
                if ("TMP".equals(category)) {
                    weather.setTemperature(fcstValue);
                } else if ("WSD".equals(category)) {
                    weather.setWindSpeed(fcstValue);
                } else if ("REH".equals(category)) {
                    weather.setHumidity(fcstValue);
                } else if ("TMX".equals(category)) {
                    weather.setTempMax(fcstValue);
                } else if ("TMN".equals(category)) {
                    weather.setTempMin(fcstValue);
                }
            }
        }

        return weather;
    }
}