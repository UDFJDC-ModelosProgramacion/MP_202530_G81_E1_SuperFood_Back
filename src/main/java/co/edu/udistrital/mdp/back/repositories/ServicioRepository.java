package co.edu.udistrital.mdp.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.back.entities.ServicioEntity;

public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {

    boolean existsByRestaurante_IdAndActivoTrue(Long restauranteId);
}

