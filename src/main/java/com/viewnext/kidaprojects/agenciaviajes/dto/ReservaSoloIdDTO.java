package com.viewnext.kidaprojects.agenciaviajes.dto;

/**
 * La clase {@code ReservaSoloIdDTO} representa un objeto de transferencia de datos (DTO)
 * que contiene información esencial para crear una reserva de vuelo. Se utiliza para
 * transportar datos relacionados con la creación de reservas entre diferentes partes
 * de la aplicación, como el servicio web y la capa de presentación.
 * 
 * <p>
 * Un objeto {@code ReservaSoloIdDTO} contiene los siguientes atributos:
 * - {@code idVueloDTO}: El identificador único del vuelo asociado a la reserva.
 * - {@code idPasajeroDTO}: El identificador único del pasajero asociado a la reserva.
 * - {@code asiento}: El asiento reservado en el vuelo.
 * </p>
 * 
 * <p>
 * Esta clase proporciona un constructor para crear objetos {@code ReservaSoloIdDTO}
 * con los atributos especificados y métodos de acceso para obtener y establecer los
 * valores de sus atributos.
 * </p>
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
public class ReservaSoloIdDTO {

	private String idVueloDTO;
	private String idPasajeroDTO;
	private String asiento;

	public ReservaSoloIdDTO(String idVuelo, String idPasajero, String asiento) {
		this.idVueloDTO = idVuelo;
		this.idPasajeroDTO = idPasajero;
		this.asiento = asiento;

	}

	public ReservaSoloIdDTO() {

	}

	public String getIdVueloDTO() {
		return idVueloDTO;
	}

	public void setIdVueloDTO(String idVueloDTO) {
		this.idVueloDTO = idVueloDTO;
	}

	public String getIdPasajeroDTO() {
		return idPasajeroDTO;
	}

	public void setIdPasajeroDTO(String idPasajeroDTO) {
		this.idPasajeroDTO = idPasajeroDTO;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	
}
