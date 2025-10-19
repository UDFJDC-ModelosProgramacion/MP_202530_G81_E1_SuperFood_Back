
package co.edu.udistrital.mdp.back.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.back.entities.RestauranteEntity;
import co.edu.udistrital.mdp.back.entities.UbicacionRestauranteEntity;
import co.edu.udistrital.mdp.back.repositories.RestauranteRepository;
import co.edu.udistrital.mdp.back.repositories.UbicacionRestauranteRepository;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class UbicacionRestauranteServiceTest {

    @Autowired private UbicacionRestauranteService ubicacionService;
    @Autowired private UbicacionRestauranteRepository ubicacionRepo;
    @Autowired private RestauranteRepository restauranteRepo;

    //CREAR

    @Test
    void crearDebeFallarSiDireccionVacia() {
        IllegalStateException ex = assertThrows(IllegalStateException.class,
            () -> ubicacionService.crear("   "));
        assertTrue(ex.getMessage().toLowerCase().contains("dirección"));
    }

    @Test
    void crearDebeFuncionarConDireccionValida() {
        UbicacionRestauranteEntity u = ubicacionService.crear("Calle 1 # 2-3");
        assertNotNull(u.getId());
        UbicacionRestauranteEntity rec = ubicacionRepo.findById(u.getId()).orElseThrow();
        assertEquals("Calle 1 # 2-3", rec.getDireccion());
    }

    //ACTUALIZAR
    @Test
    void actualizarDebeCambiarDireccion() {
        UbicacionRestauranteEntity u = ubicacionService.crear("Dir A");
        UbicacionRestauranteEntity upd = ubicacionService.actualizar(u.getId(), "Dir B");
        assertEquals("Dir B", upd.getDireccion());

        UbicacionRestauranteEntity rec = ubicacionRepo.findById(u.getId()).orElseThrow();
        assertEquals("Dir B", rec.getDireccion());
    }

    @Test
    void actualizarDebeFallarSiNoExiste() {
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
            () -> ubicacionService.actualizar(999999L, "Cualquier"));
        assertTrue(ex.getMessage().toLowerCase().contains("ubicación no existe"));
    }

    //ELIMINAR

    @Test
    void eliminarDebeFallarSiEstaVinculadaARestaurante() {
        // Crear ubicación y restaurante y vincularlos
        UbicacionRestauranteEntity u = ubicacionService.crear("Dir X");
        RestauranteEntity r = new RestauranteEntity();
        r.setNombre("R X");
        r.setPorciones(1);
        r.setUbicacion(u);
        r = restauranteRepo.save(r);

        // Confirma que el repositorio detecta la vinculación
        assertTrue(restauranteRepo.existsByUbicacion_Id(u.getId()));

        // Debe fallar la eliminación por regla de negocio
        IllegalStateException ex = assertThrows(IllegalStateException.class,
            () -> ubicacionService.eliminar(u.getId()));
        assertTrue(ex.getMessage().toLowerCase().contains("vinculada"));
        // Sigue existiendo
        assertTrue(ubicacionRepo.findById(u.getId()).isPresent());
    }

    @Test
    void eliminarDebeFuncionarSiNoEstaVinculada() {
        UbicacionRestauranteEntity u = ubicacionService.crear("Dir Y");
        Long id = u.getId();

        assertDoesNotThrow(() -> ubicacionService.eliminar(id));
        assertFalse(ubicacionRepo.findById(id).isPresent());
    }

    //DESVINCULAR

    @Test
    void desvincularDeRestauranteDebeQuitarRelacion() {
        // Ubicación y restaurante vinculados
        UbicacionRestauranteEntity u = ubicacionService.crear("Dir Z");
        RestauranteEntity r = new RestauranteEntity();
        r.setNombre("R Z");
        r.setPorciones(1);
        r.setUbicacion(u);
        r = restauranteRepo.save(r);

        //Asegura vinculación
        assertTrue(restauranteRepo.existsByUbicacion_Id(u.getId()));

        //Desvincular desde el servicio de Ubicación
        ubicacionService.desvincularDeRestaurante(u.getId());

        //Refresca
        RestauranteEntity rec = restauranteRepo.findById(r.getId()).orElseThrow();
        assertNull(rec.getUbicacion());

        //No debería reportar vinculación
        assertFalse(restauranteRepo.existsByUbicacion_Id(u.getId()));
    }
}
