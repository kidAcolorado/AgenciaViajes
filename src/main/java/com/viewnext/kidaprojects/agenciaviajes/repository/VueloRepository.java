package com.viewnext.kidaprojects.agenciaviajes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.kidaprojects.agenciaviajes.model.Vuelo;

/**
 * Interfaz que define un repositorio para la entidad {@code Vuelo}.
 * 
 * <p>
 * La interfaz {@code VueloRepository} proporciona métodos para realizar operaciones de persistencia
 * relacionadas con la entidad {@code Vuelo}. Extiende la interfaz {@code JpaRepository} de Spring Data JPA,
 * lo que le permite heredar métodos para realizar operaciones comunes de CRUD (Crear, Leer, Actualizar, Eliminar)
 * en la base de datos.
 * </p>
 * 
 * <p>
 * Esta interfaz es parte del sistema de persistencia utilizado para acceder y manipular datos de vuelos
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
public interface VueloRepository extends JpaRepository<Vuelo, Integer>{

}
