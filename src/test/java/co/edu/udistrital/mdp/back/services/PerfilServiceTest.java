
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

import co.edu.udistrital.mdp.back.entities.FotoEntity;
import co.edu.udistrital.mdp.back.entities.PerfilEntity;
import co.edu.udistrital.mdp.back.entities.UsuarioEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de lógica de Perfil
 */
@DataJpaTest
@Transactional
@Import(PerfilService.class)
public class PerfilServiceTest {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PerfilEntity> perfilList = new ArrayList<>();
    private List<UsuarioEntity> usuarioList = new ArrayList<>();
    private List<FotoEntity> fotoList = new ArrayList<>();

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
        entityManager.getEntityManager().createQuery("delete from FotoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from PerfilEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        // Crear usuarios
        for (int i = 0; i < 3; i++) {
            UsuarioEntity usuarioEntity = factory.manufacturePojo(UsuarioEntity.class);
            entityManager.persist(usuarioEntity);
            usuarioList.add(usuarioEntity);
        }

        // Crear perfiles para los dos primeros usuarios
        for (int i = 0; i < 2; i++) {
            PerfilEntity perfilEntity = factory.manufacturePojo(PerfilEntity.class);
            perfilEntity.setUsuario(usuarioList.get(i));
            perfilEntity.setFotos(new ArrayList<>());
            entityManager.persist(perfilEntity);
            perfilList.add(perfilEntity);

            // Crear fotos para cada perfil
            for (int j = 0; j < 2; j++) {
                FotoEntity fotoEntity = factory.manufacturePojo(FotoEntity.class);
                fotoEntity.setPerfil(perfilEntity);
                // Limpiar otras relaciones para evitar conflictos
                fotoEntity.setServicio(null);
                fotoEntity.setReceta(null);
                fotoEntity.setRestaurante(null);
                fotoEntity.setIngrediente(null);
                entityManager.persist(fotoEntity);
                fotoList.add(fotoEntity);
                perfilEntity.getFotos().add(fotoEntity);
            }
        }
    }

    /**
     * Prueba para crear un Perfil.
     */
    @Test
    void testCrearPerfil() {
        PerfilEntity newEntity = factory.manufacturePojo(PerfilEntity.class);
        newEntity.setUsuario(usuarioList.get(2)); // Usuario sin perfil
        
        // Usar una foto existente de la lista para evitar problemas de persistencia
        List<FotoEntity> fotos = new ArrayList<>();
        fotos.add(fotoList.get(0)); // Usar una foto ya persistida
        newEntity.setFotos(fotos);

        PerfilEntity result = perfilService.crearPerfil(newEntity);
        assertNotNull(result);

        PerfilEntity entity = entityManager.find(PerfilEntity.class, result.getId());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEntity.getComidaPreferida(), entity.getComidaPreferida());
        assertEquals(newEntity.getUsuario().getId(), entity.getUsuario().getId());
        assertFalse(entity.getFotos().isEmpty());
    }

    /**
     * Prueba para crear un Perfil para un usuario que ya tiene perfil.
     */
    @Test
    void testCrearPerfilUsuarioYaTienePerfil() {
        assertThrows(IllegalStateException.class, () -> {
            PerfilEntity newEntity = factory.manufacturePojo(PerfilEntity.class);
            newEntity.setUsuario(usuarioList.get(0)); // Usuario que ya tiene perfil
            
            // Crear al menos una foto
            FotoEntity foto = factory.manufacturePojo(FotoEntity.class);
            List<FotoEntity> fotos = new ArrayList<>();
            fotos.add(foto);
            newEntity.setFotos(fotos);
            
            perfilService.crearPerfil(newEntity);
        });
    }

    /**
     * Prueba para crear un Perfil sin fotos.
     */
    @Test
    void testCrearPerfilSinFotos() {
        assertThrows(IllegalArgumentException.class, () -> {
            PerfilEntity newEntity = factory.manufacturePojo(PerfilEntity.class);
            newEntity.setUsuario(usuarioList.get(2)); // Usuario sin perfil
            newEntity.setFotos(new ArrayList<>()); // Lista vacía
            
            perfilService.crearPerfil(newEntity);
        });
    }

    /**
     * Prueba para actualizar un Perfil.
     */
    @Test
    void testActualizarPerfil() {
        PerfilEntity entity = perfilList.get(0);
        PerfilEntity datosActualizacion = factory.manufacturePojo(PerfilEntity.class);
        datosActualizacion.setId(entity.getId());

        PerfilEntity result = perfilService.actualizarPerfil(entity.getId(), datosActualizacion);
        
        assertNotNull(result);
        PerfilEntity resp = entityManager.find(PerfilEntity.class, entity.getId());
        assertEquals(datosActualizacion.getDescripcion(), resp.getDescripcion());
        assertEquals(datosActualizacion.getComidaPreferida(), resp.getComidaPreferida());
    }

    /**
     * Prueba para actualizar un Perfil que no existe.
     */
    @Test
    void testActualizarPerfilInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            PerfilEntity datosActualizacion = factory.manufacturePojo(PerfilEntity.class);
            perfilService.actualizarPerfil(0L, datosActualizacion);
        });
    }

    /**
     * Prueba para ver un Perfil.
     */
    @Test
    void testVerPerfil() {
        PerfilEntity entity = perfilList.get(0);
        PerfilEntity resultEntity = perfilService.verPerfil(entity.getId());
        
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getComidaPreferida(), resultEntity.getComidaPreferida());
        assertEquals(entity.getUsuario().getId(), resultEntity.getUsuario().getId());
    }

    /**
     * Prueba para ver un Perfil que no existe.
     */
    @Test
    void testVerPerfilInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            perfilService.verPerfil(0L);
        });
    }

    /**
     * Prueba para eliminar un Perfil.
     */
    @Test
    void testEliminarPerfil() {
        PerfilEntity entity = perfilList.get(1);
        perfilService.eliminarPerfil(entity.getId());
        PerfilEntity deleted = entityManager.find(PerfilEntity.class, entity.getId());
        assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Perfil que no existe.
     */
    @Test
    void testEliminarPerfilInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            perfilService.eliminarPerfil(0L);
        });
    }
}
