package com.viewnext.kidaprojects.agenciaviajes.viewcontrollers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.ReservaDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.ReservaSoloIdDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTO;


/**
 * Clase que representa un controlador para las vistas relacionadas con las operaciones de reserva.
 * 
 * <p>
 * Este controlador se encarga de gestionar las solicitudes relacionadas con las operaciones de reserva
 * en el sistema. Proporciona métodos para mostrar formularios, obtener y mostrar información
 * sobre reservas, crear nuevas reservas, actualizar información de reservas y eliminar reservas
 * utilizando el servicio web de reservas.
 * </p>
 * 
 * <p>
 * Las constantes definidas en esta clase se utilizan para mensajes de error y nombres de vistas
 * en la interfaz de usuario.
 * </p>
 * 
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
@Controller
@RequestMapping("/reserva/")
public class ReservaViewController {

	// Constantes para los mensajes y nombres de las vistas
    private static final String MENSAJE = "mensaje";
    private static final String VISTA_ERROR = "vistaError"; 
    private static final String VISTA_NOT_FOUND = "vistaNotFound";
    private static final String VISTA_BAD_REQUEST = "vistaBadRequest";
    
    private static final String RESERVA_NOT_FOUND = "Reserva no encontrada";
    private static final String RESERVA_BAD_REQUEST = "Se esperaban unos argumentos distintos en la solictud";
    private static final String RESERVA_CREATED = "La reserva, con los datos que se muestran a continuación, fue creada exitosamente";
    private static final String RESERVA_UPDATED = "Datos de la Reserva actualizados con éxito";
    private static final String RESERVA_DELETED = "Reserva eliminada con éxito";
    private static final String FALLO_CONEXION_WEBCLIENT = "Error al comunicarse con el servicio";
    private static final String FALLO_NULL = "La respuesta del servidor es nula o no se pudo mapear al tipo esperado.";

	private final WebClient reservaWebClient;
	private final WebClient vueloWebClient;
	private final WebClient pasajeroWebClient;

	 /**
     * Constructor que recibe WebClients para realizar peticiones al servicio web de vuelos, pasajeros y reservas.
     *
     * @param vueloWebClient    El WebClient configurado para comunicarse con el servicio web de vuelos.
     * @param pasajeroWebClient El WebClient configurado para comunicarse con el servicio web de pasajeros.
     * @param reservaWebClient  El WebClient configurado para comunicarse con el servicio web de reservas.
     */
    public ReservaViewController(WebClient vueloWebClient, WebClient pasajeroWebClient, WebClient reservaWebClient) {
        this.vueloWebClient = vueloWebClient;
        this.pasajeroWebClient = pasajeroWebClient;
        this.reservaWebClient = reservaWebClient;
    }

    /**
     * Muestra la vista del formulario para buscar reservas.
     *
     * @param model El modelo utilizado para realizar cualquier lógica adicional antes de mostrar el formulario.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/formbuscar")
    public String mostrarFormularioBusquedaReserva(Model model) {
        return "formularioBuscarReserva";
    }

    /**
     * Muestra la vista del formulario para crear una nueva reserva.
     *
     * @param model El modelo utilizado para pasar listas de vuelos y pasajeros a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/formcrear")
    public String mostrarFormularioCrearReserva(Model model) {
        // Obtener lista de vuelos y pasajeros desde los servicios web
        List<VueloDTO> listaVuelos = vueloWebClient.get()
                .retrieve()
                .bodyToFlux(VueloDTO.class)
                .collectList()
                .block();

        List<PasajeroDTO> listaPasajeros = pasajeroWebClient.get()
                .retrieve()
                .bodyToFlux(PasajeroDTO.class)
                .collectList()
                .block();

        model.addAttribute("listaVuelos", listaVuelos);
        model.addAttribute("listaPasajeros", listaPasajeros);

        return "formularioCrearReserva";
    }

    /**
     * Muestra el formulario de actualización de una reserva.
     *
     * @param idReservaDTO El ID de la reserva a actualizar.
     * @param model        El modelo utilizado para pasar listas de vuelos y pasajeros, y los datos de la reserva a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/formActualizar")
    public String mostrarFormularioActualizarReserva(@RequestParam("idReservaDTO") String idReservaDTO, Model model) {
        // Obtener datos de la reserva desde el servicio web de reservas
        ReservaDTO reserva = reservaWebClient.get()
                .uri("/{id}", idReservaDTO)
                .retrieve()
                .bodyToMono(ReservaDTO.class)
                .block();

        // Obtener listas de vuelos y pasajeros desde los servicios web
        List<VueloDTO> listaVuelos = vueloWebClient.get()
                .retrieve()
                .bodyToFlux(VueloDTO.class)
                .collectList()
                .block();

        List<PasajeroDTO> listaPasajeros = pasajeroWebClient.get()
                .retrieve()
                .bodyToFlux(PasajeroDTO.class)
                .collectList()
                .block();

        // Agregar listas al modelo
        model.addAttribute("listaVuelos", listaVuelos);
        model.addAttribute("listaPasajeros", listaPasajeros);

        // Verificar que los datos de la reserva no sean nulos antes de agregarlos al modelo
        if (reserva.getIdReservaDTO() != null
                && reserva.getAsiento() != null
                && reserva.getVueloDTO().getIdVueloDTO() != null
                && reserva.getPasajeroDTO().getIdPasajeroDTO() != null) {
        	
            model.addAttribute("idReservaDTO", reserva.getIdReservaDTO());
            model.addAttribute("asiento", reserva.getAsiento());
            model.addAttribute("idVueloDTO", reserva.getVueloDTO().getIdVueloDTO());
            model.addAttribute("idPasajeroDTO", reserva.getPasajeroDTO().getIdPasajeroDTO());
        } else {
            model.addAttribute(MENSAJE, FALLO_NULL);
            return VISTA_ERROR;
        }

        return "formularioActualizarReserva";
    }

    /**
     * Muestra la vista que lista todas las reservas obtenidas desde el servicio web.
     *
     * @param model El modelo utilizado para pasar la lista de reservas a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping
    public String mostrarVistaListaReservas(Model model) {
        try {
            // Realiza una solicitud GET al servicio web para obtener una lista de reservas
            ResponseEntity<List<ReservaDTO>> response = reservaWebClient.get()
                    .retrieve()
                    .toEntityList(ReservaDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                // Agrega la lista de reservas al modelo para que esté disponible en la vista
                model.addAttribute("reservas", response.getBody());
                return "vistaMostrarReservas";
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            // En caso de una excepción al comunicarse con el servicio web, agrega un mensaje de error al modelo y retorna una vista de error
            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
            return VISTA_ERROR;
        }
    }


    /**
     * Muestra la vista de una reserva por su ID obtenida desde el servicio web.
     *
     * @param id    El ID de la reserva a mostrar.
     * @param model El modelo utilizado para pasar la reserva a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/id")
    public String mostrarVistaReservaById(@RequestParam(name = "id") String id, Model model) {
        try {
            // Realiza una solicitud GET al servicio web para obtener los detalles de la reserva por su ID
            ResponseEntity<ReservaDTO> response = reservaWebClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .toEntity(ReservaDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                // Agrega el objeto ReservaDTO al modelo para que esté disponible en la vista
                model.addAttribute("reserva", response.getBody());
                return "vistaMostrarReservaById"; // Retorna el nombre de la vista de detalles de la reserva
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL); // Agrega un mensaje de error al modelo
                return VISTA_ERROR; // Retorna la vista de error en caso de una respuesta nula
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, RESERVA_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }


    /**
     * Muestra la vista de la lista de reservas por ID de reserva obtenida desde el servicio web.
     *
     * @param id    El ID de reserva para filtrar las reservas.
     * @param model El modelo utilizado para pasar la lista de reservas a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/mostrar/idreserva/{id}")
    public String mostrarVistaListaReservasPorIdReserva(@PathVariable String id, Model model) {
        try {
            ResponseEntity<List<ReservaDTO>> response = reservaWebClient.get()
                    .uri("/mostrar/idreserva/{id}", id)
                    .retrieve()
                    .toEntityList(ReservaDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("reservas", response.getBody());
                return "vistaMostrarReservas";
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, RESERVA_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }


    /**
     * Muestra la vista de la lista de reservas por ID de pasajero obtenida desde el servicio web.
     *
     * @param id    El ID de pasajero para filtrar las reservas.
     * @param model El modelo utilizado para pasar la lista de reservas a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/mostrar/idpasajero/{id}")
    public String mostrarVistaListaReservasPorIdPasajero(@PathVariable String id, Model model) {
        try {
            ResponseEntity<List<ReservaDTO>> response = reservaWebClient.get()
                    .uri("/mostrar/idpasajero/{id}", id)
                    .retrieve()
                    .toEntityList(ReservaDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
            	if (!response.getBody().isEmpty()) {
                    model.addAttribute("reservas", response.getBody());
                    return "vistaMostrarReservas";
                } else {
                    // Si la respuesta está vacía, puedes manejarlo como una respuesta NOT FOUND
                    model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
                    return VISTA_NOT_FOUND;
                }
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, RESERVA_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }



    /**
     * Muestra la vista de la lista de reservas por ID de vuelo obtenida desde el servicio web.
     *
     * @param id    El ID de vuelo para filtrar las reservas.
     * @param model El modelo utilizado para pasar la lista de reservas a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/mostrar/idvuelo/{id}")
    public String mostrarVistaListaReservasPorIdVuelo(@PathVariable String id, Model model) {
        try {
            ResponseEntity<List<ReservaDTO>> response = reservaWebClient.get()
                    .uri("/mostrar/idvuelo/{id}", id)
                    .retrieve()
                    .toEntityList(ReservaDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
            	if (!response.getBody().isEmpty()) {
                    model.addAttribute("reservas", response.getBody());
                    return "vistaMostrarReservas";
                } else {
                    // Si la respuesta está vacía, puedes manejarlo como una respuesta NOT FOUND
                    model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
                    return VISTA_NOT_FOUND;
                }
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, RESERVA_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }



    /**
     * Crea una reserva utilizando el servicio web y muestra la vista correspondiente.
     *
     * @param idVueloDTO    El ID del vuelo asociado a la reserva.
     * @param idPasajeroDTO El ID del pasajero asociado a la reserva.
     * @param asiento       El asiento reservado.
     * @param model         El modelo utilizado para mostrar mensajes y datos en la vista.
     * @return El nombre de la vista a mostrar.
     */
    @PostMapping("/crear/")
    public String createReserva(@RequestParam("idVueloDTO") String idVueloDTO,
                                @RequestParam("idPasajeroDTO") String idPasajeroDTO,
                                @RequestParam("asiento") String asiento, Model model) {
        try {
            ReservaSoloIdDTO reservaSoloIdDTO = new ReservaSoloIdDTO(idVueloDTO, idPasajeroDTO, asiento);

            ResponseEntity<ReservaDTO> response = reservaWebClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(reservaSoloIdDTO)
                    .retrieve()
                    .toEntity(ReservaDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, RESERVA_CREATED);
                model.addAttribute("reservaDTO", response.getBody());
                return "VistaCrearReserva";
            } else {
            	 model.addAttribute(MENSAJE, FALLO_NULL);
                 return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, RESERVA_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }

    /**
     * Borra una reserva utilizando el servicio web y muestra la vista correspondiente.
     *
     * @param idReservaDTO El ID de la reserva que se va a borrar.
     * @param model        El modelo utilizado para mostrar mensajes en la vista.
     * @return El nombre de la vista a mostrar.
     */
	@PostMapping("/borrar/")
	public String borrarReserva(@RequestParam("idReservaDTO") String idReservaDTO, Model model) {
		try {

			ResponseEntity<Void> response = reservaWebClient.delete()
					.uri("/{id}", idReservaDTO)
					.retrieve()
					.toBodilessEntity()
					.block();

			if (response != null && response.getStatusCode().is2xxSuccessful()) {
				model.addAttribute(MENSAJE, RESERVA_DELETED);

				return "VistaBorrar";
			} else {
				model.addAttribute(MENSAJE, FALLO_NULL);
				return VISTA_ERROR; // Maneja otros errores posibles
			}
	
		 } catch (WebClientResponseException e) {
	            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	                model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
	                return VISTA_NOT_FOUND;
	            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
	                model.addAttribute(MENSAJE, RESERVA_BAD_REQUEST);
	                return VISTA_BAD_REQUEST;
	            } else {
	                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
	                return VISTA_ERROR;
	            }
	        }
	}

	/**
	 * Actualiza una reserva utilizando el servicio web y muestra la vista correspondiente.
	 *
	 * @param idReservaDTO  El ID de la reserva que se va a actualizar.
	 * @param idVueloDTO    El nuevo ID del vuelo para la reserva.
	 * @param idPasajeroDTO El nuevo ID del pasajero para la reserva.
	 * @param asiento       El nuevo asiento para la reserva.
	 * @param model         El modelo utilizado para mostrar mensajes en la vista.
	 * @return El nombre de la vista a mostrar.
	 */
	@PostMapping("/actualizar/")
	public String updateReserva(@RequestParam("idReservaDTO") String idReservaDTO,
	                            @RequestParam("idVueloDTO") String idVueloDTO,
	                            @RequestParam("idPasajeroDTO") String idPasajeroDTO,
	                            @RequestParam("asiento") String asiento, Model model) {
	    try {
	        // Crear un objeto ReservaSoloIdDTO con los nuevos datos
	        ReservaSoloIdDTO reservaSoloIdDTO = new ReservaSoloIdDTO(idVueloDTO, idPasajeroDTO, asiento);

	        // Realizar una solicitud PUT al servicio web para actualizar la reserva
	        ResponseEntity<ReservaDTO> response = reservaWebClient.put()
	                .uri("/{idReservaDTO}", idReservaDTO)
	                .contentType(MediaType.APPLICATION_JSON)
	                .bodyValue(reservaSoloIdDTO)
	                .retrieve()
	                .toEntity(ReservaDTO.class)
	                .block();
	        
	        if (response != null && response.getStatusCode().is2xxSuccessful()) {
	            model.addAttribute(MENSAJE, RESERVA_UPDATED);
	            model.addAttribute("reservaDTO", response.getBody());
	            
	            return "VistaActualizarReserva";
	        } else {
	        	model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
	        }
	    } catch (WebClientResponseException e) {
	        // Manejar diferentes tipos de errores de respuesta del servicio web
	        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	            model.addAttribute(MENSAJE, RESERVA_NOT_FOUND);
	            return VISTA_NOT_FOUND;
	        } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
	            model.addAttribute(MENSAJE, RESERVA_BAD_REQUEST);
	            return VISTA_BAD_REQUEST;
	        } else {
	            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
	            return VISTA_ERROR;
	        }
	    }
	}


}
