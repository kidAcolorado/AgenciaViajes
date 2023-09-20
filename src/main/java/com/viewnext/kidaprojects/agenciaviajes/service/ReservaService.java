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
import com.viewnext.kidaprojects.agenciaviajes.dto.PasajeroDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.ReservaDTO;
import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTO;
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

@Service
public class ReservaService implements ReservaRepository {

	@Autowired
	private ReservaRepository reservaRepository;

	@PersistenceContext
	private EntityManager entityManager;
	private final VueloService vueloService;
	private final PasajeroService pasajeroService;
	private final ReservaMapper reservaMapper;
	private final VueloMapper vueloMapper;
	private final PasajeroMapper pasajeroMapper;

	public ReservaService(ReservaMapper reservaMapper, VueloService vueloService, PasajeroService pasajeroService,
			VueloMapper vueloMapper, PasajeroMapper pasajeroMapper) {
		this.reservaMapper = reservaMapper;
		this.vueloService = vueloService;
		this.pasajeroService = pasajeroService;
		this.vueloMapper = vueloMapper;
		this.pasajeroMapper = pasajeroMapper;
	}

	// MÉTODOS IMPLEMENTADOS:
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
	 * Busca una reserva por su identificador (ID).
	 *
	 * @param id el identificador de la reserva a buscar.
	 * @return un Optional que contiene la reserva encontrada o un valor vacío si no
	 *         se encuentra.
	 */
	@Override
	public Optional<Reserva> findById(Integer id) {

		return reservaRepository.findById(id);
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
	 * Elimina una reserva de la base de datos.
	 *
	 * @param entity la reserva a eliminar.
	 */
	@Override
	public void delete(Reserva entity) {

		reservaRepository.delete(entity);
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
	 * Actualiza una reserva por su ID utilizando los datos proporcionados en el
	 * objeto ReservaDTO.
	 * 
	 * @param id         El ID de la reserva a actualizar.
	 * @param reservaDTO El objeto ReservaDTO con los datos actualizados.
	 * @return El objeto ReservaDTO correspondiente a la reserva actualizada.
	 * @throws EntityNotFoundException si no se encuentra la reserva con el ID
	 *                                 especificado.
	 */
	public ReservaDTO updateReservaById(Integer id, ReservaDTO reservaDTO) {
		// Verificar si la reserva con la ID especificada existe en la base de datos
		Optional<Reserva> optionalReserva = findById(id);
		if (optionalReserva.isEmpty()) {
			throw new EntityNotFoundException();
		}

		// Cuerpo del método en caso de que existe la Reserva:
		Reserva reserva;
		Reserva reservaActualizada;
		ReservaDTO reservaDTOActualizada;

		// Actualizar los datos de la reserva con los valores recibidos en el DTO
		reserva = reservaMapper.toReserva(reservaDTO);
		reserva.setIdReserva(id);
		// Esta línea podría ser útil, por si en un futuro el DTO no viene con el id
		// de la reserva que queremos actualizar

		// Guardar la reserva actualizada en la base de datos
		reservaActualizada = save(reserva);

		// Mapear la reserva actualizada a un DTO y devolverlo en la respuesta
		reservaDTOActualizada = reservaMapper.toReservaDTO(reservaActualizada);

		return reservaDTOActualizada;
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

			String jpql = "SELECT r FROM Reserva r INNER JOIN r.Pasajero p WHERE p.id = :id";

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

			String jpql = "SELECT r FROM Reserva r INNER JOIN r.Vuelo v WHERE v.id = :id";

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
	
	/**
	 * Obtiene una reserva por su ID de vuelo, ID de pasajero y número de asiento.
	 * 
	 * @param idVuelo    El ID del vuelo asociado a la reserva.
	 * @param idPasajero El ID del pasajero asociado a la reserva.
	 * @param asiento    El número de asiento seleccionado para la reserva.
	 * @return El objeto ReservaDTO correspondiente a la reserva encontrada.
	 * @throws EntityNotFoundException Si el vuelo, el pasajero o la reserva no se encuentran.
	 */
	public ReservaDTO getReservaByIdVueloIdPasajeroAsiento(Integer idVuelo, Integer idPasajero, String asiento)
	        throws EntityNotFoundException {
	    Optional<Vuelo> optionalVuelo;
	    Optional<Pasajero> optionalPasajero;

	    // Buscar el vuelo y el pasajero por sus respectivos IDs
	    optionalVuelo = vueloService.findById(idVuelo);
	    optionalPasajero = pasajeroService.findById(idPasajero);

	    if (optionalVuelo.isPresent() && optionalPasajero.isPresent()) {
	        // Obtener la ReservaDTO llamando a otro método
	        ReservaDTO reservaDTO = getReservaDTOByOptionalsAndAsiento(optionalVuelo, optionalPasajero, asiento);
	        return reservaDTO;
	    } else {
	        // Lanzar una excepción si alguno de los elementos no se encuentra
	        throw new EntityNotFoundException();
	    }
	}


	// MÉTODO PARA CREAR LA RESERVA: (Compuesto por este método que invoca a otros
	// dos: getReservaDTOByOptionalsAndAsiento y createReservaByReservaDTO)
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
		Optional<Vuelo> optionalVuelo;
		Optional<Pasajero> optionalPasajero;

		// Buscar el vuelo y el pasajero por sus respectivos IDs
		optionalVuelo = vueloService.findById(idVuelo);
		optionalPasajero = pasajeroService.findById(idPasajero);

		if (optionalVuelo.isPresent() && optionalPasajero.isPresent()) {
			ReservaDTO reservaDTO;
			ReservaDTO reservaDTOcreada;
			/*
			 * Para realizar la resreva vamos a invocar a dos métodos distintos: Por un lado
			 * llamaremos al método getReservaDTOByOptionalsAndAsiento mediante el cual
			 * obtendremos un objeto ReservaDTO a partir de los Optionals y el asiento
			 */
			reservaDTO = getReservaDTOByOptionalsAndAsiento(optionalVuelo, optionalPasajero, asiento);

			/*
			 * Por otro lado crearemos a reserva invocando al método createReserva, mediante
			 * el cual haremos el save de la entidad reserva a partir del reservaDTO que
			 * obtuvimos en el anterior método
			 */
			reservaDTOcreada = createReservaByReservaDTO(reservaDTO);

			// Devolvemos el objeto DTO de la reserva creada
			return reservaDTOcreada;
		} else {
			throw new EntityNotFoundException();
		}
	}

	/**
	 * Obtiene un objeto ReservaDTO a partir de los Optionals de Vuelo y Pasajero,
	 * junto con el número de asiento.
	 * 
	 * @param optionalVuelo    El Optional que contiene el Vuelo asociado a la
	 *                         reserva.
	 * @param optionalPasajero El Optional que contiene el Pasajero asociado a la
	 *                         reserva.
	 * @param asiento          El número de asiento para la reserva.
	 * @return El objeto ReservaDTO creado a partir de los datos obtenidos.
	 * @throws IllegalArgumentException Si uno o ambos Optionals no contienen un valor.
	 */
	public ReservaDTO getReservaDTOByOptionalsAndAsiento(Optional<Vuelo> optionalVuelo,
	        Optional<Pasajero> optionalPasajero, String asiento) {
	    if (optionalVuelo.isPresent() && optionalPasajero.isPresent()) {
	        Vuelo vueloEncontradoPorId = optionalVuelo.get();
	        Pasajero pasajeroEncontradoId = optionalPasajero.get();

	        // Convertir los objetos Vuelo y Pasajero a sus respectivos DTOs
	        VueloDTO vueloDTOParaReserva = vueloMapper.toVueloDTO(vueloEncontradoPorId);
	        PasajeroDTO pasajeroDTOParaReserva = pasajeroMapper.toPasajeroDTO(pasajeroEncontradoId);

	        // Crear el objeto ReservaDTO con los datos obtenidos
	        return new ReservaDTO(asiento, vueloDTOParaReserva, pasajeroDTOParaReserva);
	    } else {
	    	throw new IllegalArgumentException("No se encontraron datos válidos para crear la ReservaDTO. "
	    			+ "Verifique la disponibilidad de Vuelo y Pasajero.");

	    }
	}


	/**
	 * Crea una reserva a partir de un objeto ReservaDTO.
	 * 
	 * @param reservaDTO El objeto ReservaDTO que contiene los datos de la reserva.
	 * @return El objeto ReservaDTO correspondiente a la reserva creada.
	 */
	private ReservaDTO createReservaByReservaDTO(ReservaDTO reservaDTO) {
	    Reserva reservaParaIntroducir = reservaMapper.toReserva(reservaDTO);
	    Reserva reservaIntroducidaEnBaseDeDatos = save(reservaParaIntroducir);
	    return reservaMapper.toReservaDTO(reservaIntroducidaEnBaseDeDatos);
	}

	
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

	

	// MÉTODOS POR IMPLEMENTAR EN UN FÚTURO:

}
