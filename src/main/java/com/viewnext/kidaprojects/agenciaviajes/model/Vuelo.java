package com.viewnext.kidaprojects.agenciaviajes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.List;

/**
 * Clase que representa un vuelo en el sistema de gestión de viajes.
 * 
 * <p>
 * La clase {@code Vuelo} almacena información sobre un vuelo, incluyendo su identificador,
 * origen, destino y fecha de partida.
 * </p>
 * 
 * <p>
 * Esta clase es una entidad JPA que se mapea a una tabla llamada "vuelos" en la base de datos.
 * El identificador del vuelo se genera automáticamente mediante una estrategia de generación de
 * claves primarias.
 * </p>
 * 
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
@Entity
@Table(name = "vuelos")
public class Vuelo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVuelo;
	private String origen;
	private String destino;
	private Date fecha;
	
	@OneToMany(mappedBy = "vuelo") // Anotación inversa
    private List<Reserva> reservas;

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