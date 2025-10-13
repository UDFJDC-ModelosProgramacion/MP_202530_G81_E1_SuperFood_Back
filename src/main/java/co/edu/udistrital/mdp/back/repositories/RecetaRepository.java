package co.edu.udistrital.mdp.back.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.udistrital.mdp.back.entities.RecetaEntity;

public interface RecetaRepository extends JpaRepository <RecetaEntity, Long>{
    List <RecetaRepository> findByNombre (String nombre);  
}