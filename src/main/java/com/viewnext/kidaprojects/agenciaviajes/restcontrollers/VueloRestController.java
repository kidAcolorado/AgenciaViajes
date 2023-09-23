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
import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTOSinId;
import com.viewnext.kidaprojects.agenciaviajes.service.VueloService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/vuelo/")
public class VueloRestController {

	@Autowired
	private VueloService vueloService;

	private static final String VUELO_NOT_FOUND = "Vuelo con las características indicadas no encontrado";

	/**
	 * Obtiene todos los vuelos disponibles.
	 *
	 * @return ResponseEntity con la lista de objetos VueloDTO que representa todos
	 *         los vuelos, o ResponseEntity vacío si no hay vuelos.
	 */
	@GetMapping
	public ResponseEntity<List<VueloDTO>> getAllVuelos() {
		List<VueloDTO> listaVuelosDTO;

		listaVuelosDTO = vueloService.getAllVuelos();

		return ResponseEntity.ok(listaVuelosDTO);
	}

	/**
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
	@GetMapping("/buscar")
	public ResponseEntity<?> buscarVuelosPorOrigenDestinoFecha(@RequestParam("origen") String origen,
	        @RequestParam("destino") String destino, @RequestParam("fecha") Date fecha) {
	    try {
	        // Llama al servicio para buscar vuelos por origen, destino y fecha
	        List<VueloDTO> listaVuelosDTOFiltrada = vueloService.buscarVuelosPorOrigenDestinoFecha(origen, destino, fecha);

	        // Si se encuentran coincidencias, devuelve una respuesta con status 200 (OK)
	        // y la lista de vuelos filtrada
	        return ResponseEntity.ok(listaVuelosDTOFiltrada);

	    } catch (EntityNotFoundException e) {
	        // En caso de que no se encuentren vuelos que coincidan, devuelve una respuesta
	        // 404 Not Found con un mensaje de error
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(VUELO_NOT_FOUND);
	    }
	}


	/**
	 * Obtiene un vuelo por su ID.
	 *
	 * @param id El ID del vuelo a buscar.
	 * @return ResponseEntity con el objeto VueloDTO si se encuentra el vuelo, o
	 *         ResponseEntity con código de estado Not Found y un mensaje de error
	 *         si no se encuentra.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getVueloById(@PathVariable Integer id) {
		try {
			VueloDTO vueloDTO = vueloService.getVueloById(id);

			return ResponseEntity.ok(vueloDTO);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(VUELO_NOT_FOUND);

		}
	}

	/**
	 * Crea un nuevo vuelo.
	 *
	 * @param vueloDTO Objeto DTO que representa los datos del nuevo vuelo a crear.
	 * @return ResponseEntity con el objeto VueloDTO que representa el vuelo creado,
	 *         o ResponseEntity vacío si no se pudo crear el vuelo.
	 */
	@PostMapping
	public ResponseEntity<VueloDTO> createVuelo(@RequestBody VueloDTOSinId vueloDTOsDtoSinId) {
		VueloDTO nuevoVueloDTO;

		nuevoVueloDTO = vueloService.createVuelo(vueloDTOsDtoSinId);

		return ResponseEntity.status(HttpStatus.CREATED) // Configura el código de estado a CREATED (201)
				.body(nuevoVueloDTO); // Agrega el objeto DTO como cuerpo de la respuesta
	}

	/**
	 * Elimina un vuelo por su ID.
	 *
	 * @param id ID del vuelo a eliminar.
	 * @return ResponseEntity con un mensaje de éxito si el vuelo se eliminó correctamente, o ResponseEntity
	 *         con estado 404 (No encontrado) y un mensaje de error si el vuelo no existe.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteVueloById(@PathVariable Integer id) {
	    try {
	        // Llama al servicio para eliminar el vuelo por su ID
	        vueloService.deleteVueloById(id);

	        // Devuelve una respuesta sin contenido (204 No Content) para indicar éxito en la eliminación
	        return ResponseEntity.noContent().build();
	    } catch (EntityNotFoundException e) {
	        // En caso de que el vuelo no se encuentre, devuelve una respuesta 404 Not Found
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(VUELO_NOT_FOUND);
	    }
	}


	/**
	 * Actualiza los datos de un vuelo existente en la base de datos utilizando el
	 * ID especificado.
	 * 
	 * @param id       El ID del vuelo que se desea actualizar.
	 * @param vueloDTO El objeto DTO que contiene los nuevos datos del vuelo.
	 * @return ResponseEntity con el objeto DTO del vuelo actualizado en caso de
	 *         éxito, o ResponseEntity con estado 404 (Not Found) en caso de que el
	 *         vuelo no exista.
	 */
	@PutMapping
	public ResponseEntity<?> updateVueloById(@RequestBody VueloDTO vueloDTO) {
	    try {
	    	
	    	Integer idNumerico = Integer.parseInt(vueloDTO.getIdVueloDTO());
	        // Llama al servicio para actualizar los datos del vuelo por su ID
	        vueloService.updateVueloById(idNumerico, vueloDTO);

	        // Devuelve una respuesta con status 200 (OK) y el objeto DTO del vuelo actualizado
	        return ResponseEntity.ok(vueloDTO);
	    } catch (EntityNotFoundException e) {
	        // En caso de que el vuelo no se encuentre, devuelve una respuesta 404 Not Found
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(VUELO_NOT_FOUND);
	    }
	}

}
