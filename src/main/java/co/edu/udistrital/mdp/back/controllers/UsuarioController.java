
package co.edu.udistrital.mdp.back.controllers;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.back.dto.UsuarioDTO;
import co.edu.udistrital.mdp.back.dto.UsuarioDetailDTO;
import co.edu.udistrital.mdp.back.entities.UsuarioEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.UsuarioService;

/**
 * Clase que implementa el recurso "usuarios".
 *
 * @author Universidad Distrital
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Busca y devuelve todos los usuarios que existen en la aplicacion.
	 *
	 * @return JSONArray {@link UsuarioDetailDTO} - Los usuarios encontrados en la
	 *         aplicación. Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<UsuarioDetailDTO> findAll() {
		List<UsuarioEntity> usuarios = usuarioService.verTodosLosUsuarios();
		return modelMapper.map(usuarios, new TypeToken<List<UsuarioDetailDTO>>() {
		}.getType());
	}

	/**
	 * Busca el usuario con el id asociado recibido en la URL y lo devuelve.
	 *
	 * @param id Identificador del usuario que se esta buscando. Este debe ser una
	 *           cadena de dígitos.
	 * @return JSON {@link UsuarioDetailDTO} - El usuario buscado
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UsuarioDetailDTO findOne(@PathVariable Long id) throws EntityNotFoundException {
		Optional<UsuarioEntity> usuarioEntity = usuarioService.buscarPorId(id);
		if (!usuarioEntity.isPresent()) {
			throw new EntityNotFoundException("El usuario no existe.");
		}
		return modelMapper.map(usuarioEntity.get(), UsuarioDetailDTO.class);
	}

	/**
	 * Busca usuarios por email.
	 *
	 * @param email Email del usuario que se esta buscando.
	 * @return JSON {@link UsuarioDetailDTO} - El usuario buscado
	 */
	@GetMapping(value = "/email")
	@ResponseStatus(code = HttpStatus.OK)
	public UsuarioDetailDTO findByEmail(@RequestParam String email) throws EntityNotFoundException {
		Optional<UsuarioEntity> usuarioEntity = usuarioService.buscarPorEmail(email);
		if (!usuarioEntity.isPresent()) {
			throw new EntityNotFoundException("No existe un usuario con ese email.");
		}
		return modelMapper.map(usuarioEntity.get(), UsuarioDetailDTO.class);
	}

	/**
	 * Busca usuarios por nombre.
	 *
	 * @param nombre Nombre del usuario que se esta buscando.
	 * @return JSONArray {@link UsuarioDetailDTO} - Los usuarios encontrados
	 */
	@GetMapping(value = "/nombre")
	@ResponseStatus(code = HttpStatus.OK)
	public List<UsuarioDetailDTO> findByNombre(@RequestParam String nombre) {
		List<UsuarioEntity> usuarios = usuarioService.buscarPorNombre(nombre);
		return modelMapper.map(usuarios, new TypeToken<List<UsuarioDetailDTO>>() {
		}.getType());
	}

	/**
	 * Crea un nuevo usuario con la informacion que se recibe en el cuerpo de la
	 * petición y se regresa un objeto identico con un id auto-generado por la base
	 * de datos.
	 *
	 * @param usuarioDTO {@link UsuarioDTO} - EL usuario que se desea guardar.
	 * @return JSON {@link UsuarioDTO} - El usuario guardado con el atributo id
	 *         autogenerado.
	 * @throws IllegalOperationException 
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UsuarioDTO create(@RequestBody UsuarioDTO usuarioDTO) throws IllegalOperationException {
		try {
			UsuarioEntity usuarioEntity = usuarioService.crearUsuario(modelMapper.map(usuarioDTO, UsuarioEntity.class));
			return modelMapper.map(usuarioEntity, UsuarioDTO.class);
		} catch (IllegalArgumentException | IllegalStateException e) {
			throw new IllegalOperationException(e.getMessage());
		}
	}

	/**
	 * Actualiza el usuario con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 *
	 * @param id         Identificador del usuario que se desea actualizar. Este debe ser
	 *                   una cadena de dígitos.
	 * @param usuarioDTO {@link UsuarioDTO} El usuario que se desea guardar.
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UsuarioDTO update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO)
			throws EntityNotFoundException, IllegalOperationException {
		try {
			UsuarioEntity usuarioEntity = usuarioService.actualizarUsuario(id, modelMapper.map(usuarioDTO, UsuarioEntity.class));
			return modelMapper.map(usuarioEntity, UsuarioDTO.class);
		} catch (IllegalArgumentException e) {
			throw new EntityNotFoundException(e.getMessage());
		} catch (IllegalStateException e) {
			throw new IllegalOperationException(e.getMessage());
		}
	}

	/**
	 * Borra el usuario con el id asociado recibido en la URL.
	 *
	 * @param id Identificador del usuario que se desea borrar. Este debe ser una
	 *           cadena de dígitos.
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
		try {
			usuarioService.eliminarUsuario(id);
		} catch (IllegalArgumentException e) {
			throw new EntityNotFoundException(e.getMessage());
		}
	}
}
