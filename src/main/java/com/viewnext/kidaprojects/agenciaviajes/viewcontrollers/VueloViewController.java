package com.viewnext.kidaprojects.agenciaviajes.viewcontrollers;



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
import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTOSinId;

import java.util.List;

/**
 * Clase que representa un controlador para las vistas relacionadas con las operaciones de vuelo.
 * 
 * <p>
 * Este controlador se encarga de gestionar las solicitudes relacionadas con las operaciones de vuelo
 * en el sistema. Proporciona métodos para mostrar formularios, obtener y mostrar información
 * sobre vuelos, crear nuevos vuelos, actualizar información de vuelos y eliminar vuelos
 * utilizando el servicio web de vuelos.
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
@RequestMapping("/vuelo/")
public class VueloViewController {
	// Constantes para los mensajes y nombres de las vistas
    private static final String MENSAJE = "mensaje";
    private static final String VISTA_ERROR = "vistaError"; 
    private static final String VISTA_NOT_FOUND = "vistaNotFound";
    private static final String VISTA_BAD_REQUEST = "vistaBadRequest";
    
    private static final String VUELO_NOT_FOUND = "Vuelo no encontrado";
    private static final String VUELO_BAD_REQUEST = "Se esperaban unos argumentos distintos en la solictud";
    private static final String VUELO_CREATED = "El Vuelo, con los datos que se muestran a continuación, fue creado exitosamente";
    private static final String VUELO_UPDATED = "Datos del Vuelo actualizados con éxito";
    private static final String VUELO_DELETED = "Vuelo eliminado con éxito";
    private static final String FALLO_CONEXION_WEBCLIENT = "Error al comunicarse con el servicio";
    private static final String FALLO_NULL = "La respuesta del servidor es nula o no se pudo mapear al tipo esperado.";

    private final WebClient vueloWebClient;

    /**
     * Constructor que recibe un WebClient para realizar peticiones al servicio web
     * de vuelos.
     *
     * @param vueloWebClient El WebClient configurado para comunicarse con el
     *                       servicio web de vuelos.
     */
    public VueloViewController(WebClient vueloWebClient) {
        this.vueloWebClient = vueloWebClient;
    }
    
   
    

    /**
     * Muestra la vista del formulario para buscar vuelos.
     *
     * @param model El modelo utilizado para realizar cualquier lógica adicional
     *              antes de mostrar el formulario.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/formbuscar")
    public String mostrarFormularioBusquedaVuelo(Model model) {
        return "formularioBuscarVuelo";
    }
    
    /**
     * Muestra la vista del formulario para crear un nuevo vuelo.
     *
     * @param model El modelo utilizado para pasar un objeto VueloDTO vacío a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/formcrear")
    public String mostrarFormularioCrearVuelo(Model model) {
        model.addAttribute("vueloDTOSinId", new VueloDTOSinId());
        return "formularioCrearVuelo";
    }

    /**
     * Obtiene una lista de vuelos desde el servicio web y muestra la vista que
     * lista todos los vuelos.
     *
     * @param model El modelo utilizado para pasar la lista de vuelos a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping
    public String mostrarVistaListaVuelos(Model model) {
        try {
            // Realiza una solicitud GET al servicio web para obtener una lista de vuelos
           ResponseEntity<List<VueloDTO>>  response = vueloWebClient.get()
                    .retrieve()
                    .toEntityList(VueloDTO.class)
                    .block();

           if (response != null && response.getStatusCode().is2xxSuccessful()) {
               
               model.addAttribute("vuelos", response.getBody());
               return "vistaMostrarVuelos";
           } else {
               model.addAttribute(MENSAJE, FALLO_NULL);
               return VISTA_ERROR;
           }
        } catch (WebClientResponseException e) {
            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
            return VISTA_ERROR;
        }
    }

    /**
     * Obtiene un vuelo por su ID desde el servicio web y muestra la vista de un vuelo por su ID.
     *
     * @param id    El ID del vuelo a mostrar.
     * @param model El modelo utilizado para pasar el vuelo a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/id")
    public String mostrarVistaVueloById(@RequestParam(name = "id") String id, Model model) {
        try {
            // Realiza una solicitud GET al servicio web para obtener los detalles del vuelo por su ID
            ResponseEntity<VueloDTO> response = vueloWebClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .toEntity(VueloDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                // Agrega el objeto VueloDTO al modelo para que esté disponible en la vista
                model.addAttribute("vuelo", response.getBody());
                return "vistaMostrarVueloById"; // Retorna el nombre de la vista de detalles del vuelo
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL); // Agrega un mensaje de error al modelo
                return VISTA_ERROR; // Retorna la vista de error en caso de una respuesta nula
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, VUELO_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute(MENSAJE, VUELO_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }



    /**
     * Obtiene y muestra la vista de vuelos filtrados por origen, destino y fecha.
     *
     * @param origen El origen de los vuelos a buscar.
     * @param destino El destino de los vuelos a buscar.
     * @param fecha La fecha de los vuelos a buscar.
     * @param model El modelo utilizado para pasar la lista de vuelos filtrados a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/params")
	public String mostrarVistaVueloByOrigenDestinoFecha(@RequestParam String origen, @RequestParam String destino, @RequestParam String fecha, Model model) {
	    try {
	        // Realizar la solicitud utilizando WebClient y los parámetros de origen, destino y fecha
	    	String url = "/buscar?origen={origen}&destino={destino}&fecha={fecha}";
	    	 ResponseEntity<List<VueloDTO>> response = vueloWebClient.get()
	                 .uri(url, origen, destino, fecha)
	                 .retrieve()
	                 .toEntityList(VueloDTO.class)
	                 .block();

	         if (response != null && response.getStatusCode() .is2xxSuccessful()){
	             // Obtener la lista de VueloDTO de la respuesta
	             model.addAttribute("vuelos", response.getBody());

	             return "vistaMostrarVuelos"; // Redireccionar a la vista adecuada
	         } else {
	        	 model.addAttribute(MENSAJE, FALLO_NULL);
	             
	             return VISTA_ERROR; // Manejar otros errores posibles
	         }
	    } catch (WebClientResponseException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, VUELO_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                model.addAttribute(MENSAJE, VUELO_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
	 }

   

    /**
     * Crea un nuevo vuelo utilizando el servicio web de vuelos.
     *
     * @param vueloDTO El objeto VueloDTO con los datos del nuevo vuelo a crear.
     * @param model    El modelo utilizado para pasar mensajes y objetos a la vista.
     * @return El nombre de la vista a mostrar después de la creación.
     */
    @PostMapping("/crear/")
    public String createVuelo(@ModelAttribute("vueloDTOSinId") VueloDTOSinId vueloDTOSinId, Model model) {
        try {
            ResponseEntity<VueloDTO> response = vueloWebClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(vueloDTOSinId)
                    .retrieve()
                    .toEntity(VueloDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, VUELO_CREATED);
                model.addAttribute("nuevoVuelo", response.getBody());
                return "VistaCrearVuelo"; // Redireccionar a la lista de vuelos
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute(MENSAJE, VUELO_NOT_FOUND);
                return VISTA_NOT_FOUND;
            } else if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                model.addAttribute(MENSAJE, VUELO_BAD_REQUEST);
                return VISTA_BAD_REQUEST;
            } else {
                model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
                return VISTA_ERROR;
            }
        }
    }

    /**
     * Elimina un vuelo por su ID.
     *
     * @param vueloDTO El objeto VueloDTO con el ID del vuelo a eliminar.
     * @param model    El modelo utilizado para pasar mensajes de éxito o error a la vista.
     * @return El nombre de la vista a mostrar después de la eliminación.
     */
    @PostMapping("/borrar/")
    public String borrarVuelo(@RequestParam("idVueloDTO") String idVueloDTO, Model model) {
        try {
            
        	ResponseEntity<Void> response = vueloWebClient.delete()
                    .uri("/{id}", idVueloDTO)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, VUELO_DELETED);
                return "VistaBorrar";
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR; // Maneja otros errores posibles
            }
        } catch (WebClientResponseException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				model.addAttribute(MENSAJE, VUELO_NOT_FOUND);
				return VISTA_NOT_FOUND;
			} else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				model.addAttribute(MENSAJE, VUELO_BAD_REQUEST);
				return VISTA_BAD_REQUEST;
			} else {
				model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
				return VISTA_ERROR;
			}
		}
    }

    /**
     * Actualiza la información de un vuelo.
     *
     * @param vueloDTO El objeto VueloDTO con los datos actualizados del vuelo.
     * @param model    El modelo utilizado para pasar mensajes de éxito o error a la vista.
     * @return El nombre de la vista a mostrar después de la actualización.
     */
    @PostMapping("/actualizar/")
    public String actualizarVuelo(@ModelAttribute VueloDTO vueloDTO, Model model) {
        try {
            

            ResponseEntity<VueloDTO> response = vueloWebClient.put()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(vueloDTO)
                    .retrieve()
                    .toEntity(VueloDTO.class)
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, VUELO_UPDATED);
                // Asigna el objeto VueloDTO actualizado al modelo
                model.addAttribute("vuelo", response.getBody());
                
                
                return "VistaActualizarVuelo";
            } else {
                model.addAttribute(MENSAJE, FALLO_NULL);
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				model.addAttribute(MENSAJE, VUELO_NOT_FOUND);
				return VISTA_NOT_FOUND;
			} else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				model.addAttribute(MENSAJE, VUELO_BAD_REQUEST);
				return VISTA_BAD_REQUEST;
			} else {
				model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
				return VISTA_ERROR;
			}
		}
    }
}

