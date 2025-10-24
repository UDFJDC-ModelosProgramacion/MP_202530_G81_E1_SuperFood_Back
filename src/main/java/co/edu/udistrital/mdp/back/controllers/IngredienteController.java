package co.edu.udistrital.mdp.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.back.entities.IngredienteEntity;
import co.edu.udistrital.mdp.back.services.IngredienteService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @PostMapping
    public ResponseEntity<IngredienteEntity> crearIngrediente(@RequestBody IngredienteEntity ingrediente) {
        IngredienteEntity creado = ingredienteService.crearIngrediente(ingrediente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteEntity> modificarIngrediente(
            @PathVariable Long id,
            @RequestParam String nuevoNombre,
            @RequestParam boolean proteina,
            @RequestParam boolean grasa,
            @RequestParam boolean carbohidrato) {

        IngredienteEntity modificado = ingredienteService.modificarIngrediente(id, nuevoNombre, proteina, grasa, carbohidrato);
        return ResponseEntity.ok(modificado);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<IngredienteEntity> consultarPorNombre(@PathVariable String nombre) {
        IngredienteEntity ingrediente = ingredienteService.consultarPorNombre(nombre);
        return ResponseEntity.ok(ingrediente);
    }

    @GetMapping
    public ResponseEntity<List<IngredienteEntity>> listarTodos() {
        List<IngredienteEntity> lista = ingredienteService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> manejarExistente(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> manejarEstadoInvalido(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    static class ErrorResponse {
        private final String error;

        public String getError() {
            return error;
        }
        public ErrorResponse(String mensaje) {
            this.error = mensaje;
        }
    }
}
