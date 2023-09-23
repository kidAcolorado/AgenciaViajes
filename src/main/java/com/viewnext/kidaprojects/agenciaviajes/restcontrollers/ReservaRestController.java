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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.kidaprojects.agenciaviajes.dto.ReservaDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.ReservaSoloIdDTO;

import com.viewnext.kidaprojects.agenciaviajes.service.ReservaService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/reserva/")
public class ReservaRestController {

	@Autowired
	private ReservaService reservaService;

	private static final String RESERVA_NOT_FOUND = "Reserva con las características indicadas no encontrada";
	private static final String IDVUELO_NOT_FOUND = "Reserva con ID de Vuelo introducido no encontrada";
	private static final String IDPASAJERO_NOT_FOUND = "Reserva con ID de Pasajero introducido no encontrada";
	private static final String INVALID_ID = "Id Proporcionado inválido";

	/**
	 * Obtiene todas las reservas.
	 *
	 * @return ResponseEntity con la lista de ReservaDTO en el cuerpo de la
	 *         respuesta.
	 */
	@GetMapping
	public ResponseEntity<List<ReservaDTO>> getAllReservas() {
		List<ReservaDTO> listaReservasDTO;

		listaReservasDTO = reservaService.getAllReservas();

		return ResponseEntity.ok(listaReservasDTO);
	}

	/**
	 * Obtiene una reserva por su ID.
	 * 
	 * @param id El ID de la reserva a obtener.
	 * @return ResponseEntity con el objeto ReservaDTO si se encuentra la reserva, o
	 *         ResponseEntity con código de estado Not Found y un mensaje de error
	 *         si no se encuentra.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getReservaById(@PathVariable String id) {

		try {
			Integer idNumerico = Integer.parseInt(id);

			ReservaDTO reservaDTO = reservaService.getReservaByid(idNumerico);

			return ResponseEntity.ok(reservaDTO);

		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RESERVA_NOT_FOUND);
		} catch (NumberFormatException e) {
			// En caso de que la conversión falle, responde con un código de estado 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		}
	}

	/**
	 * Obtiene las reservas por el ID de reserva.
	 * 
	 * @param id El ID de la reserva.
	 * @return ResponseEntity con la lista de ReservaDTO correspondiente a las
	 *         reservas encontradas, o ResponseEntity con código de estado Not Found
	 *         si no se encuentra una reserva para el ID de reserva especificado.
	 */
	@GetMapping("/mostrar/idreserva/{id}")
	public ResponseEntity<?> getReservaByIdDesdeFormulario(@PathVariable String id) {
		try {

			Integer idNumerico = Integer.parseInt(id);

			ReservaDTO reservaDTO = reservaService.getReservaByid(idNumerico);

			return ResponseEntity.ok(reservaDTO);

		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RESERVA_NOT_FOUND);
		} catch (NumberFormatException e) {
			// En caso de que la conversión falle, responde con un código de estado 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		}
	}

