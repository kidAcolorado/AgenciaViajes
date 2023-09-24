package com.viewnext.kidaprojects.agenciaviajes.viewcontrollers;

import java.util.List;
import org.springframework.http.HttpStatus;
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
import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTOSinId;


/**
 * Clase que representa un controlador para las vistas relacionadas con los pasajeros.
 * 
 * <p>
 * Este controlador se encarga de gestionar las solicitudes relacionadas con los pasajeros
 * en el sistema. Proporciona métodos para mostrar formularios, obtener y mostrar información
 * sobre pasajeros, crear nuevos pasajeros, actualizar información de pasajeros y eliminar pasajeros
 * utilizando el servicio web de pasajeros.
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
@RequestMapping("/pasajero/")
public class PasajeroViewController {

	// Constantes para los mensajes y nombres de las vistas
	private static final String MENSAJE = "mensaje";
	private static final String VISTA_ERROR = "vistaError";
	private static final String VISTA_NOT_FOUND = "vistaNotFound";
	private static final String VISTA_BAD_REQUEST = "vistaBadRequest";

	private static final String PASAJERO_NOT_FOUND = "Pasajero no encontrado";
	private static final String PASAJERO_BAD_REQUEST = "Se esperaban unos argumentos distintos en la solictud";
	private static final String PASAJERO_CREATED = "El Pasajero, con los datos que se muestran a continuación, fue creado exitosamente";
	private static final String PASAJERO_UPDATED = "Datos del pasajero actualizados con éxito";
	private static final String PASAJERO_DELETED = "Pasajero eliminado con éxito";
	private static final String FALLO_CONEXION_WEBCLIENT = "Error al comunicarse con el servicio";
	private static final String FALLO_NULL = "La respuesta del servidor es nula o no se pudo mapear al tipo esperado.";

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
     * @param model El modelo utilizado para realizar cualquier lógica adicional
     *              antes de mostrar el formulario.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/formbuscar")
    public String mostrarFormularioBusquedaPasajero(Model model) {
        return "formularioBuscarPasajero";
    }

    /**
     * Muestra la vista del formulario para crear un nuevo pasajero.
     *
     * @param model El modelo utilizado para pasar un objeto PasajeroDTOSinId vacío
     *              a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/formcrear")
    public String mostrarFormularioCrearPasajero(Model model) {
        model.addAttribute("pasajeroDTOSinId", new PasajeroDTOSinId());
        return "formularioCrearPasajero";
    }

    /**
     * Obtiene una lista de pasajeros desde el servicio web y muestra la vista que
     * lista todos los pasajeros.
     *
     * @param model El modelo utilizado para pasar la lista de pasajeros a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping
    public String mostrarVistaListaPasajeros(Model model) {
        try {
            // Realiza una solicitud GET al servicio web para obtener una lista de pasajeros
            ResponseEntity<List<PasajeroDTO>> response = pasajeroWebClient.get()
            		.retrieve()
                    .toEntityList(PasajeroDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("pasajeros", response.getBody());
                return "vistaMostrarPasajeros";
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            // En caso de un error al comunicarse con el servicio web, agrega un mensaje de
            // error al modelo y retorna una vista de error
            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
            return VISTA_ERROR;
        }
    }

    /**
     * Obtiene un pasajero por su ID desde el servicio web y muestra la vista de un
     * pasajero por su ID.
     *
     * @param id    El ID del pasajero a mostrar.
     * @param model El modelo utilizado para pasar el pasajero a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/id")
    public String mostrarVistaPasajeroById(@RequestParam(name = "id") String id, Model model) {
        try {
            // Realiza una solicitud GET al servicio web para obtener los detalles del
            // pasajero por su ID
            ResponseEntity<PasajeroDTO> response = pasajeroWebClient.get()
            		.uri("/{id}", id)
            		.retrieve()
                    .toEntity(PasajeroDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                // Agrega el objeto PasajeroDTO al modelo para que esté disponible en la vista
                model.addAttribute("pasajero", response.getBody());
                return "vistaMostrarPasajeroById"; // Retorna el nombre de la vista de detalles del pasajero
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL); // Agrega un mensaje de error al modelo
                return VISTA_ERROR; // Retorna la vista de error en caso de una respuesta nula
            }

        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, PASAJERO_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, PASAJERO_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }

    /**
     * Crea un nuevo pasajero utilizando el servicio web de pasajeros.
     *
     * @param pasajeroDTOSinId El objeto PasajeroDTO con los datos del nuevo
     *                         pasajero a crear.
     * @param model            El modelo utilizado para pasar mensajes y objetos a
     *                         la vista.
     * @return El nombre de la vista a mostrar después de la creación.
     */
    @PostMapping("/crear/")
    public String createPasajero(@ModelAttribute("pasajeroDTOSinId") PasajeroDTOSinId pasajeroDTOSinId, Model model) {
        try {
            // Realiza una solicitud POST al servicio web para crear un nuevo pasajero
            ResponseEntity<PasajeroDTO> response = pasajeroWebClient.post()
            		.contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(pasajeroDTOSinId)
                    .retrieve()
                    .toEntity(PasajeroDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, PASAJERO_CREATED);
                model.addAttribute("nuevoPasajero", response.getBody());
                return "vistaCrearPasajero";
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, PASAJERO_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, PASAJERO_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }

    /**
     * Elimina un pasajero por su ID.
     *
     * @param id    El ID del pasajero a eliminar.
     * @param model El modelo utilizado para pasar mensajes de éxito o error a la
     *              vista.
     * @return El nombre de la vista a mostrar después de la eliminación.
     */
    @PostMapping("/borrar/")
    public String borrarPasajero(@RequestParam("idPasajeroDTO") String idPasajeroDTO, Model model) {
        try {
            ResponseEntity<Void> response = pasajeroWebClient.delete()
            		.uri("/{id}", idPasajeroDTO)
            		.retrieve()
                    .toBodilessEntity()
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, PASAJERO_DELETED);
                return "VistaBorrar";
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR; // Maneja otros errores posibles
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, PASAJERO_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, PASAJERO_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }

    /**
     * Actualiza la información de un pasajero.
     *
     * @param pasajeroDTO El objeto PasajeroDTO con los datos actualizados del
     *                    pasajero.
     * @param model       El modelo utilizado para pasar mensajes de éxito o error a
     *                    la vista.
     * @return El nombre de la vista a mostrar después de la actualización.
     */
    @PostMapping("/actualizar/")
    public String actualizarPasajero(@ModelAttribute("pasajeroDTO") PasajeroDTO pasajeroDTO, Model model) {
        try {
            // Realiza una solicitud PUT al servicio web para actualizar los datos de un
            // pasajero
            ResponseEntity<PasajeroDTO> response = pasajeroWebClient.put()
            		.contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(pasajeroDTO)
                    .retrieve()
                    .toEntity(PasajeroDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, PASAJERO_UPDATED);
                model.addAttribute("pasajero", response.getBody());
                return "vistaActualizarPasajero";
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, PASAJERO_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, PASAJERO_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }
}