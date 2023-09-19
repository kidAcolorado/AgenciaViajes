package com.viewnext.kidaprojects.agenciaviajes.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.viewnext.kidaprojects.agenciaviajes.dto.ReservaDTO;
import com.viewnext.kidaprojects.agenciaviajes.model.Reserva;

/**
 * Clase que proporciona métodos para mapear entre objetos Reserva y ReservaDTO.
 */
@Component
public class ReservaMapper {

	private final VueloMapper vueloMapper;
	private final PasajeroMapper pasajeroMapper;

	/**
	 * Constructor de ReservaMapper que recibe instancias de VueloMapper y
	 * PasajeroMapper.
	 *
	 * @param vueloMapper    El mapper de Vuelo necesario para mapear objetos Vuelo
	 *                       y VueloDTO.
	 * @param pasajeroMapper El mapper de Pasajero necesario para mapear objetos
	 *                       Pasajero y PasajeroDTO.
	 */
	public ReservaMapper(VueloMapper vueloMapper, PasajeroMapper pasajeroMapper) {
		this.vueloMapper = vueloMapper;
		this.pasajeroMapper = pasajeroMapper;
	}

	/**
	 * Convierte un objeto Reserva en un objeto ReservaDTO.
	 *
	 * @param reserva El objeto Reserva a convertir.
	 * @return Un objeto ReservaDTO con los mismos datos que la Reserva de entrada.
	 */
	public ReservaDTO toReservaDTO(Reserva reserva) {
		ReservaDTO reservaDTO = new ReservaDTO();
		reservaDTO.setIdReservaDTO(reserva.getIdReserva());
		reservaDTO.setAsiento(reserva.getAsiento());
		reservaDTO.setVueloDTO(vueloMapper.toVueloDTO(reserva.getVuelo()));
		reservaDTO.setPasajeroDTO(pasajeroMapper.toPasajeroDTO(reserva.getPasajero()));
		return reservaDTO;
	}

	/**
	 * Convierte un objeto ReservaDTO en un objeto Reserva.
	 *
	 * @param reservaDTO El objeto ReservaDTO a convertir.
	 * @return Un objeto Reserva con los mismos datos que el ReservaDTO de entrada.
	 */
	public Reserva toReserva(ReservaDTO reservaDTO) {
		Reserva reserva = new Reserva();
		reserva.setIdReserva(reservaDTO.getIdReservaDTO());
		reserva.setAsiento(reservaDTO.getAsiento());
		reserva.setVuelo(vueloMapper.toVuelo(reservaDTO.getVueloDTO()));
		reserva.setPasajero(pasajeroMapper.toPasajero(reservaDTO.getPasajeroDTO()));
		return reserva;
	}

	/**
	 * Convierte una lista de objetos Reserva en una lista de objetos ReservaDTO.
	 *
	 * @param listaReservas La lista de objetos Reserva a convertir.
	 * @return Una lista de objetos ReservaDTO con los mismos datos que la lista de
	 *         Reservas de entrada.
	 */
	public List<ReservaDTO> toReservaDTOList(List<Reserva> listaReservas) {
		return listaReservas.stream()
				.map(this::toReservaDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Convierte una lista de objetos ReservaDTO en una lista de objetos Reserva.
	 *
	 * @param listaReservaDTOs La lista de objetos ReservaDTO a convertir.
	 * @return Una lista de objetos Reserva con los mismos datos que la lista de
	 *         ReservaDTO de entrada.
	 */
	public List<Reserva> toReservaList(List<ReservaDTO> listaReservaDTOs) {
		return listaReservaDTOs.stream()
				.map(this::toReserva)
				.collect(Collectors.toList());
	}
}
