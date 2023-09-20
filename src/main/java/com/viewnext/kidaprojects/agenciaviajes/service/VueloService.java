package com.viewnext.kidaprojects.agenciaviajes.service;

import java.sql.Date;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;
import com.viewnext.kidaprojects.agenciaviajes.dto.VueloDTO;
import com.viewnext.kidaprojects.agenciaviajes.mappers.VueloMapper;
import com.viewnext.kidaprojects.agenciaviajes.model.Vuelo;
import com.viewnext.kidaprojects.agenciaviajes.repository.VueloRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VueloService implements VueloRepository {

	@Autowired
	private VueloRepository vueloRepository;

	private final VueloMapper vueloMapper;

	public VueloService(VueloMapper vueloMapper) {
		this.vueloMapper = vueloMapper;
	}

	// MÉTODOS IMPLEMENTADOS:
	/**
	 * Recupera todos los vuelos de la base de datos.
	 *
	 * @return una lista que contiene todos los vuelos.
	 */
	@Override
	public List<Vuelo> findAll() {
		List<Vuelo> listaVuelos;

		listaVuelos = vueloRepository.findAll();

		return listaVuelos;
	}

	/**
	 * Guarda un vuelo en la base de datos. Puede utilizarse para crear un nuevo
	 * vuelo o actualizar uno existente.
	 *
	 * @param entity el vuelo a guardar.
	 * @return el vuelo guardado.
	 */
	@Override
	public <S extends Vuelo> S save(S entity) {

		vueloRepository.save(entity);

		return entity;
	}

	/**
	 * Busca un vuelo por su identificador (ID).
	 *
	 * @param id el identificador del vuelo.
	 * @return un Optional que contiene el vuelo encontrado, o un Optional vacío si
	 *         no se encuentra el vuelo.
	 */
	@Override
	public Optional<Vuelo> findById(Integer id) {

		return vueloRepository.findById(id);
	}

	/**
	 * Elimina un vuelo de la base de datos por su identificador (ID).
	 *
	 * @param id el identificador del vuelo a eliminar.
	 */
	@Override
	public void deleteById(Integer id) {

		vueloRepository.deleteById(id);
	}

	/**
	 * Elimina un vuelo de la base de datos.
	 *
	 * @param entity el vuelo a eliminar.
	 */
	@Override
	public void delete(Vuelo entity) {

		vueloRepository.delete(entity);

	}

	/**
	 * Filtra una lista de vuelos según el origen, destino y fecha especificados.
	 *
	 * @param ciudadOrigen  La ciudad de origen de los vuelos a filtrar.
	 * @param ciudadDestino La ciudad de destino de los vuelos a filtrar.
	 * @param fecha         La fecha de los vuelos a filtrar.
	 * @param listaVuelos   La lista de vuelos a filtrar.
	 * @return Una lista de vuelos filtrados por origen, destino y fecha.
	 */
	public List<Vuelo> findVuelosByOrigenDestinoFecha(String ciudadOrigen, String ciudadDestino, Date fecha,
			List<Vuelo> listaVuelos) {
		List<Vuelo> listaVuelosFiltradaPorOrigenDestinoFecha;

		listaVuelosFiltradaPorOrigenDestinoFecha = listaVuelos.stream()
				.filter(vuelo -> vuelo.getOrigen().equalsIgnoreCase(ciudadOrigen))
				.filter(vuelo -> vuelo.getDestino().equalsIgnoreCase(ciudadDestino))
				.filter(vuelo -> vuelo.getFecha().equals(fecha))
				.toList();

		return listaVuelosFiltradaPorOrigenDestinoFecha;
	}

	/**
	 * Obtiene todos los vuelos disponibles.
	 *
	 * @return Lista de objetos VueloDTO que representa todos los vuelos.
	 */
	public List<VueloDTO> getAllVuelos() {
		List<Vuelo> listaVuelos;
		List<VueloDTO> listaVuelosDTO;

		listaVuelos = findAll();

		listaVuelosDTO = vueloMapper.toVueloDTOList(listaVuelos);

		return listaVuelosDTO;
	}

	/**
	 * Crea un nuevo vuelo.
	 *
	 * @param vueloDTO Objeto VueloDTO que representa los datos del nuevo vuelo a
	 *                 crear.
	 * @return Objeto VueloDTO que representa el vuelo creado.
	 */
	public VueloDTO createVuelo(VueloDTO vueloDTO) {
		Vuelo vueloParaIntroducir;
		Vuelo vueloIntroducidoEnBaseDeDatos;
		VueloDTO nuevoVueloDTO;

		vueloParaIntroducir = vueloMapper.toVuelo(vueloDTO);

		vueloIntroducidoEnBaseDeDatos = save(vueloParaIntroducir);

		nuevoVueloDTO = vueloMapper.toVueloDTO(vueloIntroducidoEnBaseDeDatos);

		return nuevoVueloDTO;
	}

	/**
	 * Obtiene un vuelo por su ID.
	 *
	 * @param id El ID del vuelo a buscar.
	 * @return Objeto VueloDTO que representa el vuelo encontrado.
	 * @throws EntityNotFoundException Si no se encuentra el vuelo con el ID
	 *                                 especificado.
	 */
	public VueloDTO getVueloById(Integer id) throws EntityNotFoundException {
		Optional<Vuelo> optionalVuelo = findById(id);

		if (optionalVuelo.isPresent()) {
			return vueloMapper.toVueloDTO(optionalVuelo.get());
		} else {
			throw new EntityNotFoundException();
		}
	}
	
	/**
	 * Comprueba si existe un vuelo con el identificador (ID) proporcionado en la base de datos.
	 *
	 * @param id El ID del vuelo que se va a buscar.
	 * @return true si existe un vuelo con el ID proporcionado, false si no se encuentra ningún vuelo con ese ID.
	 */
	@Override
	public boolean existsById(Integer id) {
	    return vueloRepository.existsById(id);
	}

	/**
	 * Elimina un vuelo por su ID.
	 *
	 * @param id El ID del vuelo a eliminar.
	 * @throws EntityNotFoundException Si no se encuentra el vuelo con el ID
	 *                                 especificado.
	 */
	public void deleteVueloById(Integer id) throws EntityNotFoundException {
		if (!existsById(id)) {
			throw new EntityNotFoundException();
		}

		deleteById(id);
	}

	/**
	 * 
	 * Actualiza un vuelo existente por su ID.
	 * 
	 * @param id       El ID del vuelo a actualizar.
	 * @param vueloDTO Objeto VueloDTO con los datos actualizados del vuelo.
	 * @return El objeto VueloDTO actualizado.
	 * @throws EntityNotFoundException Si el vuelo con el ID especificado no existe
	 *                                 en la base de datos.
	 */
	public VueloDTO updateVueloById(Integer id, VueloDTO vueloDTO) throws EntityNotFoundException {
		// Verificar si el vuelo con la ID especificada existe en la base de datos
		Optional<Vuelo> optionalVuelo = findById(id);
		if (optionalVuelo.isEmpty()) {
			throw new EntityNotFoundException();
		}

		// Cuerpo del método en caso de que existe el Pasajero:
		Vuelo vuelo;
		Vuelo vueloActualizado;
		VueloDTO vueloDTOActualizado;

		vuelo = vueloMapper.toVuelo(vueloDTO);
		vuelo.setIdVuelo(id);// Esta línea podría ser útil, por si en un fúturo el DTO no viene con el id del
						// vuelo que queremos actualizar

		// Guardar el vuelo actualizado en la base de datos
		vueloActualizado = save(vuelo);

		// Mapear el vuelo actualizado a un DTO y devolverlo en la respuesta
		vueloDTOActualizado = vueloMapper.toVueloDTO(vueloActualizado);

		return vueloDTOActualizado;
	}
	
	

	/**
	 * 
	 * Busca vuelos por origen, destino y fecha.
	 * @param origen  El origen del vuelo.
	 * @param destino El destino del vuelo.
	 * @param fecha   La fecha del vuelo.
	 * @return La lista de vuelos filtrada por origen, destino y fecha.
	 * @throws EntityNotFoundException Si no se encuentran vuelos con los criterios
	 *                                 especificados.
	 */
	public List<VueloDTO> buscarVuelosPorOrigenDestinoFecha(String origen, String destino, Date fecha)
			throws EntityNotFoundException {
		List<Vuelo> listaVuelosSinFiltrar;
		List<Vuelo> listaVuelosFiltradaPorOrigenDestinoFecha;
		List<VueloDTO> listaVuelosDTOFiltradaPorOrigenDestinoFecha;

		// Obtener todos los vuelos
		listaVuelosSinFiltrar = findAll();

		// Filtrar vuelos por origen, destino y fecha
		listaVuelosFiltradaPorOrigenDestinoFecha = findVuelosByOrigenDestinoFecha(origen, destino, fecha, listaVuelosSinFiltrar);

		// Mapeamos a lista de DTOs
		listaVuelosDTOFiltradaPorOrigenDestinoFecha = vueloMapper
				.toVueloDTOList(listaVuelosFiltradaPorOrigenDestinoFecha);

		// Verificar si se encontraron coincidencias
		if (listaVuelosDTOFiltradaPorOrigenDestinoFecha.isEmpty()) {
			throw new EntityNotFoundException();
		}

		// Devolver la lista de vuelos filtrados
		return listaVuelosDTOFiltradaPorOrigenDestinoFecha;
	}
	
	
	
	// MÉTODOS POR IMPLEMENTAR EN UN FÚTURO:

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Vuelo> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Vuelo> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Vuelo> entities) {
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
	public Vuelo getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vuelo getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vuelo getReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Vuelo> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Vuelo> List<S> findAll(Example<S> example, org.springframework.data.domain.Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Vuelo> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vuelo> findAllById(Iterable<Integer> ids) {
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
	public void deleteAll(Iterable<? extends Vuelo> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vuelo> findAll(org.springframework.data.domain.Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vuelo> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Vuelo> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends Vuelo> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Vuelo> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Vuelo> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Vuelo, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	






}
