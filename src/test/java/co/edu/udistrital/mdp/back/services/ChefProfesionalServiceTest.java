package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.ChefProfesionalEntity;
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
@Import(ChefProfesionalService.class)

class ChefProfesionalServiceTest {

    @Autowired
    private ChefProfesionalService chefProfesionalService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private ChefProfesionalEntity chefProfesional;

    @BeforeEach
    void setUp() {
        chefProfesional = factory.manufacturePojo(ChefProfesionalEntity.class);
        entityManager.persist(chefProfesional);
    }

    @Test
    void testCrearChefProfesional() throws IllegalOperationException {
        ChefProfesionalEntity nuevoChef = factory.manufacturePojo(ChefProfesionalEntity.class);
        ChefProfesionalEntity result = chefProfesionalService.crearChefProfesional(nuevoChef);

        assertNotNull(result);
        assertEquals(nuevoChef.getEspecialidad(), result.getEspecialidad());
    }

    @Test
    void testActualizarChefProfesional() throws EntityNotFoundException, IllegalOperationException {
        chefProfesional.setVerificado(true);
        ChefProfesionalEntity result = chefProfesionalService.actualizarChefProfesional(
                chefProfesional.getId(), chefProfesional);

        assertTrue(result.isVerificado());
    }
}
