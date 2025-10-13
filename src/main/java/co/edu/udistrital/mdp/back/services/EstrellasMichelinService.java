package co.edu.udistrital.mdp.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.EstrellasMichelinEntity;
import co.edu.udistrital.mdp.back.entities.RestauranteEntity;
import co.edu.udistrital.mdp.back.repositories.EstrellasMichelinRepository;
import co.edu.udistrital.mdp.back.repositories.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstrellasMichelinService {

    @Autowired private EstrellasMichelinRepository estrellasRepo;
    @Autowired private RestauranteRepository restauranteRepo;

    //Crear
    @Transactional
    public EstrellasMichelinEntity crear(String descripcion, Integer cantidad) {
        validarCantidad(cantidad);
        EstrellasMichelinEntity e = new EstrellasMichelinEntity();
        e.setDescripcion(descripcion);
        e.setCantidadEstrellas(cantidad);
        log.info("Creando EstrellasMichelin: {} estrellas", cantidad);
        return estrellasRepo.save(e);
    }

    //Actualizar
    @Transactional
    public EstrellasMichelinEntity actualizar(Long id, String descripcion, Integer cantidad) {
        EstrellasMichelinEntity e = estrellasRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("EstrellasMichelin no existe (id=" + id + ")."));

        if (descripcion != null) e.setDescripcion(descripcion);
        if (cantidad != null) {
            validarCantidad(cantidad);
            e.setCantidadEstrellas(cantidad);
        }

        log.info("Actualizando EstrellasMichelin id={} -> {} estrellas", id, e.getCantidadEstrellas());
        return estrellasRepo.save(e);
    }

    //Eliminar
    @Transactional
    public void eliminar(Long estrellasId) {
        EstrellasMichelinEntity e = estrellasRepo.findById(estrellasId)
            .orElseThrow(() -> new EntityNotFoundException("EstrellasMichelin no existe (id=" + estrellasId + ")."));

        // Regla: no eliminar si está vinculada a un Restaurante
        if (restauranteRepo.existsByEstrellasMichelin_Id(estrellasId)) {
            throw new IllegalStateException(
                "No se puede eliminar: está vinculada a un Restaurante. " +
                "Primero desvincule las Estrellas desde el Restaurante."
            );
        }

        estrellasRepo.delete(e);
        log.info("EstrellasMichelin {} eliminada", estrellasId);
    }

    //Desvincular desde estrellas
    @Transactional
    public void desvincularDeRestaurante(Long estrellasId) {
        RestauranteEntity r = restauranteRepo.findByEstrellasMichelin_Id(estrellasId).orElse(null);
        if (r != null) {
            r.setEstrellasMichelin(null);
            restauranteRepo.save(r);
            log.info("Estrellas {} desvinculadas del Restaurante {}", estrellasId, r.getId());
        }
    }

    //Validación
    private void validarCantidad(Integer cantidad) {
        if (cantidad == null) {
            throw new IllegalStateException("La cantidad de estrellas es obligatoria.");
        }
        if (cantidad < 0 || cantidad > 3) {
            throw new IllegalStateException("La cantidad de estrellas debe estar entre 0 y 3.");
        }
    }
}
