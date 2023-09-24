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

import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTOSinId;
import com.viewnext.kidaprojects.agenciaviajes.service.PasajeroService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST que maneja las operaciones relacionadas con los pasajeros.
 * 
 * <p>
 * La clase {@code PasajeroRestController} define una serie de endpoints para realizar operaciones CRUD
 * relacionadas con los pasajeros. Proporciona métodos para obtener todos los pasajeros, obtener un pasajero
 * por su ID, crear un nuevo pasajero, eliminar un pasajero por su ID, actualizar un pasajero por su ID y eliminar
 * un pasajero a partir de los datos proporcionados en el cuerpo de la solicitud.
 * </p>
 *
 * <p>
 * Además, este controlador maneja casos de error, como la respuesta con un código de estado 404 (Not Found) cuando
 * un pasajero no se encuentra en la base de datos o un código de estado 400 (Bad Request) cuando se proporciona
 * un ID no válido.
 * </p>
 * 
 *  <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
@RestController
@RequestMapping("/api/pasajero/")
public class PasajeroRestController {

	@Autowired
	private PasajeroService pasajeroService;

	private static final String PASAJERO_NOT_FOUND = "El pasajero con el ID introducido no fue encontrado";
	private static final String INVALID_ID = "Id Proporcionado inválido";

	/**
	 * Obtiene todos los pasajeros.
	 *
	 * @return ResponseEntity con la lista de PasajeroDTO en el cuerpo de la
	 *         respuesta.
	 */
	@GetMapping
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
	@GetMapping("/{id}")
	public ResponseEntity<?> getPasajeroById(@PathVariable String id) {
		try {
			Integer idNumerico = Integer.parseInt(id);

			PasajeroDTO pasajeroDTO = pasajeroService.getPasajeroById(idNumerico);

			return ResponseEntity.ok(pasajeroDTO);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PASAJERO_NOT_FOUND);

		} catch (NumberFormatException e) {
			// En caso de que la conversión falle, responde con un código de estado 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		}
	}

	/**
	 * Crea un nuevo pasajero con los datos proporcionados.
	 *
	 * @param pasajeroDTO El objeto DTO que contiene los datos del pasajero a crear.
	 * @return ResponseEntity con el objeto DTO del nuevo pasajero creado.
	 */
	@PostMapping
	public ResponseEntity<PasajeroDTO> createPasajero(@RequestBody PasajeroDTOSinId pasajeroDTOSinId) {
		PasajeroDTO nuevoPasajeroDTO;

		nuevoPasajeroDTO = pasajeroService.createPasajero(pasajeroDTOSinId);

		return ResponseEntity.ok(nuevoPasajeroDTO);
	}

	/**
	 * Elimina un pasajero por su ID.
	 *
	 * @param id ID del pasajero a eliminar.
	 * @return ResponseEntity con un mensaje de éxito en el cuerpo de la respuesta
	 *         si se elimina correctamente, o ResponseEntity con estado 404 (No
	 *         encontrado) y un mensaje de error si el pasajero no existe.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePasajeroById(@PathVariable String id) {
		try {
			// Intentar convertir el id de String a int
			Integer idNumerico = Integer.parseInt(id);
			// Llama al servicio para eliminar el pasajero por su ID
			pasajeroService.deletePasajeroById(idNumerico);

			// Devuelve una respuesta sin contenido (204 No Content) para indicar éxito en
			// la eliminación
			return ResponseEntity.noContent().build();

		} catch (EntityNotFoundException e) {
			// En caso de que el pasajero no se encuentre, devuelve una respuesta 404 Not
			// Found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PASAJERO_NOT_FOUND);
		} catch (NumberFormatException e) {
			// En caso de que la conversión falle, responde con un código de estado 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		}
	}

	/**
	 * Actualiza los datos de un pasajero existente en la base de datos utilizando
	 * el ID especificado.
	 * 
	 * @param id          El ID del pasajero que se desea actualizar.
	 * @param pasajeroDTO El objeto DTO que contiene los nuevos datos del pasajero.
	 * @return ResponseEntity con el objeto DTO del pasajero actualizado en caso de
	 *         éxito, o ResponseEntity con status 404 (Not Found) en caso de que el
	 *         pasajero no exista.
	 */
	@PutMapping
	public ResponseEntity<?> updatePasajeroById(@RequestBody PasajeroDTO pasajeroDTO) {
		try {
			Integer idNumerico = Integer.parseInt(pasajeroDTO.getIdPasajeroDTO());

			pasajeroService.updatePasajeroById(idNumerico, pasajeroDTO);

			// Devuelve una respuesta con status 200 (OK) y el objeto DTO del pasajero
			// actualizado
			return ResponseEntity.ok(pasajeroDTO);

		} catch (NumberFormatException e) {
			// En caso de que la conversión falle, responde con un código de estado 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		} catch (EntityNotFoundException e) {
			// En caso de que el pasajero no se encuentre, devuelve una respuesta 404 Not
			// Found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PASAJERO_NOT_FOUND);
		}
	}

	/**
	 * Elimina un pasajero a partir de los datos proporcionados en el cuerpo de la
	 * solicitud.
	 *
	 * @param pasajeroDTO El objeto PasajeroDTO que contiene los datos del pasajero
	 *                    a eliminar.
	 * @return ResponseEntity con el estado HTTP correspondiente: 204 No Content en
	 *         caso de éxito, 404 Not Found si el pasajero no se encuentra.
	 */
	@PostMapping("/borrar")
	public ResponseEntity<String> deletePasajero(@RequestBody PasajeroDTO pasajeroDTO) {
		try {
			// Llama al servicio para eliminar el pasajero por su ID
			pasajeroService.delete(pasajeroDTO);

			// Devuelve una respuesta sin contenido (204 No Content) para indicar éxito en
			// la eliminación
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			// En caso de que el pasajero no se encuentre, devuelve una respuesta 404 Not
			// Found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PASAJERO_NOT_FOUND);
		}
	}

}
