package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ChefAficionadoEntity extends UsuarioEntity {

    private String experiencia;
    private String mejorPlatillo;

    @PodamExclude
    @OneToMany(mappedBy = "chefAficionado")
    
    private List<RecetaEntity> recetas = new ArrayList<>();

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getMejorPlatillo() {
        return mejorPlatillo;
    }

    public void setMejorPlatillo(String mejorPlatillo) {
        this.mejorPlatillo = mejorPlatillo;
    }
}
