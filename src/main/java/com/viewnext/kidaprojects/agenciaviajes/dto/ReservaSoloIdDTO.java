package com.viewnext.kidaprojects.agenciaviajes.dto;

public class ReservaSoloIdDTO {

	
	
	private String idReserva;
	private String idVuelo;
	private String idPasajero;
	private String asiento;
	
	public ReservaSoloIdDTO() {
		
	}
	
	public ReservaSoloIdDTO(String idVuelo, String idPasajero, String asiento) {
		this.idVuelo = idVuelo;
		this.idPasajero = idPasajero;
		this.asiento = asiento;
		
	}
	
	public ReservaSoloIdDTO(String idReserva, String idVuelo, String idPasajero, String asiento) {
		this.idReserva = idReserva;
		this.idVuelo = idVuelo;
		this.idPasajero = idPasajero;
		this.asiento = asiento;
	}

	
	public String getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(String idReserva) {
		this.idReserva = idReserva;
	}

	public String getIdVuelo() {
		return idVuelo;
	}

	public void setIdVuelo(String idVuelo) {
		this.idVuelo = idVuelo;
	}

	public String getIdPasajero() {
		return idPasajero;
	}

	public void setIdPasajero(String idPasajero) {
		this.idPasajero = idPasajero;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}
	
	
	
	
}
