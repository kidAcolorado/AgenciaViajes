package com.viewnext.kidaprojects.agenciaviajes.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * 
 */
@Entity
@Table(name = "pasajeros")
public class Pasajero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPasajero;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	
	@OneToMany(mappedBy = "pasajero") // Anotación inversa
    private List<Reserva> reservas;
	

	public Pasajero(int idPasajero, String nombre, String apellido, Date fechaNacimiento) {
		super();
		this.idPasajero = idPasajero;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}



	public Pasajero(String nombre, String apellido, Date fechaNacimiento) {
		super();

		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

	

	public Pasajero() {
		
	}



	public int getIdPasajero() {
		return idPasajero;
	}

	public void setIdPasajero(int idPasajero) {
		this.idPasajero = idPasajero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	

}
