package com.viewnext.kidaprojects.agenciaviajes.mappers;

import java.util.List;
import org.springframework.stereotype.Component;
import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTOSinId;
import com.viewnext.kidaprojects.agenciaviajes.model.Pasajero;

/**
 * La clase {@code PasajeroMapper} proporciona métodos para mapear objetos entre la entidad
 * {@code Pasajero} y su representación de transferencia de datos {@code PasajeroDTO}. También
 * proporciona métodos para mapear a partir de {@code PasajeroDTO} sin identificador.
 * 
 * <p>
 * Esta clase se utiliza para convertir objetos entre las capas de la aplicación, permitiendo la transferencia
 * de datos entre la capa de servicios y la capa de vista. Proporciona métodos para convertir un objeto
 * {@code Pasajero} en un objeto {@code PasajeroDTO}, un objeto {@code PasajeroDTO} en un objeto
 * {@code Pasajero}, y también para convertir un objeto {@code PasajeroDTOSinId} en un objeto {@code Pasajero}.
 * </p>
 * 
  <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
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
		//Realizar conversion entre idPasajero(int) y idPasajeroDTO(String)		 
		pasajeroDTO.setIdPasajeroDTO(String.valueOf(pasajero.getIdPasajero()));
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
		//Realizar conversion entre idPasajeroDTO(String) y idPasajero(int)
		pasajero.setIdPasajero(Integer.parseInt(pasajeroDTO.getIdPasajeroDTO()));
		pasajero.setNombre(pasajeroDTO.getNombre());
		pasajero.setApellido(pasajeroDTO.getApellido());
		pasajero.setFechaNacimiento(pasajeroDTO.getFechaNacimiento());
		return pasajero;
	}
	
	public Pasajero toPasajero(PasajeroDTOSinId pasajeroDTO) {
		Pasajero pasajero = new Pasajero();
		
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
				.toList();
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
				.toList();
	}

}