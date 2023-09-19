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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.kidaprojects.agenciaviajes.dto.ReservaDTO;
import com.viewnext.kidaprojects.agenciaviajes.service.ReservaService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/reserva/")
public class ReservaRestController {

	@Autowired
	private ReservaService reservaService;

	/**
	 * Obtiene todas las reservas.
	 *
	 * @return ResponseEntity con la lista de ReservaDTO en el cuerpo de la
	 *         respuesta.
	 */
	@GetMapping("/mostrar/")
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
	 *         un mensaje de error si no se encuentra.
	 */
	@GetMapping("/mostrar/{id}")
	public ResponseEntity<?> getReservaById(@PathVariable Integer id) {
		try {
			ReservaDTO reservaDTO;

			reservaDTO = reservaService.getReservaByid(id);

			return ResponseEntity.ok(reservaDTO);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("La Reserva con ID introducido no fue encontrada");

		}
	}

	/**
	 * Obtiene las reservas por el ID de vuelo.
	 * 
	 * @param id El ID del vuelo.
	 * @return ResponseEntity con la lista de ReservaDTO correspondiente a las
	 *         reservas encontradas.
	 * @throws EntityNotFoundException si no se encuentran reservas para el ID de
	 *                                 vuelo especificado.
	 */
	@GetMapping("/mostrar/idvuelo/{id}")
	public ResponseEntity<?> getReservaByIdVuelo(@PathVariable Integer id) {
		try {
			List<ReservaDTO> listaReservasDTOPorIdVuelo;

			listaReservasDTOPorIdVuelo = reservaService.obtenerReservasPorVuelo(id);

			return ResponseEntity.ok(listaReservasDTOPorIdVuelo);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No fueron encontradas Reservas con Vuelo cuyo ID sea el introducido");

		}
	}

	/**
	 * Obtiene las reservas por el ID del pasajero.
	 * 
	 * @param id El ID del pasajero.
	 * @return ResponseEntity con la lista de ReservaDTO correspondiente a las
	 *         reservas encontradas.
	 * @throws EntityNotFoundException si no se encuentran reservas para el ID del
	 *                                 pasajero especificado.
	 */
	@GetMapping("/mostrar/idpasajero/{id}")
	public ResponseEntity<?> getReservaByIdPasajero(@PathVariable Integer id) {
		try {
			List<ReservaDTO> listaReservasDTOPorIdPasajero;
			
			listaReservasDTOPorIdPasajero = reservaService.obtenerReservasPorPasajero(id);

			return ResponseEntity.ok(listaReservasDTOPorIdPasajero);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No fueron encontradas Reservas con Pasajero cuyo ID sea el introducido");

		}
	}

	/**
	 * Elimina un vuelo por su ID.
	 *
	 * @param id ID del vuelo a eliminar.
	 * @return ResponseEntity con un mensaje de éxito si el vuelo se eliminó correctamente,
	 *         o ResponseEntity con estado 404 (No encontrado) y un mensaje de error si el vuelo no existe.
	 */
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<String> deleteReservaById(@PathVariable Integer id) {
		try {
			reservaService.deleteById(id);
			String mensajeExito = "La Reserva con ID introducido fue eliminada exitosamente.";
			return ResponseEntity.ok(mensajeExito);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("La Reserva con el ID introducido no fue encontrada");
		}
	}

	/**
	 * Actualiza una reserva por su ID utilizando los datos proporcionados en el
	 * objeto ReservaDTO.
	 * 
	 * @param id         El ID de la reserva a actualizar.
	 * @param reservaDTO El objeto ReservaDTO con los datos actualizados.
	 * @return ResponseEntity con el objeto ReservaDTO actualizado en el cuerpo de
	 *         la respuesta si la actualización es exitosa.
	 * @throws EntityNotFoundException si no se encuentra la reserva con el ID
	 *                                 especificado.
	 */
	@PutMapping("/actualizar/params")
	public ResponseEntity<?> updateReservaByIdVueloIdPasajeroAsiento(@RequestParam Integer idReserva, @RequestParam Integer idVuelo,
			@RequestParam Integer idPasajero, @RequestParam String asiento) {
		try {
			ReservaDTO reservaDTOParaUpdate;
			
			reservaDTOParaUpdate = reservaService.getReservaByIdVueloIdPasajeroAsiento(idVuelo, idPasajero, asiento);
			
			reservaDTOParaUpdate.setIdReservaDTO(idReserva);
			
			reservaDTOParaUpdate = reservaService.updateReservaById(idReserva, reservaDTOParaUpdate);

			return ResponseEntity.ok(reservaDTOParaUpdate);

		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("La Reserva con el ID introducido no fue encontrada");
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
	@PostMapping("/crear/params")
	public ResponseEntity<?> createReservaByIdVueloAndIdPasajeroAsiento(@RequestParam Integer idVuelo,
			@RequestParam Integer idPasajero, @RequestParam String asiento) {

		try {
			ReservaDTO reservaDTO;

			reservaDTO = reservaService.createReservaByIdVueloIdPasajeroAsiento(idVuelo, idPasajero, asiento);

			return ResponseEntity.ok(reservaDTO);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("No se encontraron pasajeros o vuelos con los IDs introducidos");

		}
	}
}
