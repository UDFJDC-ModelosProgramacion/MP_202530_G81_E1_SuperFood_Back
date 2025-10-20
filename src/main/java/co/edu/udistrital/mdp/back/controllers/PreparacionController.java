package co.edu.udistrital.mdp.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.PreparacionService;

@RestController
@RequestMapping("/preparaciones")
public class PreparacionController {

    @Autowired
    private PreparacionService preparacionService;

    // POST  Crear o actualizar una preparación
    @PostMapping
    public ResponseEntity<PreparacionEntity> guardarPreparacion(@RequestBody PreparacionEntity preparacion)
            throws IllegalOperationException {
        PreparacionEntity guardada = preparacionService.guardarPreparacion(preparacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // GET  Consultar todas las preparaciones
    @GetMapping
    public ResponseEntity<List<PreparacionEntity>> consultarTodas() throws EntityNotFoundException {
        List<PreparacionEntity> lista = preparacionService.consultarTodas();
        return ResponseEntity.ok(lista);
    }

    // GET Consultar preparación por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<PreparacionEntity>> consultarPorNombre(@PathVariable String nombre)
            throws EntityNotFoundException {
        List<PreparacionEntity> lista = preparacionService.consultarPorNombre(nombre);
        return ResponseEntity.ok(lista);
    }

    // Manejo de excepciones 
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorResponse> manejarOperacionInvalida(IllegalOperationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }


    static class ErrorResponse {
        public String error;
        public ErrorResponse(String mensaje) {
            this.error = mensaje;
        }
    }
}
