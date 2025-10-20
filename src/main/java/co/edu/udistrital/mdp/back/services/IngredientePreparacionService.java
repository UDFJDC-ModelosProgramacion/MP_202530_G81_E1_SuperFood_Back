package co.edu.udistrital.mdp.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.back.entities.IngredienteEntity;
import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.entities.IngredientePreparacionEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.repositories.IngredienteRepository;
import co.edu.udistrital.mdp.back.repositories.PreparacionRepository;
import co.edu.udistrital.mdp.back.repositories.IngredientePreparacionRepository;

@Service
public class IngredientePreparacionService {

    @Autowired
    private IngredientePreparacionRepository ingredientePreparacionRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private PreparacionRepository preparacionRepository;

    /**
     * Asociar un ingrediente a una preparación, con su gramaje y porción,
     */
    @Transactional
    public IngredientePreparacionEntity asociarIngrediente(
            Long preparacionId,
            Long ingredienteId,
            Double gramaje,
            String porcion) throws EntityNotFoundException {

        PreparacionEntity preparacion = preparacionRepository.findById(preparacionId)
                .orElseThrow(() -> new EntityNotFoundException("Preparación no encontrada"));

        IngredienteEntity ingrediente = ingredienteRepository.findById(ingredienteId)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado"));

        IngredientePreparacionEntity relacion = new IngredientePreparacionEntity();
        relacion.setPreparacion(preparacion);
        relacion.setIngrediente(ingrediente);
        relacion.setGramaje(gramaje);
        relacion.setPorcion(porcion);

        return ingredientePreparacionRepository.save(relacion);
    }

    /**
     * Quitar un ingrediente de una preparación
     */
    @Transactional
    public void quitarIngrediente(Long preparacionId, Long ingredienteId) throws EntityNotFoundException {
        IngredientePreparacionEntity relacion = ingredientePreparacionRepository
                .findByPreparacionIdAndIngredienteId(preparacionId, ingredienteId)
                .orElseThrow(() -> new EntityNotFoundException("No existe esta relación entre ingrediente y preparación"));

        ingredientePreparacionRepository.delete(relacion);
    }

    /**
     * Consultar todos los ingredientes asociados a una preparación.
     */
    public List<IngredientePreparacionEntity> consultarIngredientes(Long preparacionId) throws EntityNotFoundException {
        PreparacionEntity preparacion = preparacionRepository.findById(preparacionId)
                .orElseThrow(() -> new EntityNotFoundException("Preparación no encontrada"));

        List<IngredientePreparacionEntity> lista = ingredientePreparacionRepository.findByPreparacion(preparacion);

        if (lista.isEmpty()) {
            throw new EntityNotFoundException("La preparación no tiene ingredientes asociados");
        }

        return lista;
    }
}
