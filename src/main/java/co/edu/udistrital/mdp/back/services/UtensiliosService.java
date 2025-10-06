package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.UtensiliosEntity;
import co.edu.udistrital.mdp.back.repositories.UtensiliosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UtensiliosService {

    @Autowired
    private UtensiliosRepository utensiliosRepository;

    // CREATE
    public UtensiliosEntity crearUtensilio(UtensiliosEntity utensilio) {
        if (utensilio.getNombre() == null || utensilio.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del utensilio es obligatorio.");
        }
        if (utensilio.getMarca() == null || utensilio.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca del utensilio es obligatoria.");
        }
        if (utensiliosRepository.existsByNombreAndMarca(
                utensilio.getNombre().trim(),
                utensilio.getMarca().trim())) {
            throw new IllegalStateException("Ya existe un utensilio con ese nombre y marca.");
        }
        return utensiliosRepository.save(utensilio);
    }

    // UPDATE
    public UtensiliosEntity actualizarUtensilio(Long id, UtensiliosEntity nuevosDatos) {
        Optional<UtensiliosEntity> existente = utensiliosRepository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("El utensilio no existe.");
        }

        if (nuevosDatos.getNombre() == null || nuevosDatos.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del utensilio no puede estar vacío.");
        }
        if (nuevosDatos.getMarca() == null || nuevosDatos.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca del utensilio no puede estar vacía.");
        }

        existente.get().setNombre(nuevosDatos.getNombre().trim());
        existente.get().setMarca(nuevosDatos.getMarca().trim());
        return utensiliosRepository.save(existente.get());
    }
}

