package com.viewnext.kidaprojects.agenciaviajes.dto;

/**
 * La clase {@code ReservaDTO} representa un objeto de transferencia de datos (DTO) que contiene información
 * sobre una reserva de vuelo. Se utiliza para transportar datos relacionados con las reservas entre diferentes
 * partes de la aplicación, como el servicio web y la capa de presentación.
 * 
 * <p>
 * Un objeto {@code ReservaDTO} contiene los siguientes atributos:
 * - {@code idReservaDTO}: El identificador único de la reserva.
 * - {@code asiento}: El asiento reservado en el vuelo.
 * - {@code vueloDTO}: Un objeto {@code VueloDTO} que representa el vuelo asociado a la reserva.
 * - {@code pasajeroDTO}: Un objeto {@code PasajeroDTO} que representa el pasajero asociado a la reserva.
 * </p>
 * 
 * <p>
 * Esta clase proporciona constructores para crear objetos {@code ReservaDTO} con diferentes atributos.
 * También incluye métodos de acceso para obtener y establecer los valores de sus atributos.
 * </p>
 * 
 *  <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
public class ReservaDTO {

	private String idReservaDTO;
	private String asiento;
	private VueloDTO vueloDTO;
	private PasajeroDTO pasajeroDTO;

	public ReservaDTO(String asiento, VueloDTO vueloDTO, PasajeroDTO pasajeroDTO) {

		this.asiento = asiento;
		this.vueloDTO = vueloDTO;
		this.pasajeroDTO = pasajeroDTO;
	}

	public ReservaDTO() {

	}



	public String getIdReservaDTO() {
		return idReservaDTO;
	}

	public void setIdReservaDTO(String idReservaDTO) {
		this.idReservaDTO = idReservaDTO;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public VueloDTO getVueloDTO() {
		return vueloDTO;
	}

	public void setVueloDTO(VueloDTO vueloDTO) {
		this.vueloDTO = vueloDTO;
	}

	public PasajeroDTO getPasajeroDTO() {
		return pasajeroDTO;
	}

	public void setPasajeroDTO(PasajeroDTO pasajeroDTO) {
		this.pasajeroDTO = pasajeroDTO;
	}
}
