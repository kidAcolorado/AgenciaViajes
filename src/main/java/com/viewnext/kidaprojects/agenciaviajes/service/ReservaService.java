package com.viewnext.kidaprojects.agenciaviajes.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;
import com.viewnext.kidaprojects.agenciaviajes.dto.ReservaDTO;
import com.viewnext.kidaprojects.agenciaviajes.mappers.PasajeroMapper;
import com.viewnext.kidaprojects.agenciaviajes.mappers.ReservaMapper;
import com.viewnext.kidaprojects.agenciaviajes.mappers.VueloMapper;
import com.viewnext.kidaprojects.agenciaviajes.model.Pasajero;
import com.viewnext.kidaprojects.agenciaviajes.model.Reserva;
import com.viewnext.kidaprojects.agenciaviajes.model.Vuelo;
import com.viewnext.kidaprojects.agenciaviajes.repository.ReservaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

/**
 * Servicio que gestiona las operaciones relacionadas con las reservas.
 *
 * <p>
 * La clase {@code ReservaService} proporciona métodos para buscar, crear, actualizar y eliminar reservas en la
 * base de datos. También ofrece funcionalidad para obtener listas de reservas y obtener una reserva por su ID.
 * </p>
 *
 * <p>
 * Esta clase utiliza objetos {@code VueloService} y {@code PasajeroService} para interactuar con los vuelos y
 * pasajeros asociados a las reservas. Además, utiliza un objeto {@code ReservaMapper} para realizar la conversión
 * entre entidades {@code Reserva} y DTOs {@code ReservaDTO}.
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
public class ReservaService implements ReservaRepository {

	@Autowired
	private ReservaRepository reservaRepository;

	@PersistenceContext
	private EntityManager entityManager;
	private final VueloService vueloService;
	private final PasajeroService pasajeroService;
	private final ReservaMapper reservaMapper;
	

	public ReservaService(ReservaMapper reservaMapper, VueloService vueloService, PasajeroService pasajeroService,
			VueloMapper vueloMapper, PasajeroMapper pasajeroMapper) {
		this.reservaMapper = reservaMapper;
		this.vueloService = vueloService;
		this.pasajeroService = pasajeroService;
		
	}

	// -----------------------------------------------------
	// ------ METODOS PARA BUSCAR Y MANDAR RESULTADOS ------
	// -----------------------------------------------------

	/**
	 * Recupera todas las reservas de la base de datos.
	 *
	 * @return una lista de todas las reservas existentes.
	 */
	@Override
	public List<Reserva> findAll() {
		List<Reserva> listaReservas;

		listaReservas = reservaRepository.findAll();

		return listaReservas;
	}

	/**
	 * Obtiene una lista de todos los objetos ReservaDTO correspondientes a todas
	 * las reservas existentes.
	 * 
	 * @return Una lista de ReservaDTO que representa todas las reservas existentes.
	 */
	public List<ReservaDTO> getAllReservas() {
		List<ReservaDTO> listaReservasDTO;
		List<Reserva> listaReservas;

		listaReservas = findAll();

		listaReservasDTO = reservaMapper.toReservaDTOList(listaReservas);

		return listaReservasDTO;
	}

	/**
	 * Busca y recupera una entidad de tipo Reserva de la base de datos utilizando
	 * su identificador (ID).
	 * 
	 * @param id el identificador de la reserva a buscar.
	 * @return un Optional que contiene la reserva encontrada o un Optional vacío si
	 *         no se encontró ninguna entidad con el ID proporcionado.
	 */
	@Override
	public Optional<Reserva> findById(Integer id) {

		return reservaRepository.findById(id);
	}

	/**
	 * Obtiene una reserva por su ID.
	 * 
	 * @param id El ID de la reserva a obtener.
	 * @return El objeto ReservaDTO correspondiente a la reserva encontrada.
	 * @throws EntityNotFoundException si no se encuentra la reserva con el ID
	 *                                 especificado.
	 */
	public ReservaDTO getReservaByid(Integer id) throws EntityNotFoundException {
		Optional<Reserva> optionalReserva = findById(id);

		if (optionalReserva.isPresent()) {
			return reservaMapper.toReservaDTO(optionalReserva.get());
		} else {
			throw new EntityNotFoundException();
		}
	}

	/**
	 * Obtiene una lista de ReservaDTO correspondientes a las reservas realizadas
	 * por un pasajero.
	 * 
	 * @param id El ID del pasajero para el cual se obtendrán las reservas.
	 * @return Una lista de ReservaDTO correspondientes a las reservas del pasajero.
	 */
	public List<ReservaDTO> obtenerReservasPorPasajero(Integer id) {
		try {
			List<Reserva> listaReservasPorPasajero;
			List<ReservaDTO> lisRerservaDTOsPorPasajero;

			String jpql = "SELECT r FROM Reserva r INNER JOIN r.pasajero p WHERE p.idPasajero = :id";

			TypedQuery<Reserva> query = entityManager.createQuery(jpql, Reserva.class);
			query.setParameter("id", id);
			listaReservasPorPasajero = query.getResultList();

			lisRerservaDTOsPorPasajero = reservaMapper.toReservaDTOList(listaReservasPorPasajero);

			return lisRerservaDTOsPorPasajero;

		} catch (PersistenceException e) {
			// Manejar la excepción de JPA de acuerdo a la lógica de tu aplicación
			// Puedes registrar errores, lanzar excepciones personalizadas, etc.
			e.printStackTrace();

			return Collections.emptyList(); // Por ejemplo, retornar una lista vacía en caso de error
		}
	}

	/**
	 * Obtiene una lista de ReservaDTO correspondientes a las reservas realizadas
	 * para un vuelo específico.
	 *
	 * @param id El ID del vuelo para el cual se obtendrán las reservas.
	 * @return Una lista de ReservaDTO correspondientes a las reservas del vuelo.
	 */
	public List<ReservaDTO> obtenerReservasPorVuelo(Integer id) {
		try {
			List<Reserva> listaReservasPorVuelo;
			List<ReservaDTO> listaReservaDTOsPorVuelo;

			String jpql = "SELECT r FROM Reserva r INNER JOIN r.vuelo v WHERE v.idVuelo = :id";

			TypedQuery<Reserva> query = entityManager.createQuery(jpql, Reserva.class);
			query.setParameter("id", id);
			listaReservasPorVuelo = query.getResultList();

			listaReservaDTOsPorVuelo = reservaMapper.toReservaDTOList(listaReservasPorVuelo);

			return listaReservaDTOsPorVuelo;

		} catch (PersistenceException e) {
			// Manejar la excepción de JPA de acuerdo a la lógica de tu aplicación
			// Puedes registrar errores, lanzar excepciones personalizadas, etc.
			e.printStackTrace();

			return Collections.emptyList(); // Por ejemplo, retornar una lista vacía en caso de error
		}
	}

	// -----------------------------------------------------
	// ---------------- MÉTODOS PARA CREAR -----------------
	// -----------------------------------------------------

	/**
	 * Guarda una reserva en la base de datos.
	 *
	 * @param entity la reserva a guardar.
	 * @return la reserva guardada.
	 */
	@Override
	public <S extends Reserva> S save(S entity) {

		reservaRepository.save(entity);

		return reservaRepository.save(entity);
	}

	/**
	 * Crea una reserva para un vuelo y pasajero específicos, con el asiento
	 * especificado.
	 * 
	 * @param idVuelo    El ID del vuelo para el cual se creará la reserva.
	 * @param idPasajero El ID del pasajero para el cual se creará la reserva.
	 * @param asiento    El número de asiento para la reserva.
	 * @return El objeto ReservaDTO correspondiente a la reserva creada.
	 * @throws EntityNotFoundException Si el vuelo o el pasajero no se encuentran.
	 */
	public ReservaDTO createReservaByIdVueloIdPasajeroAsiento(Integer idVuelo, Integer idPasajero, String asiento)
			throws EntityNotFoundException {
		Optional<Vuelo> optionalVuelo = vueloService.findById(idPasajero);
		Optional<Pasajero> optionalPasajero = pasajeroService.findById(idPasajero);

		if (optionalPasajero.isPresent() && optionalVuelo.isPresent()) {
			Pasajero pasajero = optionalPasajero.get();
			Vuelo vuelo = optionalVuelo.get();
			Reserva reserva = new Reserva(asiento, vuelo, pasajero);

			return reservaMapper.toReservaDTO(save(reserva));
		} else {
			throw new EntityNotFoundException();
		}

	}

	// -----------------------------------------------------
	// --------------- MÉTODOS PARA BORRAR -----------------
	// -----------------------------------------------------

	/**
	 * Comprueba si existe una reserva con el identificador (ID) proporcionado en el
	 * repositorio de reservas.
	 *
	 * @param id El ID de la reserva que se va a buscar.
	 * @return true si existe una reserva con el ID proporcionado, false si no se
	 *         encuentra ninguna reserva con ese ID.
	 */
	@Override
	public boolean existsById(Integer id) {
		return reservaRepository.existsById(id);
	}

	/**
	 * Elimina una reserva de la base de datos utilizando su identificador (ID).
	 *
	 * @param id el identificador de la reserva a eliminar.
	 */
	@Override
	public void deleteById(Integer id) {

		reservaRepository.deleteById(id);
	}

	/**
	 * Elimina una reserva por su ID.
	 * 
	 * @param id El ID de la reserva a eliminar.
	 * @throws EntityNotFoundException si no se encuentra la reserva con el ID
	 *                                 especificado.
	 */
	public void deleteReservaById(Integer id) throws EntityNotFoundException {
		if (!existsById(id)) {
			throw new EntityNotFoundException();
		}

		deleteById(id);
	}

	/**
	 * Elimina una reserva de la base de datos.
	 *
	 * @param entity la reserva a eliminar.
	 */
	@Override
	public void delete(Reserva entity) {

		reservaRepository.delete(entity);
	}

	/**
	 * Elimina una reserva de la base de datos.
	 *
	 * @param reservaDTO El objeto ReservaDTO que representa la reserva a eliminar.
	 */
	public void delete(ReservaDTO reservaDTO) {
		Reserva reserva = reservaMapper.toReserva(reservaDTO);

		delete(reserva);
	}

	// -----------------------------------------------------
	// --------------- MÉTODOS PARA UPDATE -----------------
	// -----------------------------------------------------

	/**
	 * Actualiza una reserva por su ID utilizando los datos proporcionados en el
	 * objeto ReservaDTO.
	 * 
	 * @param id         El ID de la reserva a actualizar.
	 * @param reservaDTO El objeto ReservaDTO con los datos actualizados.
	 * @return El objeto ReservaDTO correspondiente a la reserva actualizada.
	 * @throws EntityNotFoundException si no se encuentra la reserva con el ID
	 *                                 especificado.
	 */
	public ReservaDTO updateReserva(Integer idReserva, Integer idPasajero, Integer idVuelo, String asiento) {
		
		Optional<Reserva> optionalReserva = findById(idReserva);
		Optional<Vuelo> optionalVuelo = vueloService.findById(idVuelo);
		Optional<Pasajero> optionalPasajero = pasajeroService.findById(idPasajero);

		if (optionalReserva.isEmpty() || optionalPasajero.isEmpty() || optionalVuelo.isEmpty()) {
			throw new EntityNotFoundException();
		}

		Pasajero pasajero = optionalPasajero.get();
		Vuelo vuelo = optionalVuelo.get();
		
		Reserva reserva = new Reserva(asiento, vuelo, pasajero);
		reserva.setIdReserva(idReserva);

		return reservaMapper.toReservaDTO(save(reserva));

	}

	

	// MÉTODOS POR IMPLEMENTAR EN UN FÚTURO:

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Reserva> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Reserva> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Reserva> entities) {
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
	public Reserva getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva getReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Reserva> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Reserva> List<S> findAll(Example<S> example, org.springframework.data.domain.Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Reserva> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reserva> findAllById(Iterable<Integer> ids) {
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
	public void deleteAll(Iterable<? extends Reserva> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Reserva> findAll(org.springframework.data.domain.Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Reserva> findAll(org.springframework.data.domain.Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Reserva> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends Reserva> Page<S> findAll(Example<S> example, org.springframework.data.domain.Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Reserva> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Reserva> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Reserva, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
