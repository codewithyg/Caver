async function fetchWeatherData() {
    try {
        const response = await fetch('/api/weather');
        const data = await response.json();

        // 데이터로 화면 요소 업데이트
        document.getElementById('location').textContent = data.location;
        document.getElementById('description').textContent = data.description;
        document.getElementById('temperature').textContent = data.temperature;
        document.getElementById('tempMin').textContent = data.tempMin;
        document.getElementById('tempMax').textContent = data.tempMax;
        document.getElementById('humidity').textContent = data.humidity;
        document.getElementById('windSpeed').textContent = data.windSpeed;
        document.getElementById('weather-icon-img').src = `http://openweathermap.org/img/wn/${data.iconCode}@2x.png`;

    } catch (error) {
        console.error('Error fetching weather data:', error);
    }
}

// 페이지 로드 시 데이터 가져오기
fetchWeatherData();

// 주기적으로 데이터 갱신 (예: 5분마다)
setInterval(fetchWeatherData, 300000);
