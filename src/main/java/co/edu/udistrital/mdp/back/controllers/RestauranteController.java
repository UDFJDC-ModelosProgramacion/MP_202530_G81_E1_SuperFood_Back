package co.edu.udistrital.mdp.back.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.back.entities.RestauranteEntity;
import co.edu.udistrital.mdp.back.services.RestauranteService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    //Crear
    @PostMapping
    public ResponseEntity<RestauranteEntity> crearRestaurante(
            @RequestBody RestauranteEntity body,                       // nombre, porciones, horario(s)
            @RequestParam List<Long> chefsIds,                         // requerido (al menos 1)
            @RequestParam(required = false) Long ubicacionId,          
            @RequestParam(required = false) Long estrellasId) {        

        Set<Long> chefs = new HashSet<>(chefsIds);
        // Usa horario/horarios del body:
        String horario  = body.getHorario();
        String horarios = body.getHorarios();

        RestauranteEntity creado = restauranteService.crearRestaurante(
                body.getNombre(),
                body.getPorciones(),
                chefs,
                ubicacionId,
                estrellasId,
                horario,
                horarios
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //Listar
    @GetMapping
    public ResponseEntity<List<RestauranteEntity>> obtenerTodosLosRestaurantes() {
        List<RestauranteEntity> lista = restauranteService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    //Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteEntity> obtenerRestaurantePorId(@PathVariable Long id) {
        RestauranteEntity r = restauranteService.obtenerPorId(id);
        return ResponseEntity.ok(r);
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteEntity> actualizarRestaurante(
            @PathVariable Long id,
            @RequestBody RestauranteEntity nuevosDatos,                // nombre, porciones, horario(s)
            @RequestParam(required = false) List<Long> chefsIds,       
            @RequestParam(required = false) Long ubicacionId,
            @RequestParam(required = false) Long estrellasId) {

        // RestauranteEntity actualizarRestaurante(Long id, RestauranteEntity datos, 
        //        Set<Long> chefsIds, Long ubicacionId, Long estrellasId)
        RestauranteEntity actualizado = restauranteService.actualizarRestaurante(
                id,
                nuevosDatos,
                (chefsIds != null ? new HashSet<>(chefsIds) : null),
                ubicacionId,
                estrellasId
        );
        return ResponseEntity.ok(actualizado);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRestaurante(@PathVariable Long id) {
        restauranteService.eliminarRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    //Asignar/Reasignar Ubicación
    @PatchMapping("/{id}/ubicacion")
    public ResponseEntity<RestauranteEntity> asignarUbicacion(
            @PathVariable Long id,
            @RequestParam Long ubicacionId) {
        return ResponseEntity.ok(restauranteService.asignarUbicacion(id, ubicacionId));
    }

    //Asignar/Reasignar Estrellas Michelin
    @PatchMapping("/{id}/estrellas")
    public ResponseEntity<RestauranteEntity> asignarEstrellas(
            @PathVariable Long id,
            @RequestParam Long estrellasId) {
        return ResponseEntity.ok(restauranteService.asignarEstrellas(id, estrellasId));
    }

    //Agregar chefs
    @PostMapping("/{id}/chefs")
    public ResponseEntity<RestauranteEntity> agregarChefs(
            @PathVariable Long id,
            @RequestParam List<Long> chefsIds) {
        return ResponseEntity.ok(restauranteService.agregarChefs(id, new HashSet<>(chefsIds)));
    }

    //Quitar foto
    @DeleteMapping("/fotos/{fotoId}")
    public ResponseEntity<Void> quitarFoto(@PathVariable Long fotoId) {
        restauranteService.quitarFoto(fotoId);
        return ResponseEntity.noContent().build();
    }

    //Manejo de errores NOT FOUND
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> manejarNoEncontrado(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    static class ErrorResponse {
        public String error;
        public ErrorResponse(String mensaje) { this.error = mensaje; }
    }
}
