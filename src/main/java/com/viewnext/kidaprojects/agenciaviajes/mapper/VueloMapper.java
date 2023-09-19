package com.viewnext.kidaprojects.agenciaviajes.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTO;
import com.viewnext.kidaprojects.agenciaviajes.model.Vuelo;

/**
 * Clase que proporciona métodos para mapear entre objetos Vuelo y VueloDTO.
 */
@Component
public class VueloMapper {

	/**
	 * Convierte un objeto Vuelo en un objeto VueloDTO.
	 *
	 * @param vuelo El objeto Vuelo a convertir.
	 * @return Un objeto VueloDTO con los mismos datos que el Vuelo de entrada.
	 */
	public VueloDTO toVueloDTO(Vuelo vuelo) {
		VueloDTO vueloDTO = new VueloDTO();
		vueloDTO.setIdVueloDTO(vuelo.getIdVuelo());
		vueloDTO.setOrigen(vuelo.getOrigen());
		vueloDTO.setDestino(vuelo.getDestino());
		vueloDTO.setFecha(vuelo.getFecha());
		return vueloDTO;
	}

	/**
	 * Convierte un objeto VueloDTO en un objeto Vuelo.
	 *
	 * @param vueloDTO El objeto VueloDTO a convertir.
	 * @return Un objeto Vuelo con los mismos datos que el VueloDTO de entrada.
	 */
	public Vuelo toVuelo(VueloDTO vueloDTO) {
		Vuelo vuelo = new Vuelo();
		vuelo.setIdVuelo(vueloDTO.getIdVueloDTO());
		vuelo.setOrigen(vueloDTO.getOrigen());
		vuelo.setDestino(vueloDTO.getDestino());
		vuelo.setFecha(vueloDTO.getFecha());
		return vuelo;
	}

	/**
	 * Convierte una lista de objetos Vuelo en una lista de objetos VueloDTO.
	 *
	 * @param listaVuelos La lista de objetos Vuelo a convertir.
	 * @return Una lista de objetos VueloDTO con los mismos datos que la lista de
	 *         Vuelos de entrada.
	 */
	public List<VueloDTO> toVueloDTOList(List<Vuelo> listaVuelos) {
		return listaVuelos.stream().map(this::toVueloDTO).collect(Collectors.toList());
	}

	/**
	 * Convierte una lista de objetos VueloDTO en una lista de objetos Vuelo.
	 *
	 * @param listaVueloDTOs La lista de objetos VueloDTO a convertir.
	 * @return Una lista de objetos Vuelo con los mismos datos que la lista de
	 *         VueloDTO de entrada.
	 */
	public List<Vuelo> toVueloList(List<VueloDTO> listaVueloDTOs) {
		return listaVueloDTOs.stream().map(this::toVuelo).collect(Collectors.toList());
	}
}
