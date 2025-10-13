
package co.edu.udistrital.mdp.back.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.back.entities.ChefProfesionalEntity;
import co.edu.udistrital.mdp.back.entities.EstrellasMichelinEntity;
import co.edu.udistrital.mdp.back.entities.FotoEntity;
import co.edu.udistrital.mdp.back.entities.RestauranteEntity;
import co.edu.udistrital.mdp.back.entities.UbicacionRestauranteEntity;
import co.edu.udistrital.mdp.back.repositories.ChefProfesionalRepository;
import co.edu.udistrital.mdp.back.repositories.EstrellasMichelinRepository;
import co.edu.udistrital.mdp.back.repositories.FotoRepository;
import co.edu.udistrital.mdp.back.repositories.RestauranteRepository;
import co.edu.udistrital.mdp.back.repositories.UbicacionRestauranteRepository;

@SpringBootTest
@Transactional
class RestauranteServiceTest {

    @Autowired private RestauranteService restauranteService;

    @Autowired private RestauranteRepository restauranteRepo;
    @Autowired private ChefProfesionalRepository chefRepo;
    @Autowired private UbicacionRestauranteRepository ubicacionRepo;
    @Autowired private EstrellasMichelinRepository estrellasRepo;
    @Autowired private FotoRepository fotoRepo;

    //Helpers
    private ChefProfesionalEntity crearChef(String nombre) {
        ChefProfesionalEntity c = new ChefProfesionalEntity();
        c.setNombre(nombre);
        return chefRepo.save(c);
    }

    //CREAR

    @Test
    void crearDebeFallarSiNombreVacio() {
        ChefProfesionalEntity chef = crearChef("Chef N");
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
            restauranteService.crearRestaurante("  ", 1, Set.of(chef.getId()), null, null, null, null)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("nombre"));
    }

    @Test
    void crearDebeFallarSiPorcionesMenorQue1() {
        ChefProfesionalEntity chef = crearChef("Chef N");
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
            restauranteService.crearRestaurante("R", 0, Set.of(chef.getId()), null, null, null, null)
        );
        assertTrue(ex.getMessage().contains("≥ 1"));
    }

    @Test
    void crearDebeFallarSiNoHayChefs() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
            restauranteService.crearRestaurante("R", 1, Set.of(), null, null, null, null)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("chefprofesional"));
    }

    @Test
    void crearDebeFuncionarConChefExistente() {
        ChefProfesionalEntity chef = crearChef("Chef OK");
        RestauranteEntity r = restauranteService.crearRestaurante(
                "R OK", 1, Set.of(chef.getId()), null, null, "8-18", null
        );
        assertNotNull(r.getId());
        RestauranteEntity rec = restauranteRepo.findById(r.getId()).orElseThrow();
        assertEquals("R OK", rec.getNombre());
        assertEquals(1, rec.getPorciones());
        assertFalse(rec.getChefs().isEmpty());
    }


    //ELIMINAR (Desvincula y borra relaciones)

    @Test
    void debeEliminarCuandoNoHayVinculosYLimpiaRelaciones() {
        // Crear restaurante completo con ubicacion, estrellas y fotos
        ChefProfesionalEntity chef = crearChef("ChefFull");
        UbicacionRestauranteEntity u = new UbicacionRestauranteEntity();
        u.setDireccion("Calle 1 # 2-3");
        u = ubicacionRepo.save(u);

        EstrellasMichelinEntity em = new EstrellasMichelinEntity();
        em.setDescripcion("Buenas");
        em.setCantidadEstrellas(2);
        em = estrellasRepo.save(em);

        RestauranteEntity r = restauranteService.crearRestaurante(
                "R Full", 1, Set.of(chef.getId()), u.getId(), em.getId(), null, null
        );

        // Foto asociada
        FotoEntity f1 = new FotoEntity();
        f1.setRestaurante(r);
        fotoRepo.save(f1);

        Long restId = r.getId();
        Long ubicId = u.getId();
        Long estId  = em.getId();

        //No hay servicios/eventos → debería permitir eliminar
        assertDoesNotThrow(() -> restauranteService.eliminarRestaurante(restId));

        //Restaurante eliminado
        assertFalse(restauranteRepo.findById(restId).isPresent());

        //Ubicación se elimina explícitamente
        assertFalse(ubicacionRepo.findById(ubicId).isPresent());
        //Estrellas se elimina explícitamente
        assertFalse(estrellasRepo.findById(estId).isPresent());
        //Fotos eliminadas
        List<FotoEntity> fotosRestantes = fotoRepo.findAll();
        assertTrue(fotosRestantes.isEmpty());
    }
}
