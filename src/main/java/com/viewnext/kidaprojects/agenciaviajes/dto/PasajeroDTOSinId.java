package com.viewnext.kidaprojects.agenciaviajes.dto;

import java.sql.Date;

public class PasajeroDTOSinId {
	
		
		private String nombre;
		
		private String apellido;
		private Date fechaNacimiento;
		
		
		public PasajeroDTOSinId(String nombre, String apellido, Date fechaNacimiento) {
			super();
			this.nombre = nombre;
			this.apellido = apellido;
			this.fechaNacimiento = fechaNacimiento;
		}
		
		public PasajeroDTOSinId() {
			
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
