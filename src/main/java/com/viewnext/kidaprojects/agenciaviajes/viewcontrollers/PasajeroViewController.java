package com.viewnext.kidaprojects.agenciaviajes.viewcontrollers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTO;

@Controller
@RequestMapping("/pasajero/")
public class PasajeroViewController {
	private static final String MENSAJE = "mensaje";
	private static final String VISTA_ERROR = "vistaError"; 
	private static final String ID_ERRONEO = "ID de pasajero no válido";
	private static final String FALLO_CONEXION_WEBCLIENT = "Error al comunicarse con el servicio";
	
	private final WebClient pasajeroWebClient;

	/**
	 * Constructor que recibe un WebClient para realizar peticiones al servicio web
	 * de pasajeros.
	 *
	 * @param pasajeroWebClient El WebClient configurado para comunicarse con el
	 *                          servicio web de pasajeros.
	 */
	public PasajeroViewController(WebClient pasajeroWebClient) {
		this.pasajeroWebClient = pasajeroWebClient;
	}
	
	/**
     * Muestra la vista del formulario para buscar pasajeros.
     *
     * @param model El modelo utilizado para realizar cualquier lógica adicional antes de mostrar el formulario.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/buscar")
    public String mostrarFormularioBusquedaPasajero(Model model) {
        
        return "formularioBuscarPasajero";
    }

	/**
	 * Obtiene una lista de pasajeros desde el servicio web y muestra la vista que lista todos los pasajeros.
	 *
	 * @param model El modelo utilizado para pasar la lista de pasajeros a la vista.
	 * @return El nombre de la vista a mostrar.
	 */
	@GetMapping("/mostrar/")
	public String mostrarVistaListaPasajeros(Model model) {
	    try {
	        List<PasajeroDTO> listaPasajeros = pasajeroWebClient.get()
	                .uri("/mostrar/")
	                .retrieve()
	                .bodyToFlux(PasajeroDTO.class)
	                .collectList()
	                .block();
	        model.addAttribute("pasajeros", listaPasajeros);
	        return "vistaMostrarPasajeros";
	    } catch (WebClientResponseException e) {
	        model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
	        return VISTA_ERROR;
	    }
	}


    
	/**
	 * Obtiene un pasajero por su ID desde el servicio web y muestra la vista de un pasajero por su ID.
	 *
	 * @param id    El ID del pasajero a mostrar.
	 * @param model El modelo utilizado para pasar el pasajero a la vista.
	 * @return El nombre de la vista a mostrar.
	 */
	@GetMapping("/mostrar/pasajero")
	public String mostrarVistaPasajeroById(@ModelAttribute PasajeroDTO pasajeroDTO, Model model) {
	    try {
	        Integer idNumerico = pasajeroDTO.getIdPasajeroDTO();
	        PasajeroDTO pasajero = pasajeroWebClient.get()
	                .uri("/mostrar/{id}", idNumerico)
	                .retrieve()
	                .bodyToMono(PasajeroDTO.class)
	                .block();
	        model.addAttribute("pasajero", pasajero);
	        return "vistaMostrarpasajeroPorId";
	    } catch (NumberFormatException e) {
	        model.addAttribute(MENSAJE, ID_ERRONEO);
	        return VISTA_ERROR;
	    } catch (WebClientResponseException e) {
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
	}

    
    /**
     * Muestra la vista del formulario para crear un nuevo pasajero.
     *
     * @param model El modelo utilizado para pasar un objeto PasajeroDTO vacío a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/crear/")
    public String mostrarFormularioCrearPasajero(Model model) {
        model.addAttribute("pasajeroDTO", new PasajeroDTO());
        return "formularioCrearPasajero";
    }

	/**
	 * Crea un nuevo pasajero utilizando el servicio web de pasajeros.
	 *
	 * @param pasajeroDTO El objeto PasajeroDTO con los datos del nuevo pasajero a
	 *                    crear.
	 * @param model       El modelo utilizado para pasar mensajes y objetos a la
	 *                    vista.
	 * @return El nombre de la vista a mostrar después de la creación.
	 */
	@PostMapping("/crear/crearpasajero")
	public String createPasajero(@ModelAttribute("pasajeroDTO") PasajeroDTO pasajeroDTO, Model model) {
		try {
			ResponseEntity<Void> response = pasajeroWebClient.post()
					.uri("/crear/")
					.contentType(MediaType.APPLICATION_JSON)
					.bodyValue(pasajeroDTO)
					.retrieve()
					.toBodilessEntity()
					.block();

			if (response != null && response.getStatusCode().is2xxSuccessful()) {
	            model.addAttribute(MENSAJE, "El Pasajero fue creado exitosamente");
	            model.addAttribute("nuevoPasajero", pasajeroDTO);
	            return "VistaCrearpasajero"; // Redireccionar a la lista de pasajeros
			} else {
				model.addAttribute(MENSAJE, "Error al crear el Pasajero");
				return VISTA_ERROR;
			}
		} catch (WebClientResponseException e) {
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
	}

	   
    
    /**
     * Elimina un pasajero por su ID.
     *
     * @param id    El ID del pasajero a eliminar.
     * @param model El modelo utilizado para pasar mensajes de éxito o error a la vista.
     * @return El nombre de la vista a mostrar después de la eliminación.
     */
    @PostMapping("/borrar")
    public String borrarPasajero(@ModelAttribute PasajeroDTO pasajeroDTO, Model model) {
        try {
            Integer idNumerico = pasajeroDTO.getIdPasajeroDTO();

            // Realiza la eliminación utilizando WebClient y el ID del pasajero
            ResponseEntity<String> response = pasajeroWebClient.delete()
                    .uri("/borrar/{id}", idNumerico)
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            if (response !=null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, "El Pasajero con ID introducido fue eliminado exitosamente");
                
               return "VistaBorrarPasajero"; 
            } else {
            	model.addAttribute(MENSAJE, "Error al borrar el Pasajero");
                return VISTA_ERROR; // Maneja otros errores posibles
            }
        } catch (NumberFormatException e) {
            model.addAttribute(MENSAJE, ID_ERRONEO);
            return VISTA_ERROR;
        } catch (WebClientResponseException e) {
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
    }

    /**
     * Actualiza la información de un pasajero.
     *
     * @param pasajeroDTO El objeto PasajeroDTO con los datos actualizados del pasajero.
     * @param model       El modelo utilizado para pasar mensajes de éxito o error a la vista.
     * @return El nombre de la vista a mostrar después de la actualización.
     */
    @PostMapping("/actualizar/")
    public String actualizarPasajero(@ModelAttribute PasajeroDTO pasajeroDTO, Model model) {
        try {
            Integer id = pasajeroDTO.getIdPasajeroDTO();

            // Realiza la actualización utilizando WebClient y el ID del pasajero
            ResponseEntity<Void> response = pasajeroWebClient.put()
                    .uri("/actualizar/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(pasajeroDTO)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, "El Pasajero con ID introducido fue actualizado exitosamente");
                // Asigna el objeto PasajeroDTO actualizado al modelo
                model.addAttribute("pasajeroDTO", pasajeroDTO);

                return "VistaActualizarPasajero";
            } else {
            	model.addAttribute(MENSAJE, "Error al actualizar el Pasajero");
                return VISTA_ERROR;
            }
        } catch (NumberFormatException e) {
            model.addAttribute(MENSAJE, ID_ERRONEO);
            return VISTA_ERROR;
        } catch (WebClientResponseException e) {
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
    }
}


