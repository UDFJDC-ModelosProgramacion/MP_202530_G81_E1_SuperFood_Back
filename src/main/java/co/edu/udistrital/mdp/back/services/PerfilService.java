package co.edu.udistrital.mdp.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.PerfilEntity;
import co.edu.udistrital.mdp.back.repositories.PerfilRepository;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;


    // crear perfil
    public PerfilEntity crearPerfil(PerfilEntity perfil) {
        if (perfilRepository.existsByUsuarioId(perfil.getUsuario().getId())) {
            throw new IllegalStateException("El usuario ya tiene un perfil.");
        }
        if (perfil.getFotos().isEmpty()){
            throw new IllegalArgumentException("El perfil debe tener al menos una foto.");
        }
        return perfilRepository.save(perfil);
    }

    // actualizar perfil
    public PerfilEntity actualizarPerfil(Long id, PerfilEntity nuevosDatos) {
        PerfilEntity existente = perfilRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El perfil no existe."));
        if (nuevosDatos.getDescripcion() != null) {
            existente.setDescripcion(nuevosDatos.getDescripcion());
        }
        if (nuevosDatos.getComidaPreferida() != null) {
            existente.setComidaPreferida(nuevosDatos.getComidaPreferida());
        }
        if (nuevosDatos.getFotos() != null && !nuevosDatos.getFotos().isEmpty()) {
            existente.setFotos(nuevosDatos.getFotos());
        }
        return perfilRepository.save(existente);
    }

    // eliminar perfil
    public void eliminarPerfil(Long id) {
        if (!perfilRepository.existsById(id)) {
            throw new IllegalArgumentException("El perfil no existe.");
        }
        perfilRepository.deleteById(id);
    }

    // ver un perfil
    public PerfilEntity verPerfil(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El perfil no existe."));
    }

}
