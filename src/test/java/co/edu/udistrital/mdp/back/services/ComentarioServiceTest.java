package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.ServicioEntity;
import co.edu.udistrital.mdp.back.entities.ChefProfesionalEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;

import java.util.*;
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

import co.edu.udistrital.mdp.back.entities.ComentarioEntity;

@DataJpaTest
@Transactional
@Import(ComentarioService.class)
public class ComentarioServiceTest {

    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private List<ComentarioEntity> comentarioList = new ArrayList<>();

    private List<ServicioEntity> servicioList = new ArrayList<>();
    private List<ChefProfesionalEntity> chefList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ChefProfesionalEntity").executeUpdate();
        comentarioList.clear();
        servicioList.clear();
        chefList.clear();
    }
    
    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
            ChefProfesionalEntity chef = factory.manufacturePojo(ChefProfesionalEntity.class);
            entityManager.persist(chef);
            chefList.add(chef);
        }
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            servicio.setChefProfesional(chefList.get(i));
            entityManager.persist(servicio);
            servicioList.add(servicio);
        }
    }

    //pruebas unitarias para crear un comentario
    @Test
    void testCreateComentario() throws IllegalOperationException {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setCalificacion(4); // Establecer calificación válida
        newEntity.setServicio(servicioList.get(0));
        newEntity.setReceta(null); // Solo debe asociarse a servicio O receta, no ambos
        ComentarioEntity result = comentarioService.crearComentario(newEntity);
        assertNotNull(result);
        ComentarioEntity entity = entityManager.find(ComentarioEntity.class, result.getId());
        assertEquals(newEntity.getCalificacion(), entity.getCalificacion());
        assertEquals(newEntity.getNombreUsuario(), entity.getNombreUsuario());
        assertEquals(newEntity.getServicio().getId(), entity.getServicio().getId());
    }
    //pruebas unitarias para crear un comentario con errores
    @Test
    void testCreateComentarioConCalificacionInvalida() {
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(0);
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(null);
            comentarioService.crearComentario(newEntity);
        });
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(6);
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(null);
            comentarioService.crearComentario(newEntity);
        });
    }
    //pruebas unitarias para crear un comentario con errores
    @Test
    void testCreateComentarioConNombreUsuarioNuloOVacio() {
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(3); // Calificación válida
            newEntity.setNombreUsuario(null);
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(null);
            comentarioService.crearComentario(newEntity);
        });
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(3); // Calificación válida
            newEntity.setNombreUsuario("");
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(null);
            comentarioService.crearComentario(newEntity);
        });
    }
    //pruebas unitarias para crear un comentario con errores
    @Test
    void testCreateComentarioSinServicioOReceta() {
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(3); // Calificación válida
            newEntity.setServicio(null);
            newEntity.setReceta(null);
            comentarioService.crearComentario(newEntity);
        });
    }
    //pruebas unitarias para actualizar un comentario
    @Test
    void testUpdateComentario() throws EntityNotFoundException, IllegalOperationException {
        // Primero crear un comentario para actualizar
        ComentarioEntity existingEntity = factory.manufacturePojo(ComentarioEntity.class);
        existingEntity.setCalificacion(3);
        existingEntity.setServicio(servicioList.get(0));
        existingEntity.setReceta(null);
        existingEntity = comentarioService.crearComentario(existingEntity);
        
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setCalificacion(4); // Calificación válida
        newEntity.setServicio(servicioList.get(1));
        newEntity.setReceta(null);
        ComentarioEntity updatedEntity = comentarioService.actualizarComentario(existingEntity.getId(), newEntity);
        assertNotNull(updatedEntity);
        ComentarioEntity entityDB = entityManager.find(ComentarioEntity.class, existingEntity.getId());
        assertEquals(newEntity.getCalificacion(), entityDB.getCalificacion());
        assertEquals(newEntity.getNombreUsuario(), entityDB.getNombreUsuario());
        assertEquals(newEntity.getServicio().getId(), entityDB.getServicio().getId());
    }
    //pruebas unitarias para actualizar un comentario inexistente
    @Test
    void testUpdateComentarioInexistente() {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        assertThrows(EntityNotFoundException.class, ()->{
            comentarioService.actualizarComentario(0L, newEntity);
        });
    }
    //pruebas unitarias para actualizar un comentario con errores
    @Test
    void testUpdateComentarioConCalificacionInvalida() throws IllegalOperationException {
        // Crear un comentario primero
        ComentarioEntity existingEntity = factory.manufacturePojo(ComentarioEntity.class);
        existingEntity.setCalificacion(3);
        existingEntity.setServicio(servicioList.get(0));
        existingEntity.setReceta(null);
        existingEntity = comentarioService.crearComentario(existingEntity);
        
        Long entityId = existingEntity.getId();
        
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(0);
            newEntity.setServicio(servicioList.get(1));
            newEntity.setReceta(null);
            comentarioService.actualizarComentario(entityId, newEntity);
        });
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(6);
            newEntity.setServicio(servicioList.get(1));
            newEntity.setReceta(null);
            comentarioService.actualizarComentario(entityId, newEntity);
        });
    }
    //pruebas unitarias para actualizar un comentario con errores
    @Test
    void testUpdateComentarioConNombreUsuarioNuloOVacio() throws IllegalOperationException {
        // Crear un comentario primero
        ComentarioEntity existingEntity = factory.manufacturePojo(ComentarioEntity.class);
        existingEntity.setCalificacion(3);
        existingEntity.setServicio(servicioList.get(0));
        existingEntity.setReceta(null);
        existingEntity = comentarioService.crearComentario(existingEntity);
        
        Long entityId = existingEntity.getId();
        
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(3); // Calificación válida
            newEntity.setNombreUsuario(null);
            newEntity.setServicio(servicioList.get(1));
            newEntity.setReceta(null);
            comentarioService.actualizarComentario(entityId, newEntity);
        });
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(3); // Calificación válida
            newEntity.setNombreUsuario("");
            newEntity.setServicio(servicioList.get(1));
            newEntity.setReceta(null);
            comentarioService.actualizarComentario(entityId, newEntity);
        });
    }
    //pruebas unitarias para actualizar un comentario con errores
    @Test
    void testUpdateComentarioSinServicioOReceta() throws IllegalOperationException {
        // Crear un comentario primero
        ComentarioEntity existingEntity = factory.manufacturePojo(ComentarioEntity.class);
        existingEntity.setCalificacion(3);
        existingEntity.setServicio(servicioList.get(0));
        existingEntity.setReceta(null);
        final ComentarioEntity savedEntity = comentarioService.crearComentario(existingEntity);
        
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(3); // Calificación válida
            newEntity.setServicio(null);
            newEntity.setReceta(null);
            comentarioService.actualizarComentario(savedEntity.getId(), newEntity);
        });
    }
    //pruebas unitarias para eliminar un comentario
    @Test
    void testDeleteComentario() throws EntityNotFoundException, IllegalOperationException {
        // Crear un comentario primero
        ComentarioEntity existingEntity = factory.manufacturePojo(ComentarioEntity.class);
        existingEntity.setCalificacion(3);
        existingEntity.setServicio(servicioList.get(0));
        existingEntity.setReceta(null);
        existingEntity = comentarioService.crearComentario(existingEntity);
        
        comentarioService.eliminarComentario(existingEntity.getId());
        ComentarioEntity deletedEntity = entityManager.find(ComentarioEntity.class, existingEntity.getId());
        assertNull(deletedEntity);
    }
    //pruebas unitarias para eliminar un comentario inexistente
    @Test
    void testDeleteComentarioInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            comentarioService.eliminarComentario(0L);
        });
    }
    //pruebas unitarias para obtener todos los comentarios
    @Test
    void testGetComentarios() throws IllegalOperationException {
        // Crear algunos comentarios primero
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            entity.setCalificacion(3 + i);
            entity.setServicio(servicioList.get(i));
            entity.setReceta(null);
            comentarioService.crearComentario(entity);
        }
        
        List<ComentarioEntity> list = comentarioService.obtenerComentarios();
        assertEquals(3, list.size());
    }
    //pruebas unitarias para obtener un comentario por id
    @Test
    void testGetComentarioPorId() throws EntityNotFoundException, IllegalOperationException {
        // Crear un comentario primero
        ComentarioEntity existingEntity = factory.manufacturePojo(ComentarioEntity.class);
        existingEntity.setCalificacion(3);
        existingEntity.setServicio(servicioList.get(0));
        existingEntity.setReceta(null);
        existingEntity = comentarioService.crearComentario(existingEntity);
        
        ComentarioEntity foundEntity = comentarioService.obtenerComentarioPorId(existingEntity.getId());
        assertNotNull(foundEntity);
        assertEquals(existingEntity.getCalificacion(), foundEntity.getCalificacion());
        assertEquals(existingEntity.getNombreUsuario(), foundEntity.getNombreUsuario());
        assertEquals(existingEntity.getServicio().getId(), foundEntity.getServicio().getId());
    }
    //pruebas unitarias para obtener un comentario por id inexistente
    @Test
    void testGetComentarioPorIdInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            comentarioService.obtenerComentarioPorId(0L);
        });
    }

}
