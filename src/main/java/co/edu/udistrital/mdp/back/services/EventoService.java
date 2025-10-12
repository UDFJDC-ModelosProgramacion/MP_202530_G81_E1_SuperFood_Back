package co.edu.udistrital.mdp.back.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.EventoEntity;
import co.edu.udistrital.mdp.back.repositories.EventoRepository;

@Service
public class EventoService {
    
    @Autowired
    private EventoRepository eventoRepository;

    // crear evento
    public EventoEntity crearEvento(EventoEntity evento) {
        if (eventoRepository.existsByNombreAndFecha(evento.getNombre(), evento.getFecha())) {
            throw new IllegalStateException("Ya existe un evento con ese nombre y fecha.");
        }
        if (evento.getFecha().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha del evento debe ser posterior a la fecha actual.");
        }
        if (evento.getNombre().isEmpty() || evento.getUbicacion().isEmpty() || evento.getFecha() == null || evento.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("El nombre, la ubicación, la fecha y la descripción del evento son obligatorios.");
        }
        return eventoRepository.save(evento);
    }

    // actualizar evento
    public EventoEntity actualizarEvento(Long id, EventoEntity nuevosDatos) {
        if (!eventoRepository.existsById(id)) {
            throw new IllegalArgumentException("El evento no existe.");
        }
        EventoEntity existente = eventoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("El evento no existe."));
        if (nuevosDatos.getNombre() != null) {
            existente.setNombre(nuevosDatos.getNombre());
        }
        if (nuevosDatos.getFecha() != null) {
            existente.setFecha(nuevosDatos.getFecha());
        }
        if (nuevosDatos.getDescripcion() != null) {
            existente.setDescripcion(nuevosDatos.getDescripcion());
        }
        if (nuevosDatos.getUbicacion() != null) {
            existente.setUbicacion(nuevosDatos.getUbicacion());
        }
        return eventoRepository.save(existente);
    }

    // eliminar evento
    public void eliminarEvento(Long id) {
        if (eventoRepository.hasUsuariosInscritos(id)) {
            throw new IllegalStateException("No se puede eliminar el evento porque tiene usuarios inscritos.");
        }
        if (!eventoRepository.existsById(id)) {
            throw new IllegalArgumentException("El evento no existe.");
        }
        eventoRepository.deleteById(id);
    }
    
    // ver un evento
    public EventoEntity verEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new IllegalArgumentException("El evento no existe.");
        }
        return eventoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("El evento no existe."));
    }

    // buscar evento por nombre y fecha
    public EventoEntity buscarPorNombreYFecha(String nombre, java.time.LocalDateTime fecha) {
        return eventoRepository.findByNombreAndFecha(nombre, fecha)
            .orElseThrow(() -> new IllegalArgumentException("El evento no existe."));
    }

    // ver todos los eventos
    public java.util.List<EventoEntity> verTodosLosEventos() {
        return eventoRepository.findAll();
    }
}
