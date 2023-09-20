package com.viewnext.kidaprojects.agenciaviajes.dto;

import java.sql.Date;

public class PasajeroDTO {
	private String idPasajeroDTO;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;

	public PasajeroDTO(String idPasajeroDTO, String nombre, String apellido, Date fechaNacimiento) {

		this.idPasajeroDTO = idPasajeroDTO;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

	public PasajeroDTO() {

	}

	
	public String getIdPasajeroDTO() {
		return idPasajeroDTO;
	}

	public void setIdPasajeroDTO(String idPasajeroDTO) {
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
