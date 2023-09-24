package com.viewnext.kidaprojects.agenciaviajes.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;
import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTOSinId;
import com.viewnext.kidaprojects.agenciaviajes.mappers.PasajeroMapper;
import com.viewnext.kidaprojects.agenciaviajes.model.Pasajero;
import com.viewnext.kidaprojects.agenciaviajes.repository.PasajeroRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Servicio que gestiona las operaciones relacionadas con los pasajeros.
 *
 * <p>
 * La clase {@code PasajeroService} proporciona métodos para buscar, crear, actualizar y eliminar pasajeros en la
 * base de datos. También ofrece funcionalidad para obtener listas de pasajeros y obtener un pasajero por su ID.
 * </p>
 *
 * <p>
 * Esta clase utiliza un objeto {@code PasajeroMapper} para realizar la conversión entre entidades {@code Pasajero} y
 * DTOs {@code PasajeroDTO}.
 * </p>
 * 
 *  <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 19 de septiembre de 2023
 */
@Service
public class PasajeroService implements PasajeroRepository {

	@Autowired
	private PasajeroRepository pasajeroRepository;

	private final PasajeroMapper pasajeroMapper;

	public PasajeroService(PasajeroMapper pasajeroMapper) {
		this.pasajeroMapper = pasajeroMapper;
	}

	// -----------------------------------------------------
	// ------ METODOS PARA BUSCAR Y MANDAR RESULTADOS ------
	// -----------------------------------------------------

	/**
	 * Recupera todas las entidades de tipo Pasajero de la base de datos.
	 *
	 * @return una lista que contiene todas las entidades Pasajero de la base de
	 *         datos.
	 */
	@Override
	public List<Pasajero> findAll() {
		List<Pasajero> listaPasajeros;

		listaPasajeros = pasajeroRepository.findAll();

		return listaPasajeros;
	}

	/**
	 * Obtiene todos los pasajeros.
	 *
	 * @return Lista de PasajeroDTO con todos los pasajeros.
	 */
	public List<PasajeroDTO> getAllPasajeros() {
		List<Pasajero> listaPasajeros;
		List<PasajeroDTO> listaPasajerosDTO;

		listaPasajeros = findAll();

		listaPasajerosDTO = pasajeroMapper.toPasajeroDTOList(listaPasajeros);

		return listaPasajerosDTO;
	}

	/**
	 * Busca y recupera una entidad de tipo Pasajero de la base de datos utilizando
	 * su identificador (ID).
	 *
	 * @param id el identificador de la entidad Pasajero a buscar.
	 * @return un Optional que contiene la entidad Pasajero encontrada, o un
	 *         Optional vacío si no se encontró ninguna entidad con el ID
	 *         proporcionado.
	 */
	@Override
	public Optional<Pasajero> findById(Integer id) {
		return pasajeroRepository.findById(id);
	}

	/**
	 * Obtiene un pasajero por su ID.
	 * 
	 * @param idPasajero ID del pasajero a buscar.
	 * @return El PasajeroDTO si se encuentra el pasajero.
	 * @throws EntityNotFoundException Si el pasajero no se encuentra.
	 */
	public PasajeroDTO getPasajeroById(Integer id) throws EntityNotFoundException {
		Optional<Pasajero> optionalPasajero = findById(id);

		if (optionalPasajero.isPresent()) {
			return pasajeroMapper.toPasajeroDTO(optionalPasajero.get());
		} else {
			throw new EntityNotFoundException();
		}
	}

	// -----------------------------------------------------
	// ---------------- MÉTODOS PARA CREAR -----------------
	// -----------------------------------------------------

	/**
	 * Guarda una entidad de tipo Pasajero en la base de datos.
	 *
	 * @param entity la entidad Pasajero a guardar en la base de datos.
	 * @return la entidad Pasajero guardada en la base de datos.
	 */
	@Override
	public <S extends Pasajero> S save(S entity) {

		pasajeroRepository.save(entity);

		return entity;
	}

	/**
	 * Crea un nuevo pasajero a partir de los datos proporcionados.
	 * 
	 * @param pasajeroDTO El objeto DTO que contiene los datos del pasajero.
	 * @return El PasajeroDTO del nuevo pasajero creado.
	 */
	public PasajeroDTO createPasajero(PasajeroDTOSinId pasajeroDTOSinID) {

		Pasajero pasajero = pasajeroMapper.toPasajero(pasajeroDTOSinID);

		return pasajeroMapper.toPasajeroDTO(save(pasajero));
	}

