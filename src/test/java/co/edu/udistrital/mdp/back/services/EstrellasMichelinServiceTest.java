
package co.edu.udistrital.mdp.back.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.back.entities.EstrellasMichelinEntity;
import co.edu.udistrital.mdp.back.entities.RestauranteEntity;
import co.edu.udistrital.mdp.back.repositories.EstrellasMichelinRepository;
import co.edu.udistrital.mdp.back.repositories.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class EstrellasMichelinServiceTest {

    @Autowired private EstrellasMichelinService estrellasService;
    @Autowired private EstrellasMichelinRepository estrellasRepo;
    @Autowired private RestauranteRepository restauranteRepo;

    //CREAR

    @Test
    void crearDebeFallarSiCantidadNull() {
        IllegalStateException ex = assertThrows(IllegalStateException.class,
            () -> estrellasService.crear("desc", null));
        assertTrue(ex.getMessage().toLowerCase().contains("obligatoria"));
    }

    @Test
    void crearDebeFallarSiCantidadFueraDeRango() {
        IllegalStateException ex1 = assertThrows(IllegalStateException.class,
            () -> estrellasService.crear("desc", -1));
        IllegalStateException ex2 = assertThrows(IllegalStateException.class,
            () -> estrellasService.crear("desc", 4));
        assertTrue(ex1.getMessage().toLowerCase().contains("entre 0 y 3"));
        assertTrue(ex2.getMessage().toLowerCase().contains("entre 0 y 3"));
    }

    @Test
    void crearDebeFuncionarConCantidadValida() {
        EstrellasMichelinEntity e = estrellasService.crear("buena", 2);
        assertNotNull(e.getId());
        EstrellasMichelinEntity rec = estrellasRepo.findById(e.getId()).orElseThrow();
        assertEquals(2, rec.getCantidadEstrellas());
        assertEquals("buena", rec.getDescripcion());
    }

    //ACTUALIZAR

    @Test
    void actualizarDebeFallarSiNoExiste() {
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
            () -> estrellasService.actualizar(999999L, "x", 1));
        assertTrue(ex.getMessage().toLowerCase().contains("no existe"));
    }

    @Test
    void actualizarDebeCambiarDescripcionYCantidad() {
        EstrellasMichelinEntity e = estrellasService.crear("old", 1);
        EstrellasMichelinEntity upd = estrellasService.actualizar(e.getId(), "new", 3);
        assertEquals("new", upd.getDescripcion());
        assertEquals(3, upd.getCantidadEstrellas());

        EstrellasMichelinEntity rec = estrellasRepo.findById(e.getId()).orElseThrow();
        assertEquals("new", rec.getDescripcion());
        assertEquals(3, rec.getCantidadEstrellas());
    }

    @Test
    void actualizarDebeFallarSiCantidadFueraDeRango() {
        EstrellasMichelinEntity e = estrellasService.crear("ok", 1);
        IllegalStateException ex = assertThrows(IllegalStateException.class,
            () -> estrellasService.actualizar(e.getId(), null, 5));
        assertTrue(ex.getMessage().toLowerCase().contains("entre 0 y 3"));
    }

    //ELIMINAR
    @Test
    void eliminarDebeFallarSiEstaVinculadaARestaurante() {
        // Crear estrellas y restaurante y vincularlos
        EstrellasMichelinEntity e = estrellasService.crear("vinc", 2);
        RestauranteEntity r = new RestauranteEntity();
        r.setNombre("R con estrellas");
        r.setPorciones(1);
        r.setEstrellasMichelin(e);
        r = restauranteRepo.save(r);

        // Debe fallar la eliminación
        IllegalStateException ex = assertThrows(IllegalStateException.class,
            () -> estrellasService.eliminar(e.getId()));
        assertTrue(ex.getMessage().toLowerCase().contains("vinculada"));

        // Sigue existiendo
        assertTrue(estrellasRepo.findById(e.getId()).isPresent());
    }

    @Test
    void eliminarDebeFuncionarSiNoEstaVinculada() {
        EstrellasMichelinEntity e = estrellasService.crear("libre", 0);
        Long id = e.getId();

        assertDoesNotThrow(() -> estrellasService.eliminar(id));
        assertFalse(estrellasRepo.findById(id).isPresent());
    }

    //DESVINCULAR

    @Test
    void desvincularDeRestauranteDebeQuitarRelacion() {
        EstrellasMichelinEntity e = estrellasService.crear("desv", 1);
        RestauranteEntity r = new RestauranteEntity();
        r.setNombre("R desv");
        r.setPorciones(1);
        r.setEstrellasMichelin(e);
        r = restauranteRepo.save(r);

        //Asegurar que está vinculado
        assertTrue(restauranteRepo.existsByEstrellasMichelin_Id(e.getId()));

        //Desvincular desde el service de Estrellas
        estrellasService.desvincularDeRestaurante(e.getId());

        //Verificar que queda sin estrellas
        RestauranteEntity rec = restauranteRepo.findById(r.getId()).orElseThrow();
        assertNull(rec.getEstrellasMichelin());

        //No debería reportar vinculación
        assertFalse(restauranteRepo.existsByEstrellasMichelin_Id(e.getId()));
    }
}
