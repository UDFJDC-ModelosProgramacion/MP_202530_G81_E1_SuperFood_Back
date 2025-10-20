package co.edu.udistrital.mdp.back.controllers;

import co.edu.udistrital.mdp.back.entities.ChefProfesionalEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.ChefProfesionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/chefprofesional")
public class ChefProfesionalController {

    private final ChefProfesionalService chefProfesionalService;

    public ChefProfesionalController(ChefProfesionalService chefProfesionalService) {
        this.chefProfesionalService = chefProfesionalService;
    }

    @GetMapping
    public ResponseEntity<List<ChefProfesionalEntity>> getAllChefProfesionales() {
        
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChefProfesionalEntity> getChefProfesionalById(@PathVariable Long id) {
      
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping
    public ResponseEntity<?> createChefProfesional(@RequestBody ChefProfesionalEntity entity) {
        try {
            ChefProfesionalEntity created = chefProfesionalService.crearChefProfesional(entity);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(location).body(created);
        } catch (IllegalOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChefProfesional(
            @PathVariable Long id,
            @RequestBody ChefProfesionalEntity entity) {
        try {
            ChefProfesionalEntity updated = chefProfesionalService.actualizarChefProfesional(id, entity);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChefProfesional(@PathVariable Long id) {
       
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
