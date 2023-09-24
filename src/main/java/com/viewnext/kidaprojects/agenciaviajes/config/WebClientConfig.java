package com.viewnext.kidaprojects.agenciaviajes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * La clase {@code WebClientConfig} configura los clientes WebClient para interactuar con
 * servicios web externos.
 * 
 * <p>
 * Esta clase define tres instancias de WebClient: {@code pasajeroWebClient}, {@code vueloWebClient},
 * y {@code reservaWebClient}, cada uno configurado para interactuar con un servicio web específico.
 * </p>
 * 
 * <p>
 * Los clientes WebClient se utilizan para realizar solicitudes HTTP a las API de servicios web externos.
 * La configuración incluye la URL base del servicio web al que se va a acceder.
 * </p>
 * 
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
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