package co.edu.udistrital.mdp.back.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.udistrital.mdp.back.entities.PreparacionEntity;

public interface PreparacionRepository extends JpaRepository <PreparacionEntity, Long>{
    List <PreparacionEntity> findByNombre (String nombre);  
}