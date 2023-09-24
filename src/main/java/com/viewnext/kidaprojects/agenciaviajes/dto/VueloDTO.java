package com.viewnext.kidaprojects.agenciaviajes.dto;

import java.sql.Date;

/**
 * La clase {@code VueloDTO} representa un vuelo en el sistema de reservas de la agencia de viajes.
 * Almacena información sobre el vuelo, incluyendo su identificador, origen, destino y fecha de salida.
 * 
 * <p>
 * Esta clase proporciona métodos para acceder y modificar esta información, así como para crear
 * instancias de vuelos. Es utilizada principalmente para transferir datos entre la capa de la vista
 * y la capa de servicios en la aplicación de reservas.
 * </p>
 * 
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
public class VueloDTO {
	private String idVueloDTO;
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



	public String getIdVueloDTO() {
		return idVueloDTO;
	}

	public void setIdVueloDTO(String idVueloDTO) {
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
