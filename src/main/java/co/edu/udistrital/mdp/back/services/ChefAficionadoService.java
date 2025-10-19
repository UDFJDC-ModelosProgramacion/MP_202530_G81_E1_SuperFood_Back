package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.ChefAficionadoEntity;
import co.edu.udistrital.mdp.back.repositories.ChefAficionadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChefAficionadoService {

    @Autowired
    private ChefAficionadoRepository chefAficionadoRepository;

    // CREATE
    public ChefAficionadoEntity crearChefAficionado(ChefAficionadoEntity chef) {
        if (chef.getExperiencia() == null || chef.getExperiencia().trim().isEmpty()) {
            throw new IllegalArgumentException("La experiencia es obligatoria.");
        }

        if (chef.getMejorPlatillo() == null || chef.getMejorPlatillo().trim().isEmpty()) {
            throw new IllegalArgumentException("El mejor platillo es obligatorio.");
        }

        return chefAficionadoRepository.save(chef);
    }

    // UPDATE
    public ChefAficionadoEntity actualizarChefAficionado(Long id, ChefAficionadoEntity nuevosDatos) {
        Optional<ChefAficionadoEntity> existente = chefAficionadoRepository.findById(id);

        if (existente.isEmpty()) {
            throw new IllegalArgumentException("El chef aficionado no existe.");
        }

        if (!id.equals(nuevosDatos.getId())) {
            throw new SecurityException("Solo puedes actualizar tu propia información.");
        }

        ChefAficionadoEntity chefExistente = existente.get();

        if (nuevosDatos.getExperiencia() != null && !nuevosDatos.getExperiencia().trim().isEmpty()) {
            chefExistente.setExperiencia(nuevosDatos.getExperiencia());
        }

        if (nuevosDatos.getMejorPlatillo() != null && !nuevosDatos.getMejorPlatillo().trim().isEmpty()) {
            chefExistente.setMejorPlatillo(nuevosDatos.getMejorPlatillo());
        }

        return chefAficionadoRepository.save(chefExistente);
    }
}

