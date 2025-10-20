package co.edu.udistrital.mdp.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.entities.RecetaEntity;
import co.edu.udistrital.mdp.back.repositories.PreparacionRepository;
import co.edu.udistrital.mdp.back.repositories.RecetaRepository;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private PreparacionRepository preparacionRepository;

    /**
     * Crear una nueva receta.
     * 
     * Al crear una receta se le asocian las preparaciones.
     * La receta se crea con nombre, tiempo de preparación, porciones y descripción.
     */
    @Transactional
    public RecetaEntity crearReceta(RecetaEntity receta, List<Long> idsPreparaciones) {
        if (idsPreparaciones != null && !idsPreparaciones.isEmpty()) {
            List<PreparacionEntity> preparaciones = preparacionRepository.findAllById(idsPreparaciones);
            receta.setPreparaciones(preparaciones);
        }

        return recetaRepository.save(receta);
    }

    /**
     * Actualizar una receta existente.
     */
    @Transactional
    public RecetaEntity actualizarReceta(Long recetaId, RecetaEntity nuevosDatos, List<Long> idsPreparaciones) {
        RecetaEntity receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con id: " + recetaId));

        receta.setNombre(nuevosDatos.getNombre());
        receta.setDescripcion(nuevosDatos.getDescripcion());
        receta.setPorciones(nuevosDatos.getPorciones());
        receta.setTiempoPreparacion(nuevosDatos.getTiempoPreparacion());

        if (idsPreparaciones != null && !idsPreparaciones.isEmpty()) {
            List<PreparacionEntity> nuevasPreparaciones = preparacionRepository.findAllById(idsPreparaciones);
            receta.setPreparaciones(nuevasPreparaciones);
        }

        return recetaRepository.save(receta);
    }

    /**
     * Eliminar una receta.
     * 
     * Al eliminar una receta se eliminan sus relaciones con preparaciones.
     */
    @Transactional
    public void eliminarReceta(Long recetaId) {
        RecetaEntity receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con id: " + recetaId));

        // Romper relaciones antes de borrar
        if (receta.getPreparaciones() != null) {
            receta.setPreparaciones(null);
        }
        recetaRepository.delete(receta);
    }

    /**
        Consultar una receta por su ID
     */
    @Transactional
    public RecetaEntity obtenerRecetaPorId(Long recetaId) {
        return recetaRepository.findById(recetaId)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con id: " + recetaId));
    }

    /**
     * Consultar todas las recetas.
     */
    @Transactional
    public List<RecetaEntity> obtenerTodasLasRecetas() {
        return recetaRepository.findAll();
    }
}
