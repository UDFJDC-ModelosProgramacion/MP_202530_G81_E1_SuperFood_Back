package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ChefProfesionalEntity extends ChefAficionadoEntity {

    private String especialidad;
    private boolean verificado;

    @PodamExclude
    @OneToMany(mappedBy = "chefProfesional")
    private List<ServicioEntity> servicios = new ArrayList<>();

    @PodamExclude
    @ManyToMany(mappedBy = "chefs")
    private List<RestauranteEntity> restaurantes = new ArrayList<>();
    
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
}
