package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.UtensiliosEntity;
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
@Import(UtensiliosService.class)

class UtensilioServiceTest {

    @Autowired
    private UtensiliosService utensiliosService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private UtensiliosEntity utensilio;

    @BeforeEach
    void setUp() {
        utensilio = factory.manufacturePojo(UtensiliosEntity.class);
        entityManager.persist(utensilio);
    }

    @Test
    void testCrearUtensilio() throws IllegalOperationException {
        UtensiliosEntity nuevoUtensilio = factory.manufacturePojo(UtensiliosEntity.class);
        UtensiliosEntity result = utensiliosService.crearUtensilio(nuevoUtensilio);

        assertNotNull(result);
        assertEquals(nuevoUtensilio.getNombre(), result.getNombre());
        assertEquals(nuevoUtensilio.getMarca(), result.getMarca());
    }

    @Test
    void testActualizarUtensilio() throws EntityNotFoundException, IllegalOperationException {
        utensilio.setMarca("Nueva Marca");
        UtensiliosEntity result = utensiliosService.actualizarUtensilio(utensilio.getId(), utensilio);

        assertNotNull(result);
        assertEquals("Nueva Marca", result.getMarca());
    }
}

