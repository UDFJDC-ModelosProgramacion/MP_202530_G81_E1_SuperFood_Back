package co.edu.udistrital.mdp.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.back.entities.EstrellasMichelinEntity;
import co.edu.udistrital.mdp.back.services.EstrellasMichelinService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/estrellas")
public class EstrellasMichelinController {

    @Autowired
    private EstrellasMichelinService estrellasService;

    // Crear
    @PostMapping
    public ResponseEntity<EstrellasMichelinEntity> crearEstrellas(
            @RequestBody EstrellasMichelinEntity body) {
        EstrellasMichelinEntity nueva = estrellasService.crear(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<EstrellasMichelinEntity>> obtenerTodasLasEstrellas() {
        List<EstrellasMichelinEntity> lista = estrellasService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<EstrellasMichelinEntity> obtenerEstrellasPorId(@PathVariable Long id) {
        EstrellasMichelinEntity estrellas = estrellasService.obtenerPorId(id);
        return ResponseEntity.ok(estrellas);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<EstrellasMichelinEntity> actualizarEstrellas(
            @PathVariable Long id,
            @RequestBody EstrellasMichelinEntity nuevosDatos) {
        EstrellasMichelinEntity actualizada = estrellasService.actualizar(id, nuevosDatos);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstrellas(@PathVariable Long id) {
        estrellasService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Manejo de NOT FOUND
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> manejarNoEncontrado(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    static class ErrorResponse {
        public String error;
        public ErrorResponse(String mensaje) { this.error = mensaje; }
    }
}
