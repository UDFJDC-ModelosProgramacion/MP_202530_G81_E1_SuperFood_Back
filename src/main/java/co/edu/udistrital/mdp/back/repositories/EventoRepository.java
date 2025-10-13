package co.edu.udistrital.mdp.back.repositories;
   
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.back.entities.EventoEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long> {
    
    boolean existsByRestaurante_Id(Long restauranteId);
  
    boolean hasUsuariosInscritos(Long eventoId);
    
    boolean existsByNombreAndFecha(String nombre, LocalDateTime fecha);
    
    Optional<EventoEntity> findByNombreAndFecha(String nombre, LocalDateTime fecha);
    
}
