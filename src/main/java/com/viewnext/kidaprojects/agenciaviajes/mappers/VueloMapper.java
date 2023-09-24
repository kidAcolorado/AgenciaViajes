package com.viewnext.kidaprojects.agenciaviajes.mappers;

import java.util.List;
import org.springframework.stereotype.Component;
import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTOSinId;
import com.viewnext.kidaprojects.agenciaviajes.model.Vuelo;

/**
 * Clase que proporciona métodos para mapear entre objetos Vuelo y VueloDTO.
 * 
 * <p>
 * La clase {@code VueloMapper} se encarga de convertir objetos entre la entidad {@code Vuelo}
 * y su representación de transferencia de datos {@code VueloDTO}. Esta clase incluye métodos para
 * realizar estas conversiones, incluyendo la conversión entre el identificador del vuelo y su
 * versión en el DTO.
 * </p>
 * 
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
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
		//Realizar conversión entre idVuelo(int) y idVueloDTO(String)
		vueloDTO.setIdVueloDTO(String.valueOf(vuelo.getIdVuelo()));
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
		//Realizar conversión entre idVueloDTO(String) y idVuelo(int)
		vuelo.setIdVuelo(Integer.parseInt(vueloDTO.getIdVueloDTO()));
		vuelo.setOrigen(vueloDTO.getOrigen());
		vuelo.setDestino(vueloDTO.getDestino());
		vuelo.setFecha(vueloDTO.getFecha());
		return vuelo;
	}
	
	public Vuelo toVuelo(VueloDTOSinId vueloDTO) {
		Vuelo vuelo = new Vuelo();
		
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
		return listaVuelos.stream()
				.map(this::toVueloDTO)
				.toList();
	}

	/**
	 * Convierte una lista de objetos VueloDTO en una lista de objetos Vuelo.
	 *
	 * @param listaVueloDTOs La lista de objetos VueloDTO a convertir.
	 * @return Una lista de objetos Vuelo con los mismos datos que la lista de
	 *         VueloDTO de entrada.
	 */
	public List<Vuelo> toVueloList(List<VueloDTO> listaVueloDTOs) {
		return listaVueloDTOs.stream()
				.map(this::toVuelo)
				.toList();
	}
}
