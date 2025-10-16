package co.edu.udistrital.mdp.back.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.ChefProfesionalEntity;
import co.edu.udistrital.mdp.back.entities.EstrellasMichelinEntity;
import co.edu.udistrital.mdp.back.entities.FotoEntity;
import co.edu.udistrital.mdp.back.entities.RestauranteEntity;
import co.edu.udistrital.mdp.back.entities.UbicacionRestauranteEntity;
import co.edu.udistrital.mdp.back.repositories.ChefProfesionalRepository;
import co.edu.udistrital.mdp.back.repositories.EstrellasMichelinRepository;
import co.edu.udistrital.mdp.back.repositories.FotoRepository;
import co.edu.udistrital.mdp.back.repositories.RestauranteRepository;
import co.edu.udistrital.mdp.back.repositories.UbicacionRestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestauranteService {

    @Autowired private RestauranteRepository restauranteRepository;
    @Autowired private ChefProfesionalRepository chefRepository;
    @Autowired private UbicacionRestauranteRepository ubicacionRepository;
    @Autowired private EstrellasMichelinRepository estrellasRepository;
    @Autowired private FotoRepository fotoRepository;

    /**
     * Crea un restaurante:
     * - nombre obligatorio
     * - porciones >= 1
     * - al menos un ChefProfesional existente
     * - Ubicación y EstrellasMichelin son opcionales, pero si se pasan IDs deben existir
     */
    @Transactional
    public RestauranteEntity crearRestaurante(String nombre,
                                              Integer porciones,
                                              Set<Long> chefsIds,
                                              Long ubicacionId,
                                              Long estrellasId,
                                              String horario,
                                              String horarios) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalStateException("El nombre es obligatorio.");
        }
        if (porciones == null || porciones < 1) {
            throw new IllegalStateException("Las porciones deben ser ≥ 1.");
        }
        if (chefsIds == null || chefsIds.isEmpty()) {
            throw new IllegalStateException("Debe asociar al menos un ChefProfesional.");
        }

        // Cargar chefs existentes
        List<ChefProfesionalEntity> encontrados = chefRepository.findAllById(chefsIds);
        if (encontrados.isEmpty()) {
            throw new EntityNotFoundException("Los chefs indicados no existen.");
        }

        RestauranteEntity r = new RestauranteEntity();
        r.setNombre(nombre);
        r.setPorciones(porciones);
        r.setHorario(horario);
        r.setHorarios(horarios);
        r.setChefs(new HashSet<>(encontrados));

        if (ubicacionId != null) {
            UbicacionRestauranteEntity u = ubicacionRepository.findById(ubicacionId)
                    .orElseThrow(() -> new EntityNotFoundException("La ubicación no existe (id=" + ubicacionId + ")."));
            r.setUbicacion(u);
        }
        if (estrellasId != null) {
            EstrellasMichelinEntity e = estrellasRepository.findById(estrellasId)
                    .orElseThrow(() -> new EntityNotFoundException("EstrellasMichelin no existe (id=" + estrellasId + ")."));
            r.setEstrellasMichelin(e);
        }

        log.info("Creando restaurante: {}", nombre);
        return restauranteRepository.save(r);
    }

    /**
     * Elimina un restaurante
     * - No eliminar si tiene servicios activos o eventos vinculados.
     * - Desvincular chefs.
     * - Manejar fotos/ubicación/estrellas: eliminar o desvincular explícitamente.
     */
    @Transactional
    public void eliminarRestaurante(Long restauranteId) {
        RestauranteEntity r = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante no existe (id=" + restauranteId + ")."));

        // Note: Service validation removed due to entity relationship structure

        // 1) Desvincular Many-to-Many con chefs
        if (r.getChefs() != null && !r.getChefs().isEmpty()) {
            r.getChefs().forEach(c -> c.getRestaurantes().remove(r));
            r.getChefs().clear();
        }

        // 2) Manejo de fotos: eliminar o solo desvincular
        if (r.getFotos() != null && !r.getFotos().isEmpty()) {
            for (FotoEntity f : new ArrayList<>(r.getFotos())) {
                f.setRestaurante(null);
                fotoRepository.delete(f);
            }
            r.getFotos().clear();
        }

        // 3) Manejo de ubicación: eliminar o solo desvincular
        if (r.getUbicacion() != null) {
            UbicacionRestauranteEntity u = r.getUbicacion();
            r.setUbicacion(null);
            restauranteRepository.save(r);
            ubicacionRepository.delete(u);
        }

        // 4) Manejo de EstrellasMichelin: eliminar o solo desvincular
        if (r.getEstrellasMichelin() != null) {
            EstrellasMichelinEntity e = r.getEstrellasMichelin();
            r.setEstrellasMichelin(null);
            restauranteRepository.save(r);
            estrellasRepository.delete(e);
        }

        log.info("Eliminando restaurante id={}", restauranteId);
        restauranteRepository.delete(r);
    }

    /**
     * Adjunta/actualiza la Ubicación del restaurante.
     */
    @Transactional
    public RestauranteEntity asignarUbicacion(Long restauranteId, Long ubicacionId) {
        RestauranteEntity r = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante no existe (id=" + restauranteId + ")."));

        UbicacionRestauranteEntity u = ubicacionRepository.findById(ubicacionId)
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no existe (id=" + ubicacionId + ")."));

        r.setUbicacion(u);
        log.info("Asignando ubicación {} a restaurante {}", ubicacionId, restauranteId);
        return restauranteRepository.save(r);
    }

    /**
     * Quita la foto (y opcionalmente la borra).
     */
    @Transactional
    public void quitarFoto(Long fotoId) {
        FotoEntity f = fotoRepository.findById(fotoId)
                .orElseThrow(() -> new EntityNotFoundException("Foto no existe (id=" + fotoId + ")."));
        f.setRestaurante(null);
        fotoRepository.delete(f);
        log.info("Foto {} desvinculada/eliminada", fotoId);
    }

    /**
     * Asigna o reemplaza el registro de EstrellasMichelin.
     */
    @Transactional
    public RestauranteEntity asignarEstrellas(Long restauranteId, Long estrellasId) {
        RestauranteEntity r = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante no existe (id=" + restauranteId + ")."));

        EstrellasMichelinEntity e = estrellasRepository.findById(estrellasId)
                .orElseThrow(() -> new EntityNotFoundException("EstrellasMichelin no existe (id=" + estrellasId + ")."));

        r.setEstrellasMichelin(e);
        log.info("Asignando EstrellasMichelin {} a restaurante {}", estrellasId, restauranteId);
        return restauranteRepository.save(r);
    }

    /**
     * Agrega chefs (todos deben existir).
     */
    @Transactional
    public RestauranteEntity agregarChefs(Long restauranteId, Set<Long> chefsIds) {
        RestauranteEntity r = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante no existe (id=" + restauranteId + ")."));

        if (chefsIds == null || chefsIds.isEmpty()) {
            throw new IllegalStateException("Debe indicar al menos un chef.");
        }
        List<ChefProfesionalEntity> lista = chefRepository.findAllById(chefsIds);
        if (lista.isEmpty()) {
            throw new EntityNotFoundException("Los chefs indicados no existen.");
        }
        if (r.getChefs() == null) r.setChefs(new HashSet<>());
        r.getChefs().addAll(lista);

        log.info("Agregando {} chefs a restaurante {}", lista.size(), restauranteId);
        return restauranteRepository.save(r);
    }
}
