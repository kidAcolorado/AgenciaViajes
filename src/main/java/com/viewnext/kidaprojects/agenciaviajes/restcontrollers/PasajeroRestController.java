package com.viewnext.kidaprojects.agenciaviajes.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTO;
import com.viewnext.kidaprojects.agenciaviajes.service.PasajeroService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/pasajero/")
public class PasajeroRestController {

	@Autowired
	private PasajeroService pasajeroService;

	/**
	 * Obtiene todos los pasajeros.
	 *
	 * @return ResponseEntity con la lista de PasajeroDTO en el cuerpo de la
	 *         respuesta.
	 */
	@GetMapping("/mostrar/")
	public ResponseEntity<List<PasajeroDTO>> getAllPasajeros() {
		List<PasajeroDTO> listaPasajerosDTO;

		listaPasajerosDTO = pasajeroService.getAllPasajeros();

		return ResponseEntity.ok(listaPasajerosDTO);
	}
	
	/**
	 * Obtiene un pasajero por su ID.
	 * 
	 * @param id El ID del pasajero a buscar.
	 * @return ResponseEntity con el objeto PasajeroDTO si se encuentra el pasajero,
	 *         o ResponseEntity con código de estado Not Found y un mensaje de error
	 *         si no se encuentra.
	 */
	@GetMapping("/mostrar/{id}")
	public ResponseEntity<?> getPasajeroById(@PathVariable Integer id) {
		try {
			PasajeroDTO pasajeroDTO = pasajeroService.getPasajeroById(id);
			
			return ResponseEntity.ok(pasajeroDTO);
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("El pasajero con el ID introducido no fue encontrado");

		}
	}

	/**
	 * Crea un nuevo pasajero con los datos proporcionados.
	 *
	 * @param pasajeroDTO El objeto DTO que contiene los datos del pasajero a crear.
	 * @return ResponseEntity con el objeto DTO del nuevo pasajero creado.
	 */
	@PostMapping("/crear/")
	public ResponseEntity<PasajeroDTO> createPasajero(@RequestBody PasajeroDTO pasajeroDTO) {
		PasajeroDTO nuevoPasajeroDTO;

		nuevoPasajeroDTO = pasajeroService.createPasajero(pasajeroDTO);

		return ResponseEntity.ok(nuevoPasajeroDTO);
	}

	

	/**
	 * Elimina un pasajero por su ID.
	 *
	 * @param id ID del pasajero a eliminar.
	 * @return ResponseEntity con una cadena vacía en el cuerpo de la respuesta si
	 *         se elimina correctamente, o ResponseEntity con estado 404 (No
	 *         encontrado) y un mensaje de error si el pasajero no existe.
	 */
	/**
	 * Elimina un pasajero por su ID.
	 *
	 * @param id ID del pasajero a eliminar.
	 * @return ResponseEntity con un mensaje de éxito en el cuerpo de la respuesta si
	 *         se elimina correctamente, o ResponseEntity con estado 404 (No
	 *         encontrado) y un mensaje de error si el pasajero no existe.
	 */
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<String> deletePasajeroById(@PathVariable Integer id) {
	    try {
	        pasajeroService.deletePasajeroById(id);
	        String mensajeExito = "El Pasajero con ID introducido fue eliminado exitosamente.";
	        return ResponseEntity.ok(mensajeExito);
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("El pasajero con el ID introducido no fue encontrado");
	    }
	}



	/**
	 * Actualiza los datos de un pasajero existente en la base de datos utilizando
	 * el ID especificado.
	 * 
	 * @param idPasajeroUpdate El ID del pasajero que se desea actualizar.
	 * @param pasajeroDTO      El objeto DTO que contiene los nuevos datos del
	 *                         pasajero.
	 * @return ResponseEntity con el objeto DTO del pasajero actualizado en caso de
	 *         éxito, o ResponseEntity con status 404 (Not Found) en caso de que el
	 *         pasajero no exista.
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> updatePasajeroById(@PathVariable Integer id, @RequestBody PasajeroDTO pasajeroDTO) {
		
		try {
			pasajeroDTO = pasajeroService.updatePasajeroById(id, pasajeroDTO);

			return ResponseEntity.ok(pasajeroDTO);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("El pasajero con el ID introducido no fue encontrado");
		}
	}

}
