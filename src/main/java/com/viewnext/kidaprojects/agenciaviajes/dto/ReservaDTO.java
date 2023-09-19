package com.viewnext.kidaprojects.agenciaviajes.dto;

public class ReservaDTO {

	private int idReservaDTO;
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



	public int getIdReservaDTO() {
		return idReservaDTO;
	}

	public void setIdReservaDTO(int idReservaDTO) {
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
