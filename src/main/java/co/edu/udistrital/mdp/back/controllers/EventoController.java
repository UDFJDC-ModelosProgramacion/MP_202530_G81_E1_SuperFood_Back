package co.edu.udistrital.mdp.back.controllers;

import java.util.List;

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

import co.edu.udistrital.mdp.back.dto.EventoDTO;
import co.edu.udistrital.mdp.back.dto.EventoDetailDTO;
import co.edu.udistrital.mdp.back.entities.EventoEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.EventoService;

/**
 * Clase que implementa el recurso "eventos".
 */
@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Busca y devuelve todos los eventos que existen en la aplicación.
     *
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en la aplicación.
     */
    @GetMapping
    public List<EventoDetailDTO> findAll() {
        List<EventoEntity> eventos = eventoService.verTodosLosEventos();
        return modelMapper.map(eventos, new TypeToken<List<EventoDetailDTO>>() {}.getType());
    }

    /**
     * Busca el evento con el id asociado recibido en la URL.
     *
     * @param id - El ID del evento que se busca
     * @return JSON {@link EventoDetailDTO} - El evento encontrado con información detallada
     */
    @GetMapping(value = "/{id}")
    public EventoDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        EventoEntity eventoEntity = eventoService.verEvento(id);
        return modelMapper.map(eventoEntity, EventoDetailDTO.class);
    }

    /**
     * Busca eventos por nombre y fecha.
     *
     * @param nombre - El nombre del evento que se busca
     * @param fecha - La fecha del evento que se busca (formato: yyyy-MM-dd'T'HH:mm:ss)
     * @return JSON {@link EventoDetailDTO} - El evento encontrado
     */
    @GetMapping(value = "/buscar")
    public EventoDetailDTO findByNombreAndFecha(
            @RequestParam String nombre, 
            @RequestParam String fecha) throws EntityNotFoundException {
        
        java.time.LocalDateTime fechaLocalDateTime = java.time.LocalDateTime.parse(fecha);
        EventoEntity eventoEntity = eventoService.buscarPorNombreYFecha(nombre, fechaLocalDateTime);
        return modelMapper.map(eventoEntity, EventoDetailDTO.class);
    }

    /**
     * Crea un nuevo evento con la información que se recibe en el cuerpo de
     * la petición y lo almacena en la base de datos.
     *
     * @param evento {@link EventoDTO} - El evento que se desea guardar.
     * @return JSON {@link EventoDetailDTO} - El evento guardado.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EventoDetailDTO create(@RequestBody EventoDTO evento) throws IllegalOperationException {
        EventoEntity eventoEntity = eventoService.crearEvento(modelMapper.map(evento, EventoEntity.class));
        return modelMapper.map(eventoEntity, EventoDetailDTO.class);
    }

    /**
     * Actualiza el evento con el id recibido en la URL con la información que
     * se recibe en el cuerpo de la petición.
     *
     * @param id     {@link Long} - El ID del evento que se desea actualizar.
     * @param evento {@link EventoDTO} - El evento que se desea guardar.
     * @return JSON {@link EventoDetailDTO} - El evento actualizado.
     */
    @PutMapping(value = "/{id}")
    public EventoDetailDTO update(@PathVariable("id") Long id, @RequestBody EventoDTO evento)
            throws EntityNotFoundException, IllegalOperationException {
        EventoEntity eventoEntity = eventoService.actualizarEvento(id, modelMapper.map(evento, EventoEntity.class));
        return modelMapper.map(eventoEntity, EventoDetailDTO.class);
    }

    /**
     * Borra el evento con el id asociado recibido en la URL.
     *
     * @param id - El ID del evento que se desea borrar
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        eventoService.eliminarEvento(id);
    }
}