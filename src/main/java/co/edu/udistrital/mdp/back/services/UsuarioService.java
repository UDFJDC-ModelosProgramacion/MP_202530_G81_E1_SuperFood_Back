package co.edu.udistrital.mdp.back.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.UsuarioEntity;
import co.edu.udistrital.mdp.back.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear usuario
    public UsuarioEntity crearUsuario(UsuarioEntity usuario) {
        if (usuario.getNombre() == null) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (usuario.getEmail() == null) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalStateException("Ya existe un usuario con ese email.");
        }
        return usuarioRepository.save(usuario);
    }

    // Actualizar usuario
    public UsuarioEntity actualizarUsuario(Long id, UsuarioEntity nuevosDatos) {
        Optional<UsuarioEntity> existente = usuarioRepository.findById(id);
        if (!existente.isPresent()) {
            throw new IllegalArgumentException("El usuario no existe.");
        }
        if (nuevosDatos.getEmail() != null) {
            if (usuarioRepository.existsByEmail(nuevosDatos.getEmail())) {
                throw new IllegalStateException("Ya existe un usuario con ese email.");
            }
            existente.get().setEmail(nuevosDatos.getEmail());
        }
        if (nuevosDatos.getNombre() != null) {
            existente.get().setNombre(nuevosDatos.getNombre());
        }
        return usuarioRepository.save(existente.get());
    }

    //eliminar usuario
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("El usuario no existe.");
        }
        usuarioRepository.deleteById(id);
    }

    //buscar usuario por email
    public Optional<UsuarioEntity> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    //buscar usuarios por id
    public Optional<UsuarioEntity> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    //buscar usuarios por nombre
    public java.util.List<UsuarioEntity> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    //ver todos los usuarios
    public java.util.List<UsuarioEntity> verTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }
    
}
