package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.ChefAficionadoEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.repositories.ChefAficionadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class ChefAficionadoService {

    @Autowired
    private ChefAficionadoRepository chefAficionadoRepository;

    public ChefAficionadoEntity crearChefAficionado(ChefAficionadoEntity chef)
            throws IllegalOperationException {

        if (chef == null)
            throw new IllegalOperationException("Datos del chef no pueden ser nulos.");

        if (chef.getExperiencia() == null || chef.getExperiencia().trim().isEmpty())
            throw new IllegalOperationException("La experiencia es obligatoria.");

        if (chef.getMejorPlatillo() == null || chef.getMejorPlatillo().trim().isEmpty())
            throw new IllegalOperationException("El mejor platillo es obligatorio.");

        chef.setExperiencia(chef.getExperiencia().trim());
        chef.setMejorPlatillo(chef.getMejorPlatillo().trim());

        return chefAficionadoRepository.save(chef);
    }

    public ChefAficionadoEntity crearChef(ChefAficionadoEntity chef) throws IllegalOperationException {
        return crearChefAficionado(chef);
    }

    public ChefAficionadoEntity actualizarChef(Object id, ChefAficionadoEntity nuevosDatos)
            throws EntityNotFoundException, IllegalOperationException {
        return actualizarChefAficionado(id, nuevosDatos);
    }

    public void eliminarChef(Long id) throws EntityNotFoundException {
        Optional<ChefAficionadoEntity> existente = chefAficionadoRepository.findById(id);
        if (existente.isEmpty())
            throw new EntityNotFoundException("El chef aficionado no existe.");
        chefAficionadoRepository.delete(existente.get());
    }

    public ChefAficionadoEntity actualizarChefAficionado(Object id, ChefAficionadoEntity nuevosDatos)
            throws EntityNotFoundException, IllegalOperationException {

        Optional<ChefAficionadoEntity> existente = chefAficionadoRepository.findById((Long) id);
        if (existente.isEmpty())
            throw new EntityNotFoundException("El chef aficionado no existe.");

        ChefAficionadoEntity chefExistente = existente.get();

        if (nuevosDatos.getExperiencia() != null && !nuevosDatos.getExperiencia().trim().isEmpty())
            chefExistente.setExperiencia(nuevosDatos.getExperiencia().trim());

        if (nuevosDatos.getMejorPlatillo() != null && !nuevosDatos.getMejorPlatillo().trim().isEmpty())
            chefExistente.setMejorPlatillo(nuevosDatos.getMejorPlatillo().trim());

        return chefAficionadoRepository.save(chefExistente);
    }
}
