package com.viewnext.kidaprojects.agenciaviajes.dto;

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
