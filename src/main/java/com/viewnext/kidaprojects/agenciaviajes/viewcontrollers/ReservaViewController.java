package com.viewnext.kidaprojects.agenciaviajes.viewcontrollers;

import java.util.List;

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

@Controller
@RequestMapping("/reserva/")
public class ReservaViewController {

	private static final String MENSAJE = "mensaje";
	private static final String VISTA_ERROR = "vistaError";
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

		return "formularioBuscarReserva";
	}

	@GetMapping("/formcrear")
	public String mostrarFormularioCrearReserva(Model model) {

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
	
	@GetMapping("/formActualizar")
	public String mostrarFormularioActualizarReserva(@RequestParam("idReservaDTO") String idReservaDTO, Model model) {
		
		
		//Buscamos los datos de la reserva
		ReservaDTO reserva = reservaWebClient.get()
				.uri("/{id}", idReservaDTO)
				.retrieve()
				.bodyToMono(ReservaDTO.class)
				.block();
		
		//Pedimos la lista vuelos
		List<VueloDTO> listaVuelos = vueloWebClient.get()
				.retrieve()
				.bodyToFlux(VueloDTO.class)
				.collectList()
				.block();
		
        //Pedimos la lista de pasajeros
		List<PasajeroDTO> listaPasajeros = pasajeroWebClient.get()
				.retrieve()
				.bodyToFlux(PasajeroDTO.class)
				.collectList()
				.block();
		
		
		
		
		model.addAttribute("listaVuelos", listaVuelos);
		model.addAttribute("listaPasajeros", listaPasajeros);
		
		model.addAttribute("idReservaDTO", reserva.getIdReservaDTO());
		model.addAttribute("asiento", reserva.getAsiento());
		model.addAttribute("idVueloDTO",reserva.getVueloDTO().getIdVueloDTO());
		model.addAttribute("idPasajeroDTO", reserva.getPasajeroDTO().getIdPasajeroDTO());
		
		
		return "formularioActualizarReserva";
	}

	@GetMapping
	public String mostrarVistaListaReservas(Model model) {
		try {
			// Realiza una solicitud GET al servicio web para obtener una lista de pasajeros
			List<ReservaDTO> listaReservas = reservaWebClient.get()
					.retrieve()
					.bodyToFlux(ReservaDTO.class) // Convierte la respuesta en un flux 
					.collectList() // Recolecta los elementos del flux en una lista
					.block(); // Bloquea hasta que la operación esté completa y devuelve la lista

			// Agrega la lista de pasajeros al modelo para que esté disponible en la vista
			model.addAttribute("reservas", listaReservas);

			// Retorna el nombre de la vista que mostrará la lista 
			return "vistaMostrarReservas";
			
		} catch (WebClientResponseException e) {
			
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
	}

	@GetMapping("/id")
	public String mostrarVistaReservaById(@RequestParam(name = "id") String id, Model model) {
		try {
						
			ReservaDTO reserva = reservaWebClient.get()
					.uri("/{id}", id)
					.retrieve()
					.bodyToMono(ReservaDTO.class)
					.block();

			
			model.addAttribute("reserva", reserva);

			
			return "vistaMostrarReservaById";
		
		} catch (WebClientResponseException e) {
			// En caso de un error al comunicarse con el servicio web, agrega un mensaje de
			// error al modelo y retorna una vista de error
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
	}

	@GetMapping("/mostrar/idreserva/{id}")
	public String mostrarVistaListaReservasPorIdReserva(@PathVariable String id, Model model) {

		try {
			List<ReservaDTO> listaReservas = reservaWebClient.get()
					.uri("/mostrar/idreserva/{id}", id)
					.retrieve()
					.bodyToFlux(ReservaDTO.class)
					.collectList()
					.block();

			model.addAttribute("reservas", listaReservas);

			return "vistaMostrarReservas";
		} catch (WebClientResponseException e) {
			
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
	}

	@GetMapping("/mostrar/idpasajero/{id}")
	public String mostrarVistaListaReservasPorIdPasajero(@PathVariable String id, Model model) {
	    try {
	        List<ReservaDTO> listaReservas = reservaWebClient.get()
	                .uri("/mostrar/idpasajero/{id}", id)
	                .retrieve()
	                .bodyToFlux(ReservaDTO.class)
	                .collectList()
	                .block();

	        model.addAttribute("reservas", listaReservas);

	        return "vistaMostrarReservas";
	    } catch (WebClientResponseException e) {
	        model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
	        return VISTA_ERROR;
	    }
	}


	@GetMapping("/mostrar/idvuelo/{id}")
	public String mostrarVistaListaReservasPorIdVuelo(@PathVariable String id, Model model) {
	    try {
	        List<ReservaDTO> listaReservas = reservaWebClient.get()
	                .uri("/mostrar/idvuelo/{id}", id)
	                .retrieve()
	                .bodyToFlux(ReservaDTO.class)
	                .collectList()
	                .block();

	        model.addAttribute("reservas", listaReservas);

	        return "vistaMostrarReservas";
	    } catch (WebClientResponseException e) {
	        model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
	        return VISTA_ERROR;
	    }
	}


	@PostMapping("/crear/")
	public String createReserva(@RequestParam("idVueloDTO") String idVueloDTO,
			@RequestParam("idPasajeroDTO") String idPasajeroDTO, @RequestParam("asiento") String asiento, Model model) {
		try {

			ReservaSoloIdDTO reservaSoloIdDTO = new ReservaSoloIdDTO(idVueloDTO, idPasajeroDTO, asiento);

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
		} catch (WebClientResponseException e) {
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
	}

	@PostMapping("/borrar/")
	public String borrarReserva(@RequestParam("idReservaDTO") String idReservaDTO, Model model) {
		try {

			ResponseEntity<Void> response = reservaWebClient.delete().uri("/{id}", idReservaDTO).retrieve()
					.toBodilessEntity().block();

			if (response != null && response.getStatusCode().is2xxSuccessful()) {
				model.addAttribute(MENSAJE, "Reserva eliminada con éxito");

				return "VistaBorrar";
			} else {
				model.addAttribute(MENSAJE, "Error al borrar la reserva");
				return VISTA_ERROR; // Maneja otros errores posibles
			}
	
		} catch (WebClientResponseException e) {
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
	}

	@PostMapping("/actualizar/")
	public String updateReserva(@RequestParam("idReservaDTO") String idReservaDTO, 
			@RequestParam("idVueloDTO") String idVueloDTO,
			@RequestParam("idPasajeroDTO") String idPasajeroDTO, 
			@RequestParam("asiento") String asiento, Model model) {
		try {
			
			
			ReservaSoloIdDTO reservaSoloIdDTO = new ReservaSoloIdDTO(idVueloDTO, idPasajeroDTO, asiento);
			
			System.out.println(reservaSoloIdDTO.getIdVueloDTO());

			ReservaDTO response = reservaWebClient.put()
					.uri("/{idReservaDTO}", idReservaDTO)
					.contentType(MediaType.APPLICATION_JSON)
					.bodyValue(reservaSoloIdDTO)
					.retrieve()
					.bodyToMono(ReservaDTO.class)
					.block();

			if (response != null) {
				model.addAttribute(MENSAJE, "La Reserva fue actualizada exitosamente");
				model.addAttribute("reservaDTO", response);
				return "VistaActualizarReserva";
			} else {
				return "error";
			}
		} catch (WebClientResponseException e) {
			model.addAttribute(MENSAJE, FALLO_CONEXION_WEBCLIENT);
			return VISTA_ERROR;
		}
	}


}
