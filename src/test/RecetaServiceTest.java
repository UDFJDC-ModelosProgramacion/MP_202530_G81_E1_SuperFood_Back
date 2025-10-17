package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.entities.RecetaEntity;
import co.edu.udistrital.mdp.back.repositories.PreparacionRepository;
import co.edu.udistrital.mdp.back.repositories.RecetaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(RecetaService.class)
class RecetaServiceTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private RecetaEntity receta;
    private PreparacionEntity preparacion1;
    private PreparacionEntity preparacion2;

    @BeforeEach
    void setUp() {
        // Crear preparaciones de prueba
        preparacion1 = factory.manufacturePojo(PreparacionEntity.class);
        preparacion2 = factory.manufacturePojo(PreparacionEntity.class);
        entityManager.persist(preparacion1);
        entityManager.persist(preparacion2);

        // Crear  una receta inicial
        receta = factory.manufacturePojo(RecetaEntity.class);
        receta.setPreparaciones(List.of(preparacion1, preparacion2));
        entityManager.persist(receta);
    }

    @Test
    void testCrearReceta() {
        RecetaEntity nuevaReceta = factory.manufacturePojo(RecetaEntity.class);
        List<Long> idsPreparaciones = List.of(preparacion1.getId(), preparacion2.getId());

        RecetaEntity result = recetaService.crearReceta(nuevaReceta, idsPreparaciones);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(nuevaReceta.getNombre(), result.getNombre());
        assertEquals(2, result.getPreparaciones().size());
    }

    @Test
    void testActualizarReceta() {
        RecetaEntity nuevosDatos = factory.manufacturePojo(RecetaEntity.class);
        nuevosDatos.setNombre("Receta actualizada");

        List<Long> idsPreparaciones = new ArrayList<>();
        idsPreparaciones.add(preparacion1.getId());

        RecetaEntity actualizada = recetaService.actualizarReceta(receta.getId(), nuevosDatos, idsPreparaciones);

        assertEquals("Receta actualizada", actualizada.getNombre());
        assertEquals(1, actualizada.getPreparaciones().size());
    }

    @Test
    void testActualizarRecetaNoExistente() {
        RecetaEntity nuevosDatos = factory.manufacturePojo(RecetaEntity.class);
        assertThrows(EntityNotFoundException.class, () ->
                recetaService.actualizarReceta(999L, nuevosDatos, null));
    }

    @Test
    void testEliminarReceta() {
        Long id = receta.getId();
        recetaService.eliminarReceta(id);
        RecetaEntity eliminada = entityManager.find(RecetaEntity.class, id);
        assertNull(eliminada);
    }

    @Test
    void testEliminarRecetaNoExistente() {
        assertThrows(EntityNotFoundException.class, () ->
                recetaService.eliminarReceta(999L));
    }

    @Test
    void testObtenerRecetaPorId() {
        RecetaEntity encontrada = recetaService.obtenerRecetaPorId(receta.getId());
        assertNotNull(encontrada);
        assertEquals(receta.getId(), encontrada.getId());
    }

    @Test
    void testObtenerRecetaPorIdNoExistente() {
        assertThrows(EntityNotFoundException.class, () ->
                recetaService.obtenerRecetaPorId(999L));
    }

    @Test
    void testObtenerTodasLasRecetas() {
        List<RecetaEntity> recetas = recetaService.obtenerTodasLasRecetas();
        assertFalse(recetas.isEmpty());
    }
}
