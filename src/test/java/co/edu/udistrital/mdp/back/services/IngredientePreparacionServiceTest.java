package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.IngredienteEntity;
import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.entities.IngredientePreparacionEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.repositories.IngredientePreparacionRepository;
import co.edu.udistrital.mdp.back.repositories.IngredienteRepository;
import co.edu.udistrital.mdp.back.repositories.PreparacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(IngredientePreparacionService.class)
class IngredientePreparacionServiceTest {

    @Autowired
    private IngredientePreparacionService ingredientePreparacionService;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IngredientePreparacionRepository ingredientePreparacionRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private PreparacionRepository preparacionRepository;

    private PodamFactory factory = new PodamFactoryImpl();

    private IngredienteEntity ingrediente;
    private PreparacionEntity preparacion;

    @BeforeEach
    void setUp() {
        ingrediente = factory.manufacturePojo(IngredienteEntity.class);
        preparacion = factory.manufacturePojo(PreparacionEntity.class);

        entityManager.persist(ingrediente);
        entityManager.persist(preparacion);
    }

    @Test
    void testAsociarIngrediente() throws EntityNotFoundException {
        IngredientePreparacionEntity result = ingredientePreparacionService.asociarIngrediente(
                preparacion.getId(), ingrediente.getId(), 120.0, "1 taza");

        assertNotNull(result.getId());
        assertEquals(preparacion.getId(), result.getPreparacion().getId());
        assertEquals(ingrediente.getId(), result.getIngrediente().getId());
        assertEquals(120.0, result.getGramaje());
        assertEquals("1 taza", result.getPorcion());
    }

    @Test
    void testAsociarIngredientePreparacionNoExiste() {
        assertThrows(EntityNotFoundException.class, () ->
                ingredientePreparacionService.asociarIngrediente(999L, ingrediente.getId(), 50.0, "1 unidad"));
    }

    @Test
    void testAsociarIngredienteIngredienteNoExiste() {
        assertThrows(EntityNotFoundException.class, () ->
                ingredientePreparacionService.asociarIngrediente(preparacion.getId(), 999L, 50.0, "1 unidad"));
    }

    @Test
    void testQuitarIngrediente() throws EntityNotFoundException {
        IngredientePreparacionEntity relacion = ingredientePreparacionService.asociarIngrediente(
                preparacion.getId(), ingrediente.getId(), 100.0, "1 taza");

        ingredientePreparacionService.quitarIngrediente(preparacion.getId(), ingrediente.getId());

        Optional<IngredientePreparacionEntity> deleted = ingredientePreparacionRepository
                .findByPreparacionIdAndIngredienteId(preparacion.getId(), ingrediente.getId());
        assertTrue(deleted.isEmpty());
    }

    @Test
    void testQuitarIngredienteRelacionNoExiste() {
        assertThrows(EntityNotFoundException.class, () ->
                ingredientePreparacionService.quitarIngrediente(preparacion.getId(), ingrediente.getId()));
    }

    @Test
    void testConsultarIngredientes() throws EntityNotFoundException {
        IngredientePreparacionEntity relacion = ingredientePreparacionService.asociarIngrediente(
                preparacion.getId(), ingrediente.getId(), 80.0, "1 porción");

        List<IngredientePreparacionEntity> lista = ingredientePreparacionService.consultarIngredientes(preparacion.getId());

        assertEquals(1, lista.size());
        assertEquals(ingrediente.getId(), lista.get(0).getIngrediente().getId());
    }

    @Test
    void testConsultarIngredientesSinAsociaciones() {
        assertThrows(EntityNotFoundException.class, () ->
                ingredientePreparacionService.consultarIngredientes(preparacion.getId()));
    }

    @Test
    void testConsultarIngredientesPreparacionNoExiste() {
        assertThrows(EntityNotFoundException.class, () ->
                ingredientePreparacionService.consultarIngredientes(999L));
    }
}
