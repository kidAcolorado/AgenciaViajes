package com.viewnext.kidaprojects.agenciaviajes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.kidaprojects.agenciaviajes.model.Reserva;

/**
 * Interfaz que define un repositorio para la entidad {@code Reserva}.
 * 
 * <p>
 * La interfaz {@code ReservaRepository} proporciona métodos para realizar operaciones de persistencia
 * relacionadas con la entidad {@code Reserva}. Extiende la interfaz {@code JpaRepository} de Spring Data JPA,
 * lo que le permite heredar métodos para realizar operaciones comunes de CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la base de datos.
 * </p>
 * 
 * <p>
 * Esta interfaz es parte del sistema de persistencia utilizado para acceder y manipular datos de reservas
 * en la base de datos.
 * </p>
 * 
 *  <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
public interface ReservaRepository extends JpaRepository<Reserva, Integer>{

}
