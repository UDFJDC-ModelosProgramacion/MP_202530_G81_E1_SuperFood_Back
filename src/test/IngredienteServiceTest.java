package co.edu.udistrital.mdp.back.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.back.entities.IngredienteEntity;
import co.edu.udistrital.mdp.back.repositories.IngredienteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(IngredienteService.class)
class IngredienteServiceTest {

    @Autowired
    private IngredienteService ingredienteService;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    private final PodamFactory factory = new PodamFactoryImpl();

    @Test
    void testCrearIngrediente_Exitoso() {
        IngredienteEntity ingrediente = new IngredienteEntity();
        ingrediente.setNombre("Pollo");
        ingrediente.setEsProteina(true);
        ingrediente.setEsGrasa(false);
        ingrediente.setEsCarbohidrato(false);

        IngredienteEntity creado = ingredienteService.crearIngrediente(ingrediente);

        assertNotNull(creado.getId());
        assertEquals("Pollo", creado.getNombre());
        assertTrue(creado.getEsProteina());
    }

    @Test
    void testCrearIngrediente_Repetido() {
        IngredienteEntity existente = new IngredienteEntity();
        existente.setNombre("Arroz");
        existente.setEsCarbohidrato(true);
        ingredienteRepository.save(existente);

        IngredienteEntity duplicado = new IngredienteEntity();
        duplicado.setNombre("Arroz");
        duplicado.setEsCarbohidrato(true);

        assertThrows(EntityExistsException.class, () -> ingredienteService.crearIngrediente(duplicado));
    }

    @Test
    void testCrearIngrediente_SinCategoria() {
        IngredienteEntity sinCategoria = new IngredienteEntity();
        sinCategoria.setNombre("Aceite");
        sinCategoria.setEsProteina(false);
        sinCategoria.setEsCarbohidrato(false);
        sinCategoria.setEsGrasa(false);

        assertThrows(EntityNotFoundException.class, () -> ingredienteService.crearIngrediente(sinCategoria));
    }

    @Test
    void testModificarIngrediente_Exitoso() {
        IngredienteEntity ingrediente = new IngredienteEntity();
        ingrediente.setNombre("Pescado");
        ingrediente.setEsProteina(true);
        ingredienteRepository.save(ingrediente);

        IngredienteEntity modificado = ingredienteService.modificarIngrediente(
                ingrediente.getId(),
                "Atún",
                true,
                false,
                false
        );

        assertEquals("Atún", modificado.getNombre());
        assertTrue(modificado.getEsProteina());
    }

    @Test
    void testModificarIngrediente_NombreDuplicado() {
        IngredienteEntity i1 = new IngredienteEntity();
        i1.setNombre("Leche");
        i1.setEsProteina(true);
        ingredienteRepository.save(i1);

        IngredienteEntity i2 = new IngredienteEntity();
        i2.setNombre("Yogurt");
        i2.setEsProteina(true);
        ingredienteRepository.save(i2);

        assertThrows(IllegalStateException.class, () ->
                ingredienteService.modificarIngrediente(i2.getId(), "Leche", true, false, false)
        );
    }

    @Test
    void testConsultarPorNombre_Exitoso() {
        IngredienteEntity ingrediente = new IngredienteEntity();
        ingrediente.setNombre("Pan");
        ingrediente.setEsCarbohidrato(true);
        ingredienteRepository.save(ingrediente);

        IngredienteEntity encontrado = ingredienteService.consultarPorNombre("Pan");
        assertEquals("Pan", encontrado.getNombre());
    }

    @Test
    void testConsultarPorNombre_NoExistente() {
        assertThrows(EntityNotFoundException.class, () -> ingredienteService.consultarPorNombre("Nada"));
    }

    @Test
    void testListarTodos() {
        IngredienteEntity i1 = new IngredienteEntity();
        i1.setNombre("Papa");
        i1.setEsCarbohidrato(true);
        ingredienteRepository.save(i1);

        IngredienteEntity i2 = new IngredienteEntity();
        i2.setNombre("Carne");
        i2.setEsProteina(true);
        ingredienteRepository.save(i2);

        List<IngredienteEntity> lista = ingredienteService.listarTodos();
        assertEquals(2, lista.size());
    }
}
