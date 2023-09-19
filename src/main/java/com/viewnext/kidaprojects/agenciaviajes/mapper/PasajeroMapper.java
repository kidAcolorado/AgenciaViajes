package com.viewnext.kidaprojects.agenciaviajes.mapper;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTO;
import com.viewnext.kidaprojects.agenciaviajes.model.Pasajero;

/**
 * Clase que proporciona métodos para mapear entre objetos Pasajero y PasajeroDTO.
 */
@Component
public class PasajeroMapper {

	/**
	 * Convierte un objeto Pasajero en un objeto PasajeroDTO.
	 *
	 * @param pasajero El objeto Pasajero a convertir.
	 * @return Un objeto PasajeroDTO con los mismos datos que el Pasajero de
	 *         entrada.
	 */
	public PasajeroDTO toPasajeroDTO(Pasajero pasajero) {
		PasajeroDTO pasajeroDTO = new PasajeroDTO();
		pasajeroDTO.setIdPasajeroDTO(pasajero.getIdPasajero());
		pasajeroDTO.setNombre(pasajero.getNombre());
		pasajeroDTO.setApellido(pasajero.getApellido());
		pasajeroDTO.setFechaNacimiento(pasajero.getFechaNacimiento());
		return pasajeroDTO;
	}

	/**
	 * Convierte un objeto PasajeroDTO en un objeto Pasajero.
	 *
	 * @param pasajeroDTO El objeto PasajeroDTO a convertir.
	 * @return Un objeto Pasajero con los mismos datos que el PasajeroDTO de
	 *         entrada.
	 */
	public Pasajero toPasajero(PasajeroDTO pasajeroDTO) {
		Pasajero pasajero = new Pasajero();
		pasajero.setIdPasajero(pasajeroDTO.getIdPasajeroDTO());
		pasajero.setNombre(pasajeroDTO.getNombre());
		pasajero.setApellido(pasajeroDTO.getApellido());
		pasajero.setFechaNacimiento(pasajeroDTO.getFechaNacimiento());
		return pasajero;
	}

	/**
	 * Convierte una lista de objetos Pasajero en una lista de objetos PasajeroDTO.
	 *
	 * @param listaPasajeros La lista de objetos Pasajero a convertir.
	 * @return Una lista de objetos PasajeroDTO con los mismos datos que la lista de
	 *         Pasajeros de entrada.
	 */
	public List<PasajeroDTO> toPasajeroDTOList(List<Pasajero> listaPasajeros) {
		return listaPasajeros.stream()
				.map(this::toPasajeroDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Convierte una lista de objetos PasajeroDTO en una lista de objetos Pasajero.
	 *
	 * @param listaPasajeroDTOs La lista de objetos PasajeroDTO a convertir.
	 * @return Una lista de objetos Pasajero con los mismos datos que la lista de
	 *         PasajeroDTO de entrada.
	 */
	public List<Pasajero> toPasajeroList(List<PasajeroDTO> listaPasajeroDTOs) {
		return listaPasajeroDTOs.stream()
				.map(this::toPasajero)
				.collect(Collectors.toList());
	}

}