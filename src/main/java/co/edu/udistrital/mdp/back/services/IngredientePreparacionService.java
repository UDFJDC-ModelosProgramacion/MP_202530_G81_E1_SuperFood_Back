package co.edu.udistrital.mdp.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.IngredienteEntity;
import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.repositories.IngredienteRepository;
import co.edu.udistrital.mdp.back.repositories.PreparacionRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service

public class IngredientePreparacionService{
    //creación de inyección de dependencias

    @Autowired
    private IngredienteRepository ingredienteRepository;
    @Autowired
    private PreparacionRepository preparacionRepository;

    /**
	 * Agregar un ingrediente a la preparacion
	 *
	 * @param ingredienteId      El id ingrediente a guardar
	 * @param preparacionId El id de la preparacion en la cual se va a guardar el ingrediente.
	 * @return El ingrediente creado.
	 * @throws EntityNotFoundException 
	 */

    @Transactional
    public PreparacionEntity addIngredienteToPreparacion(Long preparacionId, Long ingredienteId) throws EntityNotFoundException {
        PreparacionEntity preparacion = preparacionRepository.findById(preparacionId)
            .orElseThrow(() -> new EntityNotFoundException("Preparación no encontrada"));

        IngredienteEntity ingrediente = ingredienteRepository.findById(ingredienteId)
            .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado"));

        // Agregar ingrediente a la preparación
        preparacion.getIngredientes().add(ingrediente);

        return preparacionRepository.save(preparacion);
    }

    /**
     * Crea un nuevo ingrediente en la base de datos.
     * Si ya existe un ingrediente con el mismo nombre, devuelve el existente.
     * Un ingrediente puede ser parte de proteina, grasa y carbohidrato a la vez
     * un ingrediente debe ser parte al menos de una categoria, proteina, grasa, carbohidrato.
     * 
     * 
     * @param nombre Nombre del ingrediente
     * @return Ingrediente guardado o existente
     * @throws EntityExistException el ingrediente ya existe en la base.
     * @throws EntityNotFoundException el ingrediente no se le asigno ningun macronutriente
     * 
     */
    public IngredienteEntity addIngrediente(IngredienteEntity ingrediente) {
        
        //Validar el nombre para consultar si ya existe.

        List<IngredienteEntity> ingredientes = ingredienteRepository.findAll();
        for (IngredienteEntity i : ingredientes) {
            if (i.getNombre().equalsIgnoreCase(ingrediente.getNombre())) {
                throw new EntityExistsException(
                    "El ingrediente '" + ingrediente.getNombre() + "' ya existe en la base de datos."
                );
            }
        }

        //Validar que pertenezca al menos a un macronutriente
        if (!(Boolean.TRUE.equals(ingrediente.getEsProteina()) ||
             Boolean.TRUE.equals(ingrediente.getEsGrasa()) ||
            Boolean.TRUE.equals(ingrediente.getEsCarbohidrato()))) {

            throw new EntityNotFoundException(
            "El ingrediente '" + ingrediente.getNombre() + "' debe pertenecer al menos a un macronutriente."
        );
}

        // Guardarlo si pasa todos los filtros.
        return ingredienteRepository.save(ingrediente);
    }



}