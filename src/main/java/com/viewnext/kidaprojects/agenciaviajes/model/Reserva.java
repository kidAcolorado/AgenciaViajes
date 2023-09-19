package com.viewnext.kidaprojects.agenciaviajes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReserva;
	private String asiento;

	@ManyToOne
	@JoinColumn(name = "idVuelo")
	private Vuelo vuelo;

	@ManyToOne
	@JoinColumn(name = "idPasajero")
	private Pasajero pasajero;

	public Reserva(String asiento, Vuelo vuelo, Pasajero pasajero) {

		this.asiento = asiento;
		this.vuelo = vuelo;
		this.pasajero = pasajero;
	}

	public Reserva() {

	}

	
	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int id) {
		this.idReserva = id;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public Vuelo getVuelo() {
		return vuelo;
	}

	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}

}
