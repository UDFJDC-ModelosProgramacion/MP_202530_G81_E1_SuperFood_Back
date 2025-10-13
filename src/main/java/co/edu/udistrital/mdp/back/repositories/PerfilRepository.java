package co.edu.udistrital.mdp.back.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.back.entities.PerfilEntity;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {
    
    
    boolean existsByUsuarioId(Long usuarioId);
    
    boolean hasFotos(Long perfilId);
}
