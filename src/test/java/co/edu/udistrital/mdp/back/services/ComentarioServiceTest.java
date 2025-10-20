package co.edu.udistrital.mdp.back.services;

import co.edu.udistrital.mdp.back.entities.ServicioEntity;
import co.edu.udistrital.mdp.back.entities.ChefProfesionalEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.repositories.ComentarioRepository;

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
    private ComentarioRepository comentarioRepository;
    
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
        entityManager.getEntityManager().createQuery("delete from ClienteEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ChefProfesionalEntity").executeUpdate();
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

    //Crear comentario
    @Transactional
    private ComentarioEntity crearComentario(ComentarioEntity comentario) throws IllegalOperationException {
        if(comentario.getCalificacion() < 1 || comentario.getCalificacion() > 5) {
            throw new IllegalOperationException("La calificacion debe estar entre 1 y 5");
        }
        if(comentario.getNombreUsuario() == null || comentario.getNombreUsuario().isEmpty()) {
            throw new IllegalOperationException("El nombre de usuario no puede ser nulo o vacio");
        }
        if (comentario.getServicio() == null || comentario.getReceta() == null ) {
            throw new IllegalOperationException("El comentario debe estar asociado a un servicio o a una receta");
        }
        return comentarioRepository.save(comentario);
    
    }
    //Obtener comentario por id
    public ComentarioEntity obtenerComentarioPorId(Long id) throws EntityNotFoundException {
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (comentario == null) {
            throw new EntityNotFoundException("El comentario con id " + id + " no existe");
        }
        return comentario;
    }
    //Actualizar comentario
    @Transactional
    public ComentarioEntity actualizarComentario(Long id, ComentarioEntity comentarioActualizado) throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (comentario == null) {
            throw new EntityNotFoundException("El comentario con id " + id + " no existe");
        }
        if(comentarioActualizado.getCalificacion() < 1 || comentarioActualizado.getCalificacion() > 5) {
            throw new IllegalOperationException("La calificacion debe estar entre 1 y 5");
        }
        if(comentarioActualizado.getNombreUsuario() == null || comentarioActualizado.getNombreUsuario().isEmpty()) {
            throw new IllegalOperationException("El nombre de usuario no puede ser nulo o vacio");
        }
        if (comentarioActualizado.getServicio() == null || comentarioActualizado.getReceta() == null ) {
            throw new IllegalOperationException("El comentario debe estar asociado a un servicio o a una receta");
        }
        comentario.setCalificacion(comentarioActualizado.getCalificacion());
        comentario.setNombreUsuario(comentarioActualizado.getNombreUsuario());
        return comentarioRepository.save(comentario);
    }
    //Eliminar comentario
    @Transactional
    public void eliminarComentario(Long id) throws EntityNotFoundException {
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (comentario == null) {
            throw new EntityNotFoundException("El comentario con id " + id + " no existe");
        }
        comentarioRepository.delete(comentario);
    }
    //Obtener todos los comentarios
    @Transactional
    public List<ComentarioEntity> obtenerComentarios() {
        return comentarioRepository.findAll();
    }
    //pruebas unitarias para crear un comentario
    @Test
    void testCreateComentario() throws IllegalOperationException {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setServicio(servicioList.get(0));
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
            comentarioService.crearComentario(newEntity);
        });
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(6);
            newEntity.setServicio(servicioList.get(0));
            comentarioService.crearComentario(newEntity);
        });
    }
    //pruebas unitarias para crear un comentario con errores
    @Test
    void testCreateComentarioConNombreUsuarioNuloOVacio() {
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setNombreUsuario(null);
            newEntity.setServicio(servicioList.get(0));
            comentarioService.crearComentario(newEntity);
        });
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setNombreUsuario("");
            newEntity.setServicio(servicioList.get(0));
            comentarioService.crearComentario(newEntity);
        });
    }
    //pruebas unitarias para crear un comentario con errores
    @Test
    void testCreateComentarioSinServicioOReceta() {
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setServicio(null);
            newEntity.setReceta(null);
            comentarioService.crearComentario(newEntity);
        });
    }
    //pruebas unitarias para actualizar un comentario
    @Test
    void testUpdateComentario() throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity entity = comentarioList.get(0);
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setServicio(servicioList.get(1));
        ComentarioEntity updatedEntity = comentarioService.actualizarComentario(entity.getId(), newEntity);
        assertNotNull(updatedEntity);
        ComentarioEntity entityDB = entityManager.find(ComentarioEntity.class, entity.getId());
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
    void testUpdateComentarioConCalificacionInvalida() {
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity entity = comentarioList.get(0);
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(0);
            newEntity.setServicio(servicioList.get(1));
            comentarioService.actualizarComentario(entity.getId(), newEntity);
        });
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity entity = comentarioList.get(0);
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setCalificacion(6);
            newEntity.setServicio(servicioList.get(1));
            comentarioService.actualizarComentario(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar un comentario con errores
    @Test
    void testUpdateComentarioConNombreUsuarioNuloOVacio() {
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity entity = comentarioList.get(0);
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setNombreUsuario(null);
            newEntity.setServicio(servicioList.get(1));
            comentarioService.actualizarComentario(entity.getId(), newEntity);
        });
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity entity = comentarioList.get(0);
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setNombreUsuario("");
            newEntity.setServicio(servicioList.get(1));
            comentarioService.actualizarComentario(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar un comentario con errores
    @Test
    void testUpdateComentarioSinServicioOReceta() {
        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity entity = comentarioList.get(0);
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            newEntity.setServicio(null);
            newEntity.setReceta(null);
            comentarioService.actualizarComentario(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para eliminar un comentario
    @Test
    void testDeleteComentario() throws EntityNotFoundException {
        ComentarioEntity entity = comentarioList.get(0);
        comentarioService.eliminarComentario(entity.getId());
        ComentarioEntity deletedEntity = entityManager.find(ComentarioEntity.class, entity.getId());
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
    void testGetComentarios() {
        List<ComentarioEntity> list = comentarioService.obtenerComentarios();
        assertEquals(comentarioList.size(), list.size());
        for (ComentarioEntity entity : list) {
            boolean found = false;
            for (ComentarioEntity storedEntity : comentarioList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
    //pruebas unitarias para obtener un comentario por id
    @Test
    void testGetComentarioPorId() throws EntityNotFoundException {
        ComentarioEntity entity = comentarioList.get(0);
        ComentarioEntity foundEntity = comentarioService.obtenerComentarioPorId(entity.getId());
        assertNotNull(foundEntity);
        assertEquals(entity.getCalificacion(), foundEntity.getCalificacion());
        assertEquals(entity.getNombreUsuario(), foundEntity.getNombreUsuario());
        assertEquals(entity.getServicio().getId(), foundEntity.getServicio().getId());
    }
    //pruebas unitarias para obtener un comentario por id inexistente
    @Test
    void testGetComentarioPorIdInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            comentarioService.obtenerComentarioPorId(0L);
        });
    }

}
