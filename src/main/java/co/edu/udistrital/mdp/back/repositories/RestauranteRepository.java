package co.edu.udistrital.mdp.back.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.back.entities.RestauranteEntity;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {

    boolean existsByUbicacion_Id(Long ubicacionId);
    Optional<RestauranteEntity> findByUbicacion_Id(Long ubicacionId);

    boolean existsByEstrellasMichelin_Id(Long estrellasId);
    Optional<RestauranteEntity> findByEstrellasMichelin_Id(Long estrellasId);
}