	/**
	 * Obtiene las reservas por el ID de vuelo.
	 * 
	 * @param id El ID del vuelo.
	 * @return ResponseEntity con la lista de ReservaDTO correspondiente a las
	 *         reservas encontradas, o ResponseEntity con código de estado Not Found
	 *         si no se encuentran reservas para el ID de vuelo especificado.
	 */
	@GetMapping("/mostrar/idvuelo/{id}")
	public ResponseEntity<?> getReservaByIdVuelo(@PathVariable String id) {
		try {
			List<ReservaDTO> listaReservasDTOPorIdVuelo;

			Integer idNumerico = Integer.parseInt(id);
			// Llama al servicio para obtener las reservas por el ID del vuelo
			listaReservasDTOPorIdVuelo = reservaService.obtenerReservasPorVuelo(idNumerico);

			// Retorna una respuesta con las reservas si se encontraron
			return ResponseEntity.ok(listaReservasDTOPorIdVuelo);

		} catch (EntityNotFoundException e) {
			// En caso de que no se encuentren reservas para el vuelo especificado, retorna
			// una respuesta con estado Not Found
			// y un mensaje de error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(IDVUELO_NOT_FOUND);

		} catch (NumberFormatException e) {
			// En caso de que la conversión falle, responde con un código de estado 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		}
	}

	/**
	 * Obtiene las reservas por el ID del pasajero.
	 * 
	 * @param id El ID del pasajero.
	 * @return ResponseEntity con la lista de ReservaDTO correspondiente a las
	 *         reservas encontradas, o ResponseEntity con código de estado Not Found
	 *         si no se encuentran reservas para el ID del pasajero especificado.
	 */
	@GetMapping("/mostrar/idpasajero/{id}")
	public ResponseEntity<?> getReservaByIdPasajero(@PathVariable String id) {
		try {
			List<ReservaDTO> listaReservasDTOPorIdPasajero;

			Integer idNumerico = Integer.parseInt(id);
			// Llama al servicio para obtener las reservas del pasajero por su ID
			listaReservasDTOPorIdPasajero = reservaService.obtenerReservasPorPasajero(idNumerico);

			// Retorna una respuesta con las reservas si se encontraron
			return ResponseEntity.ok(listaReservasDTOPorIdPasajero);

		} catch (EntityNotFoundException e) {
			// En caso de que no se encuentren reservas para el pasajero, retorna una
			// respuesta con estado Not Found
			// y un mensaje de error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(IDPASAJERO_NOT_FOUND);

		} catch (NumberFormatException e) {
			// En caso de que la conversión falle, responde con un código de estado 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		}
	}

	/**
	 * Crea una reserva a partir de los IDs de vuelo y pasajero, y el número de
	 * asiento proporcionados.
	 *
	 * @param idVuelo    El ID del vuelo asociado a la reserva.
	 * @param idPasajero El ID del pasajero asociado a la reserva.
	 * @param asiento    El número de asiento seleccionado para la reserva.
	 * @return ResponseEntity con el objeto DTO de la reserva creada o un mensaje de
	 *         error si no se encuentra el vuelo o el pasajero.
	 */
	@PostMapping
	public ResponseEntity<?> createReservaByIdVueloAndIdPasajeroAsiento(
			@RequestBody ReservaSoloIdDTO reservaSoloIdDTO) {

		try {
			ReservaDTO nuevaReservaDTO;

			Integer idVuelo = Integer.parseInt(reservaSoloIdDTO.getIdVueloDTO());
			Integer idPasajero = Integer.parseInt(reservaSoloIdDTO.getIdPasajeroDTO());
			String asiento = reservaSoloIdDTO.getAsiento();

			nuevaReservaDTO = reservaService.createReservaByIdVueloIdPasajeroAsiento(idVuelo, idPasajero, asiento);

			return ResponseEntity.status(HttpStatus.CREATED) // Configura el código de estado a CREATED (201)
					.body(nuevaReservaDTO); // Agrega el objeto DTO como cuerpo de la respuesta

		} catch (EntityNotFoundException e) {

			return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(RESERVA_NOT_FOUND);
			
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		}
	}

	/**
	 * Elimina una reserva por su ID.
	 *
	 * @param id ID de la reserva a eliminar.
	 * @return ResponseEntity con un mensaje de éxito si la reserva se eliminó
	 *         correctamente, o ResponseEntity con estado 404 (No encontrado) y un
	 *         mensaje de error si la reserva no existe.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteReservaById(@PathVariable String id) {
		try {
			// Intentar convertir el id de String a int
			Integer idNumerico = Integer.parseInt(id);
			// Llama al servicio para eliminar el pasajero por su ID
			reservaService.deleteReservaById(idNumerico);

			// Devuelve una respuesta sin contenido (204 No Content) para indicar éxito en
			// la eliminación
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			// En caso de que el pasajero no se encuentre, devuelve una respuesta 404 Not
			// Found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RESERVA_NOT_FOUND);
		} catch (NumberFormatException e) {
			// En caso de que la conversión falle, responde con un código de estado 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_ID);
		}
	}

	/**
	 * Actualiza una reserva por su ID utilizando los datos proporcionados en el
	 * objeto ReservaDTO.
	 *
	 * @param idReserva  El ID de la reserva a actualizar.
	 * @param idVuelo    El ID del vuelo asociado a la reserva.
	 * @param idPasajero El ID del pasajero asociado a la reserva.
	 * @param asiento    El número de asiento asociado a la reserva.
	 * @return ResponseEntity con el objeto ReservaDTO actualizado en el cuerpo de
	 *         la respuesta si la actualización es exitosa.
	 * @throws EntityNotFoundException si no se encuentra la reserva con el ID
	 *                                 especificado.
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<?> updateReservaByIdVueloIdPasajeroAsiento(@RequestParam Integer idReserva,
			@RequestParam Integer idVuelo, @RequestParam Integer idPasajero, @RequestParam String asiento) { // TO-DO
		try {
			// Obtiene la reserva a actualizar utilizando los IDs proporcionados
			ReservaDTO reservaDTOParaUpdate = reservaService.getReservaByIdVueloIdPasajeroAsiento(idVuelo, idPasajero,
					asiento);

			// Establece el ID de la reserva a actualizar
			reservaDTOParaUpdate.setIdReservaDTO(String.valueOf(idReserva));

			// Actualiza la reserva en el servicio y obtiene la versión actualizada
			reservaDTOParaUpdate = reservaService.updateReservaById(idReserva, reservaDTOParaUpdate);

			// Retorna una respuesta con status 200 (OK) y la reserva actualizada
			return ResponseEntity.ok(reservaDTOParaUpdate);

		} catch (EntityNotFoundException e) {
			// En caso de que no se encuentre la reserva, devuelve una respuesta 404 Not
			// Found
			// con un mensaje de error
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RESERVA_NOT_FOUND);
		}
	}

}
