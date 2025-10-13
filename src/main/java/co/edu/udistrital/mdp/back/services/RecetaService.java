package co.edu.udistrital.mdp.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.entities.RecetaEntity;
import co.edu.udistrital.mdp.back.repositories.PreparacionRepository;
import co.edu.udistrital.mdp.back.repositories.RecetaRepository;
import jakarta.persistence.EntityNotFoundException;
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
     * 
     * @param receta Objeto con los datos básicos.
     * @param preparaciones Lista de preparaciones que se van a asociar.
     * @return Receta creada con sus preparaciones asociadas.
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
     * 
     * @param recetaId ID de la receta a modificar.
     * @param nuevosDatos Datos actualizados de la receta.
     * @param idsPreparaciones IDs de preparaciones nuevas que reemplazarán las anteriores.
     * @return Receta actualizada.
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
     * 
     * @param recetaId ID de la receta a eliminar.
     */
    @Transactional
    public void eliminarReceta(Long recetaId) {
        RecetaEntity receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con id: " + recetaId));

        // Romper relaciones antes de borrar
        receta.getPreparaciones().clear();
        recetaRepository.delete(receta);
    }

    /**
     * Consultar una receta por su ID.
     * 
     * @param recetaId ID de la receta.
     * @return Receta encontrada.
     */
    @Transactional
    public RecetaEntity obtenerRecetaPorId(Long recetaId) {
        return recetaRepository.findById(recetaId)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con id: " + recetaId));
    }

    /**
     * Consultar todas las recetas.
     * 
     * @return Lista de recetas.
     */
    @Transactional
    public List<RecetaEntity> obtenerTodasLasRecetas() {
        return recetaRepository.findAll();
    }
}
