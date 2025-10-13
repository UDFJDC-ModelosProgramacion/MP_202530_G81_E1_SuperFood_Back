package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.ChefAficionadoEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(ChefAficionadoService.class)
class ChefAficionadoServiceTest {

    @Autowired
    private ChefAficionadoService chefAficionadoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private ChefAficionadoEntity chefAficionado;

    @BeforeEach
    void setUp() {
        chefAficionado = factory.manufacturePojo(ChefAficionadoEntity.class);
        entityManager.persist(chefAficionado);
    }

    @Test
    void testCrearChefAficionado() throws IllegalOperationException {
        ChefAficionadoEntity nuevoChef = factory.manufacturePojo(ChefAficionadoEntity.class);
        ChefAficionadoEntity result = chefAficionadoService.crearChefAficionado(nuevoChef);

        assertNotNull(result);
        assertEquals(nuevoChef.getExperiencia(), result.getExperiencia());
        assertEquals(nuevoChef.getMejorPlatillo(), result.getMejorPlatillo());
    }

    @Test
    void testCrearChef() throws IllegalOperationException {
        ChefAficionadoEntity nuevoChef = factory.manufacturePojo(ChefAficionadoEntity.class);
        ChefAficionadoEntity result = chefAficionadoService.crearChef(nuevoChef);

        assertNotNull(result.getId());
        assertEquals(nuevoChef.getExperiencia(), result.getExperiencia());
    }

    @Test
    void testActualizarChef() throws EntityNotFoundException, IllegalOperationException {
        chefAficionado.setExperiencia("Nueva experiencia");
        ChefAficionadoEntity result = chefAficionadoService.actualizarChef(chefAficionado.getId(), chefAficionado);

        assertEquals("Nueva experiencia", result.getExperiencia());
    }

    @Test
    void testEliminarChef() throws EntityNotFoundException {
        chefAficionadoService.eliminarChef((Long) chefAficionado.getId());
        ChefAficionadoEntity deleted = entityManager.find(ChefAficionadoEntity.class, chefAficionado.getId());
        assertNull(deleted);
    }

    @Test
    void testActualizarChefAficionado() throws EntityNotFoundException, IllegalOperationException {
        chefAficionado.setMejorPlatillo("Pizza artesanal");
        ChefAficionadoEntity result = chefAficionadoService.actualizarChefAficionado(chefAficionado.getId(), chefAficionado);

        assertNotNull(result);
        assertEquals("Pizza artesanal", result.getMejorPlatillo());
    }
}
