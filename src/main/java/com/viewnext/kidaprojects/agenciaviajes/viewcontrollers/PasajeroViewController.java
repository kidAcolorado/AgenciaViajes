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
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/formbuscar")
    public String mostrarFormularioBusquedaPasajero(Model model) {
        
        return "formularioBuscarPasajero";
    }

	/**
	 * Obtiene una lista de pasajeros desde el servicio web y muestra la vista que lista todos los pasajeros.
	 *
	 * @param model El modelo utilizado para pasar la lista de pasajeros a la vista.
	 * @return El nombre de la vista a mostrar.
	 */
    @GetMapping
    public String mostrarVistaListaPasajeros(Model model) {
        try {
            // Realiza una solicitud GET al servicio web para obtener una lista de pasajeros
            List<PasajeroDTO> listaPasajeros = pasajeroWebClient.get()
                    .retrieve()
                    .bodyToFlux(PasajeroDTO.class) // Convierte la respuesta en un flux de PasajeroDTO
                    .collectList() // Recolecta los elementos del flux en una lista
                    .block(); // Bloquea hasta que la operación esté completa y devuelve la lista

            // Agrega la lista de pasajeros al modelo para que esté disponible en la vista
            model.addAttribute("pasajeros", listaPasajeros);

            // Retorna el nombre de la vista que mostrará la lista de pasajeros
            return "vistaMostrarPasajeros";
        } catch (WebClientResponseException e) {
            // En caso de un error al comunicarse con el servicio web, agrega un mensaje de error al modelo y retorna una vista de error
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
	@GetMapping("/id")
	public String mostrarVistaPasajeroById(@RequestParam(name = "id") String id, Model model) {
	    try {
	        // Obtiene el ID del pasajero del objeto PasajeroDTO recibido como parámetro
	        Integer idNumerico = Integer.parseInt(id);
	        
	        // Realiza una solicitud GET al servicio web para obtener los detalles del pasajero por su ID
	        PasajeroDTO pasajero = pasajeroWebClient.get()
	                .uri("/{id}", idNumerico)
	                .retrieve()
	                .bodyToMono(PasajeroDTO.class)
	                .block();
	        
	        // Agrega el objeto PasajeroDTO al modelo para que esté disponible en la vista
	        model.addAttribute("pasajero", pasajero);
	        
	        // Retorna el nombre de la vista que mostrará los detalles del pasajero
	        return "vistaMostrarPasajeroById";
	    } catch (NumberFormatException e) {
	        // En caso de un error de conversión de ID, agrega un mensaje de error al modelo y retorna una vista de error
	        model.addAttribute(MENSAJE, ID_ERRONEO);
	        return VISTA_ERROR;
	    } catch (WebClientResponseException e) {
	        // En caso de un error al comunicarse con el servicio web, agrega un mensaje de error al modelo y retorna una vista de error
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
    @GetMapping("/formcrear")
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
	@PostMapping("/crear/")
	public String createPasajero(@ModelAttribute("pasajeroDTO") PasajeroDTO pasajeroDTO, Model model) {
		try {
			ResponseEntity<Void> response = pasajeroWebClient.post()
					.contentType(MediaType.APPLICATION_JSON)
					.bodyValue(pasajeroDTO)
					.retrieve()
					.toBodilessEntity()
					.block();

			if (response != null && response.getStatusCode().is2xxSuccessful()) {
	            model.addAttribute(MENSAJE, "El Pasajero fue creado exitosamente");
	            model.addAttribute("nuevoPasajero", pasajeroDTO);
	            return "VistaCrearPasajero"; // Redireccionar a la lista de pasajeros
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
    @PostMapping("/borrar/")
    public String borrarPasajero(@ModelAttribute PasajeroDTO pasajeroDTO, Model model) {
        try {
            String id = pasajeroDTO.getIdPasajeroDTO();

            // Realiza la eliminación utilizando WebClient y el ID del pasajero
            ResponseEntity<String> response = pasajeroWebClient.delete()
                    .uri("/{id}", id)
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
        	
            Integer id = Integer.parseInt(pasajeroDTO.getIdPasajeroDTO());

            // Realiza la actualización utilizando WebClient y el ID del pasajero
            ResponseEntity<Void> response = pasajeroWebClient.put()
                    .uri("/{id}", id)
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


