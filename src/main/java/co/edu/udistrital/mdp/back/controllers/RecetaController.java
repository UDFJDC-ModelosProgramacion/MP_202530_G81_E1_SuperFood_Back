package co.edu.udistrital.mdp.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.back.entities.RecetaEntity;
import co.edu.udistrital.mdp.back.services.RecetaService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @PostMapping
    public ResponseEntity<RecetaEntity> crearReceta(
            @RequestBody RecetaEntity receta,
            @RequestParam(required = false) List<Long> idsPreparaciones) {
        RecetaEntity nueva = recetaService.crearReceta(receta, idsPreparaciones);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @GetMapping
    public ResponseEntity<List<RecetaEntity>> obtenerTodasLasRecetas() {
        List<RecetaEntity> lista = recetaService.obtenerTodasLasRecetas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaEntity> obtenerRecetaPorId(@PathVariable Long id) {
        RecetaEntity receta = recetaService.obtenerRecetaPorId(id);
        return ResponseEntity.ok(receta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaEntity> actualizarReceta(
            @PathVariable Long id,
            @RequestBody RecetaEntity nuevosDatos,
            @RequestParam(required = false) List<Long> idsPreparaciones) {
        RecetaEntity actualizada = recetaService.actualizarReceta(id, nuevosDatos, idsPreparaciones);
        return ResponseEntity.ok(actualizada);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> manejarNoEncontrado(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(ex.getMessage())
        );
    }
    
    static class ErrorResponse {
        public String error;
        public ErrorResponse(String mensaje) { this.error = mensaje; }
    }
}

