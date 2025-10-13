package co.edu.udistrital.mdp.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.RestauranteEntity;
import co.edu.udistrital.mdp.back.entities.UbicacionRestauranteEntity;
import co.edu.udistrital.mdp.back.repositories.RestauranteRepository;
import co.edu.udistrital.mdp.back.repositories.UbicacionRestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionRestauranteService {

    @Autowired private UbicacionRestauranteRepository ubicacionRepo;
    @Autowired private RestauranteRepository restauranteRepo;

    //Crear
    @Transactional
    public UbicacionRestauranteEntity crear(String direccion) {
        validarDireccion(direccion);
        UbicacionRestauranteEntity u = new UbicacionRestauranteEntity();
        u.setDireccion(direccion);
        log.info("Creando Ubicación: {}", direccion);
        return ubicacionRepo.save(u);
    }

    //Actualizar
    @Transactional
    public UbicacionRestauranteEntity actualizar(Long id, String direccion) {
        UbicacionRestauranteEntity u = ubicacionRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ubicación no existe (id=" + id + ")."));

        if (direccion != null) {
            validarDireccion(direccion);
            u.setDireccion(direccion);
        }

        log.info("Actualizando Ubicación id={}", id);
        return ubicacionRepo.save(u);
    }

    //Eliminar
    @Transactional
    public void eliminar(Long ubicacionId) {
        UbicacionRestauranteEntity u = ubicacionRepo.findById(ubicacionId)
            .orElseThrow(() -> new EntityNotFoundException("Ubicación no existe (id=" + ubicacionId + ")."));

        // Regla: no eliminar si está vinculada a un Restaurante
        if (restauranteRepo.existsByUbicacion_Id(ubicacionId)) {
            throw new IllegalStateException(
                "No se puede eliminar: está vinculada a un Restaurante. " +
                "Primero desvincule la Ubicación desde el Restaurante."
            );
        }

        ubicacionRepo.delete(u);
        log.info("Ubicación {} eliminada", ubicacionId);
    }

    //Desvincular desde la ubicación
    @Transactional
    public void desvincularDeRestaurante(Long ubicacionId) {
        RestauranteEntity r = restauranteRepo.findByUbicacion_Id(ubicacionId)
            .orElse(null);
        if (r != null) {
            r.setUbicacion(null);
            restauranteRepo.save(r);
            log.info("Ubicación {} desvinculada del Restaurante {}", ubicacionId, r.getId());
        }
    }

    //Validaciones
    private void validarDireccion(String direccion) {
        if (direccion == null || direccion.isBlank()) {
            throw new IllegalStateException("La dirección es obligatoria.");
        }
    }
}
