package co.edu.udistrital.mdp.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.back.entities.UsuarioEntity;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    Optional<UsuarioEntity> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<UsuarioEntity> findByName(String nombre);
    
}
