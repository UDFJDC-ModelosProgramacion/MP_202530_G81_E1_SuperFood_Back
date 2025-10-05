package co.edu.udistrital.mdp.back.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.udistrital.mdp.back.entities.IngredienteEntity;

public interface IngredienteRepository extends JpaRepository <IngredienteEntity, Long>{
    List<IngredienteEntity> findByNombre (String nombre);  
}
