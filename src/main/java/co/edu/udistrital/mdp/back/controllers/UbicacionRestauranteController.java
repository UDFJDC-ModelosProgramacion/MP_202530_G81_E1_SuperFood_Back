package co.edu.udistrital.mdp.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.back.entities.UbicacionRestauranteEntity;
import co.edu.udistrital.mdp.back.services.UbicacionRestauranteService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionRestauranteController {

    @Autowired
    private UbicacionRestauranteService ubicacionService;

    // Crear
    @PostMapping
    public ResponseEntity<UbicacionRestauranteEntity> crearUbicacion(
            @RequestBody UbicacionRestauranteEntity body) {
        UbicacionRestauranteEntity nueva = ubicacionService.crear(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<UbicacionRestauranteEntity>> obtenerTodasLasUbicaciones() {
        List<UbicacionRestauranteEntity> lista = ubicacionService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionRestauranteEntity> obtenerUbicacionPorId(@PathVariable Long id) {
        UbicacionRestauranteEntity ubicacion = ubicacionService.obtenerPorId(id);
        return ResponseEntity.ok(ubicacion);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionRestauranteEntity> actualizarUbicacion(
            @PathVariable Long id,
            @RequestBody UbicacionRestauranteEntity nuevosDatos) {
        UbicacionRestauranteEntity actualizada = ubicacionService.actualizar(id, nuevosDatos);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        ubicacionService.eliminar(id);
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
