package co.edu.udistrital.mdp.back.controllers;

import co.edu.udistrital.mdp.back.entities.UtensiliosEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.UtensiliosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/utensilios")
public class UtensiliosController {

    private final UtensiliosService utensiliosService;

    public UtensiliosController(UtensiliosService utensiliosService) {
        this.utensiliosService = utensiliosService;
    }

    @GetMapping
    public ResponseEntity<List<UtensiliosEntity>> getAllUtensilios() {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtensiliosEntity> getUtensilioById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping
    public ResponseEntity<?> createUtensilio(@RequestBody UtensiliosEntity entity) {
        try {
            UtensiliosEntity created = utensiliosService.crearUtensilio(entity);
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
    public ResponseEntity<?> updateUtensilio(@PathVariable Long id, @RequestBody UtensiliosEntity entity) {
        try {
            UtensiliosEntity updated = utensiliosService.actualizarUtensilio(id, entity);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUtensilio(@PathVariable Long id) {
  
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