	// -----------------------------------------------------
	// --------------- MÉTODOS PARA BORRAR -----------------
	// -----------------------------------------------------

	/**
	 * Comprueba si existe un pasajero con el identificador (ID) proporcionado en la
	 * base de datos.
	 *
	 * @param id El ID del pasajero que se va a buscar.
	 * @return true si existe un pasajero con el ID proporcionado, false si no se
	 *         encuentra ningún pasajero con ese ID.
	 */
	@Override
	public boolean existsById(Integer id) {

		return pasajeroRepository.existsById(id);
	}

	/**
	 * Elimina un pasajero de la base de datos por su identificador (ID).
	 *
	 * @param id el identificador del pasajero a eliminar.
	 */
	@Override
	public void deleteById(Integer id) {

		pasajeroRepository.deleteById(id);
	}

	/**
	 * Elimina un pasajero por su ID.
	 *
	 * @param idPasajero ID del pasajero a eliminar.
	 * @throws EntityNotFoundException Si el pasajero no se encuentra.
	 */
	public void deletePasajeroById(Integer id) throws EntityNotFoundException {
		if (!existsById(id)) {
			throw new EntityNotFoundException();
		}

		deleteById(id);
	}

	/**
	 * Elimina un pasajero de la base de datos.
	 *
	 * @param entity El objeto Pasajero que se va a eliminar.
	 */
	@Override
	public void delete(Pasajero entity) {

		pasajeroRepository.delete(entity);
	}

	/**
	 * Elimina un pasajero de la base de datos.
	 *
	 * @param entity el pasajero a eliminar.
	 */
	public void delete(PasajeroDTO pasajeroDTO) {
		Pasajero pasajero = pasajeroMapper.toPasajero(pasajeroDTO);

		delete(pasajero);

	}

	// -----------------------------------------------------
	// --------------- MÉTODOS PARA UPDATE -----------------
	// -----------------------------------------------------

	/**
	 * Actualiza un pasajero en la base de datos por su ID.
	 *
	 * @param id          El ID del pasajero a actualizar.
	 * @param pasajeroDTO El objeto PasajeroDTO que contiene los nuevos datos del
	 *                    pasajero.
	 * @return El PasajeroDTO que representa el pasajero actualizado.
	 * @throws EntityNotFoundException Si no se encuentra el pasajero con el ID
	 *                                 especificado.
	 */
	public PasajeroDTO updatePasajeroById(Integer id, PasajeroDTO pasajeroDTO) throws EntityNotFoundException {
		// Verificar si el pasajero con la ID especificada existe en la base de datos
		Optional<Pasajero> optionalPasajero = findById(id);
		if (optionalPasajero.isEmpty()) {
			throw new EntityNotFoundException();
		}

		// Cuerpo del método en caso de que existe el Pasajero:
		Pasajero pasajero;
		Pasajero pasajeroActualizado;
		PasajeroDTO pasajeroDTOActualizado;

		// Actualizar los datos del pasajero con los valores recibidos en el DTO
		pasajero = pasajeroMapper.toPasajero(pasajeroDTO);
		pasajero.setIdPasajero(id); // Esta línea podría ser útil, por si en un futuro el DTO no viene con el id
		// del pasajero que queremos actualizar

		// Guardar el pasajero actualizado en la base de datos
		pasajeroActualizado = save(pasajero);

		// Mapear el pasajero actualizado a un DTO y devolverlo
		pasajeroDTOActualizado = pasajeroMapper.toPasajeroDTO(pasajeroActualizado);

		return pasajeroDTOActualizado;
	}

	// MÉTODOS POR IMPLEMENTAR EN UN FÚTURO:

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Pasajero> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Pasajero> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Pasajero> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public Pasajero getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pasajero getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pasajero getReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Pasajero> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Pasajero> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Pasajero> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pasajero> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Pasajero> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Pasajero> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Pasajero> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Pasajero> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends Pasajero> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Pasajero> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Pasajero> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Pasajero, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}

}