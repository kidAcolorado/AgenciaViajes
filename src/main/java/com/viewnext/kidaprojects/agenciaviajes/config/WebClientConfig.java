package com.viewnext.kidaprojects.agenciaviajes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	private static final String BASE_URL = "http://localhost:8080";

    @Bean
    WebClient pasajeroWebClient() {
        String apiUrl = BASE_URL + "/api/pasajero/";
        return WebClient.create(apiUrl);
    }

    @Bean
    WebClient vueloWebClient() {
        String apiUrl = BASE_URL + "/api/vuelo/";
        return WebClient.create(apiUrl);
    }

    @Bean
    WebClient reservaWebClient() {
        String apiUrl = BASE_URL + "/api/reserva/";
        return WebClient.create(apiUrl);
    }
}