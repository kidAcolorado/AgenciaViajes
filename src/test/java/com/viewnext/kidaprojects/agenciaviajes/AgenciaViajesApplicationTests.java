package com.viewnext.kidaprojects.agenciaviajes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static io.restassured.RestAssured.given;
import com.viewnext.kidaprojects.agenciaviajes.model.Pasajero;
import com.viewnext.kidaprojects.agenciaviajes.repository.PasajeroRepository;
import com.viewnext.kidaprojects.agenciaviajes.service.PasajeroService;
import com.viewnext.kidaprojects.agenciaviajes.viewcontrollers.PasajeroViewController;


@DataJpaTest
class AgenciaViajesApplicationTests {

    @Autowired
    private PasajeroViewController pasajeroController;

    @Autowired
    private PasajeroService pasajeroService;

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @Test
    void contextLoads() {
        assertThat(pasajeroController).isNotNull();
    }

    @Test
    void testBuscarPasajeroPorId() {
        Optional<Pasajero> optionalPasajero = pasajeroService.findById(1); // Reemplaza con un ID existente
        if (optionalPasajero.isPresent()) {
        	Pasajero pasajero = optionalPasajero.get();
        	assertEquals("Juan", pasajero.getNombre()); // Verifica si el nombre es el esperado
        }
        
    }

    @Test
    void testObtenerPasajeroPorId() {
        given()
            .when()
            .get("/api/pasajero/1") // Reemplaza con la ruta real del endpoint
            .then()
            .statusCode(200) // Verifica el código de respuesta
            .body("nombre", equalTo("Juan")); // Verifica el contenido esperado
    }

    @Test
    void testGuardarYRecuperarPasajero() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Ashitaka");

        pasajeroRepository.save(pasajero);
        Pasajero recuperado = pasajeroRepository.findById(pasajero.getIdPasajero()).orElse(null);

        assertEquals("Ashitaka", recuperado.getNombre());
    }

    
}

