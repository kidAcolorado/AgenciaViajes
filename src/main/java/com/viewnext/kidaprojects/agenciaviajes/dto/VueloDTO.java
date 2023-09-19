package com.viewnext.kidaprojects.agenciaviajes.dto;

import java.sql.Date;

public class VueloDTO {
	private int idVueloDTO;
	private String origen;
	private String destino;
	private Date fecha;

	public VueloDTO(String origen, String destino, Date fecha) {
		
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
	}

	public VueloDTO() {

	}



	public int getIdVueloDTO() {
		return idVueloDTO;
	}

	public void setIdVueloDTO(int idVueloDTO) {
		this.idVueloDTO = idVueloDTO;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
