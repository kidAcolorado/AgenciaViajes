package com.viewnext.kidaprojects.agenciaviajes.restcontrollers;

import java.sql.Date;
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

import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTO;
import com.viewnext.kidaprojects.agenciaviajes.service.VueloService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/vuelo/")
public class VueloRestController {

	@Autowired
	private VueloService vueloService;

	/**
	 * Obtiene todos los vuelos disponibles.
	 *
	 * @return ResponseEntity con la lista de objetos VueloDTO que representa todos
	 *         los vuelos, o ResponseEntity vacío si no hay vuelos.
	 */
	@GetMapping("/mostrar/")
	public ResponseEntity<List<VueloDTO>> getAllVuelos() {
		List<VueloDTO> listaVuelosDTO;

		listaVuelosDTO = vueloService.getAllVuelos();

		return ResponseEntity.ok(listaVuelosDTO);
	}
	
	/**
	 * Obtiene un vuelo por su ID.
	 *
	 * @param id El ID del vuelo a buscar.
	 * @return ResponseEntity con el objeto VueloDTO si se encuentra el vuelo, o
	 *         ResponseEntity con código de estado Not Found y un mensaje de error
	 *         si no se encuentra.
	 */
	@GetMapping("/mostrar/{id}")
	public ResponseEntity<?> getVueloById(@PathVariable Integer id) {
		try {
			VueloDTO vueloDTO = vueloService.getVueloById(id);

			return ResponseEntity.ok(vueloDTO);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("El pasajero con el ID introducido no fue encontrado");

		}
	}


	/**
	 * Crea un nuevo vuelo.
	 *
	 * @param vueloDTO Objeto VueloDTO que representa los datos del nuevo vuelo a
	 *                 crear.
	 * @return ResponseEntity con el objeto VueloDTO que representa el vuelo creado,
	 *         o ResponseEntity vacío si no se pudo crear el vuelo.
	 */
	@PostMapping("/crear/")
	public ResponseEntity<VueloDTO> createVuelo(@RequestBody VueloDTO vueloDTO) {
		VueloDTO nuevoVueloDTO;

		nuevoVueloDTO = vueloService.createVuelo(vueloDTO);

		return ResponseEntity.ok(nuevoVueloDTO);
	}

	
	/**
	 * Elimina un vuelo por su ID.
	 *
	 * @param id ID del vuelo a eliminar.
	 * @return ResponseEntity con un mensaje de éxito si el vuelo se eliminó correctamente,
	 *         o ResponseEntity con estado 404 (No encontrado) y un mensaje de error si el vuelo no existe.
	 */
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<String> deleteVueloById(@PathVariable Integer id) {
		try {
			vueloService.deleteById(id);
			String mensajeExito = "El vuelo con ID introducido fue eliminado exitosamente.";
			return ResponseEntity.ok(mensajeExito);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("El pasajero con el ID no fue encontrado");
		}
	}

	/**
	 * Actualiza los datos de un vuelo existente en la base de datos utilizando el
	 * ID especificado.
	 * 
	 * @param idVuelo  El ID del vuelo que se desea actualizar.
	 * @param vueloDTO El objeto DTO que contiene los nuevos datos del vuelo.
	 * @return ResponseEntity con el objeto DTO del vuelo actualizado en caso de
	 *         éxito, o ResponseEntity con estado 404 (Not Found) en caso de que el
	 *         vuelo no exista.
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> updateVueloById(@PathVariable Integer id, @RequestBody VueloDTO vueloDTO) {
		try {
			vueloDTO = vueloService.updateVueloById(id, vueloDTO);

			return ResponseEntity.ok(vueloDTO);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("El vuelo con el ID introducido no fue encontrado");
		}

	}
	
	

	/**
	 * 
	 * Busca vuelos por origen, destino y fecha y devuelve una lista de vuelos
	 * filtrada.
	 * 
	 * @param origen  El origen del vuelo.
	 * @param destino El destino del vuelo.
	 * @param fecha   La fecha del vuelo.
	 * @return ResponseEntity con la lista de vuelos filtrada si se encuentran
	 *         coincidencias, o ResponseEntity con código de estado Not Found y un
	 *         mensaje de error si no se encuentran vuelos.
	 */
	@GetMapping("/buscar/params")
	public ResponseEntity<?> buscarVuelosPorOrigenDestinoFecha(@RequestParam("origen") String origen,
			@RequestParam("destino") String destino, @RequestParam("fecha") Date fecha) {
		try {
			List<VueloDTO> listaVuelosDTOFiltrada;

			listaVuelosDTOFiltrada = vueloService.buscarVuelosPorOrigenDestinoFecha(origen, destino, fecha);

			return ResponseEntity.ok(listaVuelosDTOFiltrada);

		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Vuelo con las características indicadas no encontrado");
		}

	}

}
