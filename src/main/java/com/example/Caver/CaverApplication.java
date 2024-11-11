package com.example.Caver;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaverApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("/home/yg/movefile/Caver").load();
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("NEWS_API_KEY", dotenv.get("NEWS_API_KEY"));
		System.setProperty("WEATHER_API_KEY", dotenv.get("WEATHER_API_KEY"));
		System.setProperty("STOCK_API_KEY", dotenv.get("STOCK_API_KEY"));

		SpringApplication.run(CaverApplication.class, args);
	}

}
