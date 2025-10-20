package co.edu.udistrital.mdp.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.IngredienteEntity;
import co.edu.udistrital.mdp.back.repositories.IngredienteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    /**
     * Crea un nuevo ingrediente en la base de datos.
     * No puede haber dos ingredientes con el mismo nombre.
     * Debe pertenecer al menos a  la categoria (proteína, grasa o carbohidrato)
     */
    @Transactional
    public IngredienteEntity crearIngrediente(IngredienteEntity ingrediente) {

        List<IngredienteEntity> existentes = ingredienteRepository.findAll();
        for (IngredienteEntity i : existentes) {
            if (i.getNombre().equalsIgnoreCase(ingrediente.getNombre())) {
                throw new EntityExistsException(
                    "El ingrediente '" + ingrediente.getNombre() + "' ya existe en la base de datos."
                );
            }
        }

        // Validar que tenga al menos un macronutriente
        if (!(Boolean.TRUE.equals(ingrediente.getEsProteina())
           || Boolean.TRUE.equals(ingrediente.getEsGrasa())
           || Boolean.TRUE.equals(ingrediente.getEsCarbohidrato()))) {
            throw new EntityNotFoundException(
                "El ingrediente '" + ingrediente.getNombre() + "' debe pertenecer al menos a un macronutriente."
            );
        }

        log.info("Creando ingrediente: {}", ingrediente.getNombre());
        return ingredienteRepository.save(ingrediente);
    }

    /**
     * Modifica un ingrediente existente.
     * No puede cambiar su nombre a uno que ya esté en uso.
     * Debe seguir teniendo al menos un macronutriente
     */
    @Transactional
    public IngredienteEntity modificarIngrediente(Long id, String nuevoNombre,
                                                  boolean proteina, boolean grasa, boolean carbohidrato) {

        IngredienteEntity ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado con id: " + id));

        // Validar nombre repetido
        List<IngredienteEntity> existentes = ingredienteRepository.findAll();
        for (IngredienteEntity existente : existentes) {
            if (!existente.getId().equals(id) && existente.getNombre().equalsIgnoreCase(nuevoNombre)) {
                throw new IllegalStateException("Ya existe otro ingrediente con el nombre '" + nuevoNombre + "'");
            }
        }

        // Validar macronutrientes
        if (!(proteina || grasa || carbohidrato)) {
            throw new IllegalStateException("El ingrediente debe pertenecer al menos a un macronutriente.");
        }

        ingrediente.setNombre(nuevoNombre);
        ingrediente.setEsProteina(proteina);
        ingrediente.setEsGrasa(grasa);
        ingrediente.setEsCarbohidrato(carbohidrato);

        log.info("Modificando ingrediente con id {}: nuevo nombre '{}'", id, nuevoNombre);
        return ingredienteRepository.save(ingrediente);
    }

    //consulta un ingrediente por nombre
    @Transactional
    public IngredienteEntity consultarPorNombre(String nombre) {
        List<IngredienteEntity> ingredientes = ingredienteRepository.findByNombre(nombre);
        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new EntityNotFoundException("No existe un ingrediente con nombre: " + nombre);
        }
        return ingredientes.get(0);
    }

    // Consulta todos los ingredientes existentes.
    @Transactional
    public List<IngredienteEntity> listarTodos() {
        return ingredienteRepository.findAll();
    }
}
