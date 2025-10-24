package co.edu.udistrital.mdp.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.back.entities.IngredientePreparacionEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.services.IngredientePreparacionService;

@RestController
@RequestMapping("/ingrediente-preparacion")
public class IngredientePreparacionController {

    @Autowired
    private IngredientePreparacionService ingredientePreparacionService;

    @PostMapping("/{preparacionId}/{ingredienteId}")
    public ResponseEntity<IngredientePreparacionEntity> asociarIngrediente(
            @PathVariable Long preparacionId,
            @PathVariable Long ingredienteId,
            @RequestParam Double gramaje,
            @RequestParam String porcion) throws EntityNotFoundException {

        IngredientePreparacionEntity relacion = ingredientePreparacionService.asociarIngrediente(
                preparacionId, ingredienteId, gramaje, porcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(relacion);
    }

    @DeleteMapping("/{preparacionId}/{ingredienteId}")
    public ResponseEntity<Void> quitarIngrediente(
            @PathVariable Long preparacionId,
            @PathVariable Long ingredienteId) throws EntityNotFoundException {

        ingredientePreparacionService.quitarIngrediente(preparacionId, ingredienteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{preparacionId}")
    public ResponseEntity<List<IngredientePreparacionEntity>> consultarIngredientes(
            @PathVariable Long preparacionId) throws EntityNotFoundException {

        List<IngredientePreparacionEntity> lista =
                ingredientePreparacionService.consultarIngredientes(preparacionId);
        return ResponseEntity.ok(lista);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
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
