package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.ChefProfesionalEntity;
import co.edu.udistrital.mdp.back.repositories.ChefProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ChefProfesionalService {

    @Autowired
    private ChefProfesionalRepository chefProfesionalRepository;

    // CREATE
    public ChefProfesionalEntity crearChefProfesional(ChefProfesionalEntity chef) {
        if (chef.getEspecialidad() == null || chef.getEspecialidad().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar su especialidad.");
        }
        return chefProfesionalRepository.save(chef);
    }

    // UPDATE
    public ChefProfesionalEntity actualizarChefProfesional(Long id, ChefProfesionalEntity nuevosDatos) {
        Optional<ChefProfesionalEntity> existente = chefProfesionalRepository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("El chef profesional no existe.");
        }

        if (existente.get().isVerificado() != nuevosDatos.isVerificado()) {
            throw new SecurityException("El estado de verificación solo puede cambiarlo un administrador.");
        }

        if (nuevosDatos.getEspecialidad() == null || nuevosDatos.getEspecialidad().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar una especialidad válida.");
        }

        existente.get().setEspecialidad(nuevosDatos.getEspecialidad());
        return chefProfesionalRepository.save(existente.get());
    }
}
