package co.edu.udistrital.mdp.back.repositories;
   
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.back.entities.EventoEntity;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long> {
    
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM EventoEntity e JOIN e.usuarios u WHERE e.id = :eventoId")
    boolean hasUsuariosInscritos(@Param("eventoId") Long eventoId);
    
    boolean existsByNombreAndFecha(String nombre, LocalDateTime fecha);
    
    Optional<EventoEntity> findByNombreAndFecha(String nombre, LocalDateTime fecha);
    
}
