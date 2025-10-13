package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.UtensiliosEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.repositories.UtensiliosRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class UtensiliosService {

    @Autowired
    private UtensiliosRepository utensiliosRepository;

    public UtensiliosEntity crearUtensilio(UtensiliosEntity utensilio) throws IllegalOperationException {
        if (utensilio == null)
            throw new IllegalOperationException("Datos del utensilio no pueden ser nulos.");

        if (utensilio.getNombre() == null || utensilio.getNombre().trim().isEmpty())
            throw new IllegalOperationException("El nombre del utensilio es obligatorio.");

        if (utensilio.getMarca() == null || utensilio.getMarca().trim().isEmpty())
            throw new IllegalOperationException("La marca del utensilio es obligatoria.");

        if (utensiliosRepository.existsByNombreAndMarca(
                utensilio.getNombre().trim(),
                utensilio.getMarca().trim()))
            throw new IllegalOperationException("Ya existe un utensilio con ese nombre y marca.");

        utensilio.setNombre(utensilio.getNombre().trim());
        utensilio.setMarca(utensilio.getMarca().trim());

        return utensiliosRepository.save(utensilio);
    }

    public UtensiliosEntity actualizarUtensilio(Long id, UtensiliosEntity nuevosDatos)
            throws EntityNotFoundException, IllegalOperationException {

        Optional<UtensiliosEntity> existente = utensiliosRepository.findById(id);
        if (existente.isEmpty())
            throw new EntityNotFoundException("El utensilio no existe.");

        if (nuevosDatos.getNombre() == null || nuevosDatos.getNombre().trim().isEmpty())
            throw new IllegalOperationException("El nombre del utensilio no puede estar vacío.");

        if (nuevosDatos.getMarca() == null || nuevosDatos.getMarca().trim().isEmpty())
            throw new IllegalOperationException("La marca del utensilio no puede estar vacía.");

        UtensiliosEntity utensilio = existente.get();
        utensilio.setNombre(nuevosDatos.getNombre().trim());
        utensilio.setMarca(nuevosDatos.getMarca().trim());

        return utensiliosRepository.save(utensilio);
    }
}
