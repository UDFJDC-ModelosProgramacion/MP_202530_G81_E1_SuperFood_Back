package co.edu.udistrital.mdp.back.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.udistrital.mdp.back.entities.IngredientePreparacionEntity;
import co.edu.udistrital.mdp.back.entities.PreparacionEntity;

public interface IngredientePreparacionRepository extends JpaRepository<IngredientePreparacionEntity, Long> {

    Optional<IngredientePreparacionEntity> findByPreparacionIdAndIngredienteId(Long preparacionId, Long ingredienteId);

    List<IngredientePreparacionEntity> findByPreparacion(PreparacionEntity preparacion);
}