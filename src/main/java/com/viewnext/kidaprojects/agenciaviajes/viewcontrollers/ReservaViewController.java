package com.viewnext.kidaprojects.agenciaviajes.viewcontrollers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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



@Controller
@RequestMapping("/reserva/")
public class ReservaViewController {

	private static final String MENSAJE = "mensaje";
	private static final String VISTA_ERROR = "vistaError"; 
	private static final String ID_ERRONEO = "ID de Reserva no válido";
	private static final String FALLO_CONEXION_WEBCLIENT = "Error al comunicarse con el servicio";
	private final WebClient reservaWebClient;
	private final WebClient vueloWebClient;
	private final WebClient pasajeroWebClient;
	
	
	public ReservaViewController(WebClient reservaWebClient, WebClient vueloWebClient, WebClient pasajeroWebClient) {
		this.reservaWebClient = reservaWebClient;
		this.vueloWebClient = vueloWebClient;
		this.pasajeroWebClient = pasajeroWebClient;
		
	}

	

	@GetMapping("/formbuscar")
	public String mostrarFormularioBusquedaReserva(Model model) {
		// Aquí puedes realizar cualquier lógica adicional necesaria antes de mostrar el
		// formulario
		return "formularioBuscarReserva";
	}
	
	//MOSTRARTODOMETODO
	@GetMapping("/formcrear")
	public String mostrarFormularioCrearReserva(Model model) {
		// Realiza una solicitud GET al servicio web para obtener una lista de vuelos
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
	
	@PostMapping("/crear/")
	public String createReserva( @RequestParam("idVueloDTO") String idVueloDTO,
		    @RequestParam("idPasajeroDTO") String idPasajeroDTO,
		    @RequestParam("asiento") String asiento,
		    Model model) {
		try {
			
			ReservaSoloIdDTO reservaSoloIdDTO = new ReservaSoloIdDTO(idVueloDTO, idPasajeroDTO, asiento);
			
			System.out.println("psajero: " + reservaSoloIdDTO.getIdPasajeroDTO());
			System.out.println("vuelo: " + reservaSoloIdDTO.getIdVueloDTO());
			System.out.println(reservaSoloIdDTO.getAsiento());

			 ReservaDTO response = reservaWebClient.post()
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .bodyValue(reservaSoloIdDTO)
	                    .retrieve()
	                    .bodyToMono(ReservaDTO.class)
	                    .block();

			 if (response != null) {
	                model.addAttribute(MENSAJE, "La Reserva fue creado exitosamente");
	                model.addAttribute("reservaDTO", response);
	                return "VistaCrearReserva"; 
			} else {
				return "error";
			}
		} catch (Exception ex) {
			return "error";
		}
	}
	
	@GetMapping
	public String mostrarVistaListaReservas(Model model) {
		 try {
	            // Realiza una solicitud GET al servicio web para obtener una lista de pasajeros
	            List<ReservaDTO> listaReservas = reservaWebClient.get()
	                    .retrieve()
	                    .bodyToFlux(ReservaDTO.class) // Convierte la respuesta en un flux de PasajeroDTO
	                    .collectList() // Recolecta los elementos del flux en una lista
	                    .block(); // Bloquea hasta que la operación esté completa y devuelve la lista

	            // Agrega la lista de pasajeros al modelo para que esté disponible en la vista
	            model.addAttribute("reservas", listaReservas);

	            // Retorna el nombre de la vista que mostrará la lista de pasajeros
	            return "vistaMostrarReservas";
	        } catch (WebClientResponseException e) {
	            // En caso de un error al comunicarse con el servicio web, agrega un mensaje de error al modelo y retorna una vista de error
	            model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
	            return VISTA_ERROR;
	        }
	}
	
	
	

	@GetMapping("/id")
	public String mostrarVistaReservaById(@RequestParam(name = "id") String id, Model model) {
	    try {
	        // Obtiene el ID del pasajero del objeto PasajeroDTO recibido como parámetro
	        Integer idNumerico = Integer.parseInt(id);
	        
	        // Realiza una solicitud GET al servicio web para obtener los detalles del pasajero por su ID
	        ReservaDTO reserva = reservaWebClient.get()
	                .uri("/{id}", idNumerico)
	                .retrieve()
	                .bodyToMono(ReservaDTO.class)
	                .block();
	        
	        // Agrega el objeto PasajeroDTO al modelo para que esté disponible en la vista
	        model.addAttribute("reserva", reserva);
	        
	        // Retorna el nombre de la vista que mostrará los detalles del pasajero
	        return "vistaMostrarReservaById";
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

	@GetMapping("/mostrar/idreserva/{id}")
	public String mostrarVistaListaReservasPorIdReserva(@PathVariable Integer id, Model model) {
		List<ReservaDTO> listaReservas = obtenerListaReservasPorIdReserva(id);
		model.addAttribute("reservas", listaReservas);

		return "vistaMostrarReservas";
	}

	@GetMapping("/mostrar/idpasajero/{id}")
	public String mostrarVistaListaReservasPorIdPasajero(@PathVariable Integer id, Model model) {
		List<ReservaDTO> listaReservas = obtenerListaReservasPorIdPasajero(id);
		model.addAttribute("reservas", listaReservas);

		return "vistaMostrarReservas";
	}
	@GetMapping("/mostrar/idvuelo/{id}")
	public String mostrarVistaListaReservasPorIdVuelo(@PathVariable Integer id, Model model) {
		List<ReservaDTO> listaReservas = obtenerListaReservasPorIdVuelo(id);
		model.addAttribute("reservas", listaReservas);

		return "vistaMostrarReservas";
	}

	public List<ReservaDTO> obtenerListaReservasPorIdReserva(Integer id) {
		return reservaWebClient.get().uri("/mostrar/idreserva/{id}", id).retrieve().bodyToFlux(ReservaDTO.class)
				.collectList().block();
	}

	public List<ReservaDTO> obtenerListaReservasPorIdVuelo(Integer id) {
		return reservaWebClient.get().uri("/mostrar/idvuelo/{id}", id).retrieve().bodyToFlux(ReservaDTO.class)
				.collectList().block();
	}

	public List<ReservaDTO> obtenerListaReservasPorIdPasajero(Integer id) {
		return reservaWebClient.get().uri("/mostrar/idpasajero/{id}", id).retrieve().bodyToFlux(ReservaDTO.class)
				.collectList().block();
	}
	
	

	

	

	@PostMapping("/actualizar/")
	public String actualizarReserva(@ModelAttribute("reservaSoloIdDTO") ReservaSoloIdDTO reservaSoloIdDTO, Model model) {
		try {
			// Obtener los valores del objeto reservaSoloIdDTO. Además parseamos a integer
			// los String de id
			
			Integer idVuelo = Integer.parseInt(reservaSoloIdDTO.getIdVueloDTO());
			Integer idPasajero = Integer.parseInt(reservaSoloIdDTO.getIdPasajeroDTO());
			String asiento = reservaSoloIdDTO.getAsiento();

			// Construir la URL con los parámetros
			String url = "/actualizar/params?idReserva={idReserva}&idVuelo={idVuelo}&idPasajero={idPasajero}&asiento={asiento}";

			// Realizar la actualización utilizando WebClient y el ID de la reserva
			ResponseEntity<ReservaDTO> response = reservaWebClient.put()
					.uri(url, idVuelo, idPasajero, asiento)
					.retrieve().
					toEntity(ReservaDTO.class)
					.block();

			if (response.getStatusCode().is2xxSuccessful()) {
				ReservaDTO reservaDTO = response.getBody();

				model.addAttribute("reservaDTO", reservaDTO);
				model.addAttribute("mensaje",
						"La Reserva, con los datos que se muestran a continuación, fue actualizada exitosamente");

				return "reservaActualizar";
			} else {
				return "error";
			}
		} catch (Exception ex) {
			return "error";
		}
	}
	
	
	@PostMapping("/borrar/")
	public String borrarReserva(@RequestParam("idReservaDTO") String idReservaDTO, Model model) {
	    try {
	    	
	    	
	    	ResponseEntity<Void> response = reservaWebClient.delete()
	    			.uri("/{id}", idReservaDTO)
					.retrieve()
					.toBodilessEntity()
					.block();

	        if (response != null && response.getStatusCode().is2xxSuccessful()) {
	            model.addAttribute(MENSAJE, "Reserva eliminada con éxito");
	           
	            return "VistaBorrar"; 
	        } else {
	            model.addAttribute(MENSAJE, "Error al borrar la reserva");
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
	
	

}
