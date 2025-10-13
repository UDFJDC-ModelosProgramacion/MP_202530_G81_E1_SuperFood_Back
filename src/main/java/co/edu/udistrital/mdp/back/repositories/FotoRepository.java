package co.edu.udistrital.mdp.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.udistrital.mdp.back.entities.FotoEntity;

public interface FotoRepository extends JpaRepository<FotoEntity, Long> {
    
}
