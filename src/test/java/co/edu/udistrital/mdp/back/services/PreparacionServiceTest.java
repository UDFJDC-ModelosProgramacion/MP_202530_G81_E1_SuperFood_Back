package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(PreparacionService.class)
class PreparacionServiceTest {

    @Autowired
    private PreparacionService preparacionService;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PreparacionRepository preparacionRepository;

    private PodamFactory factory = new PodamFactoryImpl();

    private PreparacionEntity preparacion1;
    private PreparacionEntity preparacion2;

    @BeforeEach
    void setUp() {
        preparacion1 = factory.manufacturePojo(PreparacionEntity.class);
        preparacion1.setNombre("Tallarines");
        preparacion1.setPasos("Hervir agua y cocinar la pasta");
        entityManager.persist(preparacion1);

        preparacion2 = factory.manufacturePojo(PreparacionEntity.class);
        preparacion2.setNombre("Ensalada");
        preparacion2.setPasos("Cortar verduras y mezclar");
        entityManager.persist(preparacion2);
    }

    @Test
    void testGuardarPreparacionNueva() throws IllegalOperationException {
        PreparacionEntity nueva = factory.manufacturePojo(PreparacionEntity.class);
        nueva.setNombre("Pizza artesanal");
        nueva.setPasos("Amasar y hornear");

        PreparacionEntity result = preparacionService.guardarPreparacion(nueva);

        assertNotNull(result.getId());
        assertEquals("Pizza artesanal", result.getNombre());
    }

    @Test
    void testGuardarPreparacionExistente() throws IllegalOperationException {
        PreparacionEntity modificada = factory.manufacturePojo(PreparacionEntity.class);
        modificada.setNombre(preparacion1.getNombre());
        modificada.setPasos("Hervir agua con sal y cocinar la pasta");

        PreparacionEntity result = preparacionService.guardarPreparacion(modificada);

        assertEquals(preparacion1.getNombre(), result.getNombre());
        assertEquals("Hervir agua con sal y cocinar la pasta", result.getPasos());
    }

    @Test
    void testGuardarPreparacionSinNombre() {
        PreparacionEntity invalida = factory.manufacturePojo(PreparacionEntity.class);
        invalida.setNombre(null);
        invalida.setPasos("Algo");

        assertThrows(IllegalOperationException.class, () ->
                preparacionService.guardarPreparacion(invalida));
    }

    @Test
    void testGuardarPreparacionSinPasos() {
        PreparacionEntity invalida = factory.manufacturePojo(PreparacionEntity.class);
        invalida.setNombre("Prueba sin pasos");
        invalida.setPasos(null);

        assertThrows(IllegalOperationException.class, () ->
                preparacionService.guardarPreparacion(invalida));
    }

    @Test
    void testConsultarTodas() throws EntityNotFoundException {
        List<PreparacionEntity> lista = preparacionService.consultarTodas();
        assertEquals(2, lista.size());
    }

    @Test
    void testConsultarTodasVacia() {
        preparacionRepository.deleteAll();
        assertThrows(EntityNotFoundException.class, () ->
                preparacionService.consultarTodas());
    }

    @Test
    void testConsultarPorNombreExistente() throws EntityNotFoundException {
        List<PreparacionEntity> lista = preparacionService.consultarPorNombre("Ensalada");
        assertFalse(lista.isEmpty());
        assertEquals("Ensalada", lista.get(0).getNombre());
    }

    @Test
    void testConsultarPorNombreInexistente() {
        assertThrows(EntityNotFoundException.class, () ->
                preparacionService.consultarPorNombre("No existe"));
    }
}
