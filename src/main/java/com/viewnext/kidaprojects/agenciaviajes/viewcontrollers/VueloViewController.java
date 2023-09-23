package com.viewnext.kidaprojects.agenciaviajes.viewcontrollers;



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

@Controller
@RequestMapping("/vuelo/")
public class VueloViewController {
    private static final String MENSAJE = "mensaje";
    private static final String VISTA_ERROR = "vistaError";
    private static final String ID_ERRONEO = "ID de vuelo no válido";
    private static final String FALLO_CONEXION_WEBCLIENT = "Error al comunicarse con el servicio";

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
            List<VueloDTO> listaVuelos = vueloWebClient.get()
                    .retrieve()
                    .bodyToFlux(VueloDTO.class)
                    .collectList()
                    .block();

            model.addAttribute("vuelos", listaVuelos);

            return "vistaMostrarVuelos";
        } catch (WebClientResponseException e) {
            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
            return VISTA_ERROR;
        }
    }

    /**
     * Obtiene un vuelo por su ID desde el servicio web y muestra la vista de un
     * vuelo por su ID.
     *
     * @param id    El ID del vuelo a mostrar.
     * @param model El modelo utilizado para pasar el vuelo a la vista.
     * @return El nombre de la vista a mostrar.
     */
    @GetMapping("/id")
    public String mostrarVistaVueloById(@RequestParam(name = "id") String id, Model model) {
        try {
            Integer idNumerico = Integer.parseInt(id);

            VueloDTO vuelo = vueloWebClient.get()
                    .uri("/{id}", idNumerico)
                    .retrieve()
                    .bodyToMono(VueloDTO.class)
                    .block();

            model.addAttribute("vuelo", vuelo);

            return "vistaMostrarVueloById";
        } catch (NumberFormatException e) {
            model.addAttribute(MENSAJE, ID_ERRONEO);
            return VISTA_ERROR;
        } catch (WebClientResponseException e) {
            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
            return VISTA_ERROR;
        }
    }
    
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

	         if (response.getStatusCode().is2xxSuccessful()) {
	             // Obtener la lista de VueloDTO de la respuesta
	             List<VueloDTO> vuelos = response.getBody();
	             model.addAttribute("vuelos", vuelos);

	             return "vistaMostrarVuelos"; // Redireccionar a la vista adecuada
	         } else {
	        	 model.addAttribute(MENSAJE, "estoy aqui");
	             
	             return VISTA_ERROR; // Manejar otros errores posibles
	         }
	    } catch (WebClientResponseException e) {
            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
            return VISTA_ERROR;
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
            ResponseEntity<Void> response = vueloWebClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(vueloDTOSinId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (response != null && response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute(MENSAJE, "El Vuelo fue creado exitosamente");
                model.addAttribute("nuevoVuelo", vueloDTOSinId);
                return "VistaCrearVuelo"; // Redireccionar a la lista de vuelos
            } else {
                model.addAttribute(MENSAJE, "Error al crear el Vuelo");
                return VISTA_ERROR;
            }
        } catch (WebClientResponseException e) {
            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
            return VISTA_ERROR;
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
                model.addAttribute(MENSAJE, "Vuelo eliminado con éxito");
                return "VistaBorrar";
            } else {
                model.addAttribute(MENSAJE, "Error al borrar el Vuelo");
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
                model.addAttribute(MENSAJE, "Datos del vuelo actualizados con éxito");
                // Asigna el objeto VueloDTO actualizado al modelo
                model.addAttribute("vuelo", response);
                
                
                return "VistaActualizarVuelo";
            } else {
                model.addAttribute(MENSAJE, "Error al actualizar el Vuelo");
                return VISTA_ERROR;
            }
        } catch (NumberFormatException e) {
            model.addAttribute(MENSAJE, "Error al actualizar el Vuelo");
            return VISTA_ERROR;
        } catch (WebClientResponseException e) {
            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
            return VISTA_ERROR;
        }
    }

}

