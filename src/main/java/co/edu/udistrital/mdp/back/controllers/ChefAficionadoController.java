package co.edu.udistrital.mdp.back.controllers;

import co.edu.udistrital.mdp.back.entities.ChefAficionadoEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.ChefAficionadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/chefaficionado")
public class ChefAficionadoController {

    private final ChefAficionadoService chefAficionadoService;

    public ChefAficionadoController(ChefAficionadoService chefAficionadoService) {
        this.chefAficionadoService = chefAficionadoService;
    }

    @GetMapping
    public ResponseEntity<List<ChefAficionadoEntity>> getAllChefAficionados() {
 TED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChefAficionadoEntity> getChefAficionadoById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping
    public ResponseEntity<?> createChefAficionado(@RequestBody ChefAficionadoEntity entity) {
        try {
            ChefAficionadoEntity created = chefAficionadoService.crearChefAficionado(entity);
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
    public ResponseEntity<?> updateChefAficionado(@PathVariable Long id, @RequestBody ChefAficionadoEntity entity) {
        try {
            ChefAficionadoEntity updated = chefAficionadoService.actualizarChefAficionado(id, entity);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChefAficionado(@PathVariable Long id) {
        try {
            chefAficionadoService.eliminarChef(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
