package com.viewnext.kidaprojects.agenciaviajes.dto;

import java.sql.Date;

/**
 * La clase {@code PasajeroDTOSinId} representa un objeto de transferencia de datos (DTO) que contiene información
 * sobre un pasajero sin incluir un identificador único. Se utiliza para transportar datos relacionados con los pasajeros
 * entre diferentes partes de la aplicación, como el servicio web y la capa de presentación.
 * 
 * <p>
 * Un objeto {@code PasajeroDTOSinId} contiene los siguientes atributos:
 * - {@code nombre}: El nombre del pasajero.
 * - {@code apellido}: El apellido del pasajero.
 * - {@code fechaNacimiento}: La fecha de nacimiento del pasajero.
 * </p>
 * 
 * <p>
 * Esta clase proporciona constructores para crear objetos {@code PasajeroDTOSinId} con diferentes atributos.
 * También incluye métodos de acceso para obtener y establecer los valores de sus atributos.
 * </p>
 * 
 *  <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
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
