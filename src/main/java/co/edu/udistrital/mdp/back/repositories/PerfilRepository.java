package co.edu.udistrital.mdp.back.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.back.entities.PerfilEntity;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {
    
    
    boolean existsByUsuarioId(Long usuarioId);
    
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM PerfilEntity p JOIN p.fotos f WHERE p.id = :perfilId")
    boolean hasFotos(Long perfilId);
}
