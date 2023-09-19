package com.viewnext.kidaprojects.agenciaviajes.dto;

import java.sql.Date;

public class PasajeroDTO {
	private int idPasajeroDTO;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;

	public PasajeroDTO(int id, String nombre, String apellido, Date fechaNacimiento) {

		this.idPasajeroDTO = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

	public PasajeroDTO() {

	}

	
	public int getIdPasajeroDTO() {
		return idPasajeroDTO;
	}

	public void setIdPasajeroDTO(int idPasajeroDTO) {
		this.idPasajeroDTO = idPasajeroDTO;
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
