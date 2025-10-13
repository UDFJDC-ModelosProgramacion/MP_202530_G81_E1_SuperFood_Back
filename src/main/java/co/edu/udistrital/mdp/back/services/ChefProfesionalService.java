package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.ChefProfesionalEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.repositories.ChefProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ChefProfesionalService {

    @Autowired
    private ChefProfesionalRepository chefProfesionalRepository;

    public ChefProfesionalEntity crearChefProfesional(ChefProfesionalEntity chef)
            throws IllegalOperationException {

        if (chef == null)
            throw new IllegalOperationException("Datos del chef no pueden ser nulos.");

        if (chef.getEspecialidad() == null || chef.getEspecialidad().trim().isEmpty())
            throw new IllegalOperationException("Debe indicar su especialidad.");

        chef.setEspecialidad(chef.getEspecialidad().trim());
        return chefProfesionalRepository.save(chef);
    }

    public ChefProfesionalEntity actualizarChefProfesional(Long id, ChefProfesionalEntity nuevosDatos)
            throws EntityNotFoundException, IllegalOperationException {

        Optional<ChefProfesionalEntity> existente = chefProfesionalRepository.findById(id);
        if (existente.isEmpty())
            throw new EntityNotFoundException("El chef profesional no existe.");

        ChefProfesionalEntity chefExistente = existente.get();

        if (chefExistente.isVerificado() != nuevosDatos.isVerificado())
            throw new IllegalOperationException("Solo un administrador puede cambiar el estado de verificación.");

        if (nuevosDatos.getEspecialidad() == null || nuevosDatos.getEspecialidad().trim().isEmpty())
            throw new IllegalOperationException("Debe indicar una especialidad válida.");

        chefExistente.setEspecialidad(nuevosDatos.getEspecialidad().trim());
        return chefProfesionalRepository.save(chefExistente);
    }
}
