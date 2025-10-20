

package co.edu.udistrital.mdp.back.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.udistrital.mdp.back.entities.UsuarioEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de lógica de Usuario
 */
@DataJpaTest
@Transactional
@Import(UsuarioService.class)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<UsuarioEntity> usuarioList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            UsuarioEntity usuarioEntity = factory.manufacturePojo(UsuarioEntity.class);
            entityManager.persist(usuarioEntity);
            usuarioList.add(usuarioEntity);
        }
    }

    /**
     * Prueba para crear un Usuario.
     */
    @Test
    void testCrearUsuario() {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioService.crearUsuario(newEntity);
        assertNotNull(result);

        UsuarioEntity entity = entityManager.find(UsuarioEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getEmail(), entity.getEmail());
    }

    /**
     * Prueba para crear un Usuario con el mismo email de un Usuario que ya existe.
     */
    @Test
    void testCrearUsuarioConEmailDuplicado() {
        assertThrows(IllegalStateException.class, () -> {
            UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
            newEntity.setEmail(usuarioList.get(0).getEmail());
            usuarioService.crearUsuario(newEntity);
        });
    }

    /**
     * Prueba para crear un Usuario sin nombre.
     */
    @Test
    void testCrearUsuarioSinNombre() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
            newEntity.setNombre(null);
            usuarioService.crearUsuario(newEntity);
        });
    }

    /**
     * Prueba para crear un Usuario sin email.
     */
    @Test
    void testCrearUsuarioSinEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
            newEntity.setEmail(null);
            usuarioService.crearUsuario(newEntity);
        });
    }

    /**
     * Prueba para consultar la lista de Usuarios.
     */
    @Test
    void testVerTodosLosUsuarios() {
        List<UsuarioEntity> list = usuarioService.verTodosLosUsuarios();
        assertEquals(usuarioList.size(), list.size());
        for (UsuarioEntity entity : list) {
            boolean found = false;
            for (UsuarioEntity storedEntity : usuarioList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Usuario por ID.
     */
    @Test
    void testBuscarPorId() {
        UsuarioEntity entity = usuarioList.get(0);
        UsuarioEntity resultEntity = usuarioService.buscarPorId(entity.getId()).orElse(null);
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getEmail(), resultEntity.getEmail());
    }

    /**
     * Prueba para consultar un Usuario que no existe por ID.
     */
    @Test
    void testBuscarPorIdInvalido() {
        assertTrue(usuarioService.buscarPorId(0L).isEmpty());
    }

    /**
     * Prueba para consultar un Usuario por email.
     */
    @Test
    void testBuscarPorEmail() {
        UsuarioEntity entity = usuarioList.get(0);
        UsuarioEntity resultEntity = usuarioService.buscarPorEmail(entity.getEmail()).orElse(null);
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getEmail(), resultEntity.getEmail());
    }

    /**
     * Prueba para consultar un Usuario que no existe por email.
     */
    @Test
    void testBuscarPorEmailInvalido() {
        assertTrue(usuarioService.buscarPorEmail("noexiste@test.com").isEmpty());
    }

    /**
     * Prueba para consultar Usuarios por nombre.
     */
    @Test
    void testBuscarPorNombre() {
        UsuarioEntity entity = usuarioList.get(0);
        List<UsuarioEntity> resultList = usuarioService.buscarPorNombre(entity.getNombre());
        assertFalse(resultList.isEmpty());
        
        boolean found = false;
        for (UsuarioEntity resultEntity : resultList) {
            if (resultEntity.getId().equals(entity.getId())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    /**
     * Prueba para actualizar un Usuario.
     */
    @Test
    void testActualizarUsuario() {
        UsuarioEntity entity = usuarioList.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);
        pojoEntity.setId(entity.getId());
        
        usuarioService.actualizarUsuario(entity.getId(), pojoEntity);
        
        UsuarioEntity resp = entityManager.find(UsuarioEntity.class, entity.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getEmail(), resp.getEmail());
    }

    /**
     * Prueba para actualizar un Usuario que no existe.
     */
    @Test
    void testActualizarUsuarioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);
            usuarioService.actualizarUsuario(0L, pojoEntity);
        });
    }

    /**
     * Prueba para actualizar un Usuario con email duplicado.
     */
    @Test
    void testActualizarUsuarioEmailDuplicado() {
        assertThrows(IllegalStateException.class, () -> {
            UsuarioEntity entity = usuarioList.get(0);
            UsuarioEntity updateData = new UsuarioEntity();
            updateData.setEmail(usuarioList.get(1).getEmail()); // Email que ya existe
            
            usuarioService.actualizarUsuario(entity.getId(), updateData);
        });
    }

    /**
     * Prueba para eliminar un Usuario.
     */
    @Test
    void testEliminarUsuario() {
        UsuarioEntity entity = usuarioList.get(1);
        usuarioService.eliminarUsuario(entity.getId());
        UsuarioEntity deleted = entityManager.find(UsuarioEntity.class, entity.getId());
        assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Usuario que no existe.
     */
    @Test
    void testEliminarUsuarioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.eliminarUsuario(0L);
        });
    }
}
