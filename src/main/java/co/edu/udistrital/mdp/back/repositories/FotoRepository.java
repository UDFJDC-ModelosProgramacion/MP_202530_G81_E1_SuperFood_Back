package co.edu.udistrital.mdp.back.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.udistrital.mdp.back.entities.FotoEntity;
import co.edu.udistrital.mdp.back.entities.IngredienteEntity;
import co.edu.udistrital.mdp.back.entities.RecetaEntity;
import co.edu.udistrital.mdp.back.entities.RestauranteEntity;
import co.edu.udistrital.mdp.back.entities.ServicioEntity;
import java.util.List;

@Repository
public interface FotoRepository extends JpaRepository<FotoEntity, Long> {
    
    public List <FotoEntity> findByServicio(ServicioEntity servicio);
    public List <FotoEntity> findByRestaurante(RestauranteEntity restaurante);
    public List <FotoEntity> findByReceta(RecetaEntity receta);
    public List <FotoEntity> findByIngrediente(IngredienteEntity ingrediente);
    public FotoEntity findByEnlace(String enlace);

}
