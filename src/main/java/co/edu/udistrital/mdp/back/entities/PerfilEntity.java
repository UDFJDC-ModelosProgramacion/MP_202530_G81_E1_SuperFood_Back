package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PerfilEntity extends BaseEntity {

    private String descripcion;
    private String comidaPreferida;

    @PodamExclude
    @OneToMany(mappedBy = "perfil")
    private List<FotoEntity> fotos = new ArrayList<>();
    
    @PodamExclude
    @OneToOne
    private UsuarioEntity usuario;
    
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getComidaPreferida() {
        return comidaPreferida;
    }
    public void setComidaPreferida(String comidaPreferida) {
        this.comidaPreferida = comidaPreferida;
    }
    public List<FotoEntity> getFotos() {
        return fotos;
    }
    public void setFotos(List<FotoEntity> fotos) {
        this.fotos = fotos;
    }
    public UsuarioEntity getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
}
