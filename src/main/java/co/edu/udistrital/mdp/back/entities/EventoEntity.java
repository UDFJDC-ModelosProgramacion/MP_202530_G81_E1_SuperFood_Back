
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.ManyToMany;

@Data
@Entity
public class EventoEntity extends BaseEntity {

    private String nombre;          // Nombre del evento
    private String descripcion;     // Descripción general
    private String ubicacion;       // Dirección o lugar
    private LocalDateTime fecha;    // Fecha y hora del evento

    @PodamExclude
    @ManyToMany(mappedBy = "eventos")
    private List<UsuarioEntity> usuarios = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }

}
