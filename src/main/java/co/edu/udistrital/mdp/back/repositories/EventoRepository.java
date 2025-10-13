package co.edu.udistrital.mdp.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.back.entities.EventoEntity;

public interface EventoRepository extends JpaRepository<EventoEntity, Long> {

    boolean existsByRestaurante_Id(Long restauranteId);
}
