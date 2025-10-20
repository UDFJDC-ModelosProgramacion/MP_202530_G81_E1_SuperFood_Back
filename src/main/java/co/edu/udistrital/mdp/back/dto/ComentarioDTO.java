package co.edu.udistrital.mdp.back.dto;

import lombok.Data;

@Data
public class ComentarioDTO {
    private long id;// agregar este atributo al entity
    private String descripcion;
    private String nombreUsuario; // cambiar por usuarioId en entity mirar asociacion
}
