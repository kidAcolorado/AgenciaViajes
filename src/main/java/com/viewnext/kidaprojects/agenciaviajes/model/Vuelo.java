package com.viewnext.kidaprojects.agenciaviajes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "vuelos")
public class Vuelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVuelo;
	private String origen;
	private String destino;
	private Date fecha;

	public Vuelo(String origen, String destino, Date fecha) {
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
	}

	public Vuelo() {

	}

	
	

	public int getIdVuelo() {
		return idVuelo;
	}

	public void setIdVuelo(int idVuelo) {
		this.idVuelo = idVuelo;
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