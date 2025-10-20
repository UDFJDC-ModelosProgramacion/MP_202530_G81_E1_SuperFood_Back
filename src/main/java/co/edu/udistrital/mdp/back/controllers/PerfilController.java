package co.edu.udistrital.mdp.back.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.back.dto.PerfilDTO;
import co.edu.udistrital.mdp.back.dto.PerfilDetailDTO;
import co.edu.udistrital.mdp.back.entities.PerfilEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.PerfilService;

/**
 * Clase que implementa el recurso "perfiles".
 */
@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Busca el perfil con el id asociado recibido en la URL.
     *
     * @param id - El ID del perfil que se busca
     * @return JSON {@link PerfilDetailDTO} - El perfil encontrado con información detallada
     */
    @GetMapping(value = "/{id}")
    public PerfilDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        PerfilEntity perfilEntity = perfilService.verPerfil(id);
        return modelMapper.map(perfilEntity, PerfilDetailDTO.class);
    }

    /**
     * Crea un nuevo perfil con la información que se recibe en el cuerpo de
     * la petición y lo almacena en la base de datos.
     *
     * @param perfil {@link PerfilDTO} - EL perfil que se desea guardar.
     * @return JSON {@link PerfilDTO} - El perfil guardado.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PerfilDetailDTO create(@RequestBody PerfilDTO perfil) throws IllegalOperationException {
        PerfilEntity perfilEntity = perfilService.crearPerfil(modelMapper.map(perfil, PerfilEntity.class));
        return modelMapper.map(perfilEntity, PerfilDetailDTO.class);
    }

    /**
     * Actualiza el perfil con el id recibido en la URL con la información que
     * se recibe en el cuerpo de la petición.
     *
     * @param id     {@link Long} - El ID del perfil que se desea actualizar.
     * @param perfil {@link PerfilDTO} - El perfil que se desea guardar.
     * @return JSON {@link PerfilDetailDTO} - El perfil actualizado.
     */
    @PutMapping(value = "/{id}")
    public PerfilDetailDTO update(@PathVariable("id") Long id, @RequestBody PerfilDTO perfil)
            throws EntityNotFoundException, IllegalOperationException {
        PerfilEntity perfilEntity = perfilService.actualizarPerfil(id, modelMapper.map(perfil, PerfilEntity.class));
        return modelMapper.map(perfilEntity, PerfilDetailDTO.class);
    }

    /**
     * Borra el perfil con el id asociado recibido en la URL.
     *
     * @param id - El ID del perfil que se desea borrar
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        perfilService.eliminarPerfil(id);
    }
}
