package co.edu.udistrital.mdp.back.services;
import java.util.List;

import co.edu.udistrital.mdp.back.entities.*;
import co.edu.udistrital.mdp.back.exceptions.*;
import co.edu.udistrital.mdp.back.repositories.FotoRepository;

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
import co.edu.udistrital.mdp.back.entities.FotoEntity;


@DataJpaTest
@Transactional
@Import(FotoService.class)
public class FotoServiceTest {

    @Autowired
    FotoRepository fotoRepository;
    @Autowired
    private FotoService fotoService;
    
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private List<FotoEntity> fotoList = new ArrayList<>();

    private List<ServicioEntity> servicioList = new ArrayList<>();
    private List<RecetaEntity> recetaList = new ArrayList<>();
    private List<RestauranteEntity> restauranteList = new ArrayList<>();
    private List<IngredienteEntity> ingredienteList = new ArrayList<>();
    private List<PerfilEntity> perfilList = new ArrayList<>();
    //private List<UbicacionRestauranteEntity> ubicacionRestauranteList = new ArrayList<>();
    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from FotoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from RecetaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from RestauranteEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from IngredienteEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from PerfilEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from UbicacionRestauranteEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicio);
            servicioList.add(servicio);
        }
        for (int i = 0; i < 3; i++) {
            RecetaEntity receta = factory.manufacturePojo(RecetaEntity.class);
            entityManager.persist(receta);
            recetaList.add(receta);
        }
        for (int i = 0; i < 3; i++) {
            RestauranteEntity restaurante = factory.manufacturePojo(RestauranteEntity.class);
            entityManager.persist(restaurante);
            restauranteList.add(restaurante);
        }
        for (int i = 0; i < 3; i++) {
            IngredienteEntity ingrediente = factory.manufacturePojo(IngredienteEntity.class);
            entityManager.persist(ingrediente);
            ingredienteList.add(ingrediente);
        }
        for (int i = 0; i < 3; i++) {
            PerfilEntity perfil = factory.manufacturePojo(PerfilEntity.class);
            entityManager.persist(perfil);
            perfilList.add(perfil);
        }
        for (int i = 0; i < 3; i++) {
            UbicacionRestauranteEntity ubicacionRestaurante = factory.manufacturePojo(UbicacionRestauranteEntity.class);
            entityManager.persist(ubicacionRestaurante);
            //ubicacionRestauranteList.add(ubicacionRestaurante);
        }
        for (int i = 0; i < 3; i++) {
            FotoEntity foto = factory.manufacturePojo(FotoEntity.class);
            foto.setServicio(servicioList.get(0));
            foto.setReceta(recetaList.get(0));
            foto.setRestaurante(restauranteList.get(0));
            foto.setIngrediente(ingredienteList.get(0));
            foto.setPerfil(perfilList.get(0));
            //foto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            entityManager.persist(foto);
            fotoList.add(foto);
        }
    }
    //crear una foto
    @Transactional
    private FotoEntity crearFoto(FotoEntity foto)throws IllegalOperationException {
        if (foto.getEnlace() == null || foto.getEnlace().isEmpty()) {
            throw new IllegalOperationException("El enlace de la foto no puede ser nulo o vacío");
        }
        if (foto.getIngrediente() == null || foto.getReceta() == null || foto.getRestaurante() == null || foto.getServicio() == null || foto.getPerfil() == null /*|| foto.getUbicacionRestaurante() == null*/) {
            throw new IllegalOperationException("La foto debe estar asociada a un ingrediente, receta, restaurante, servicio y perfil");
        }
        return fotoRepository.save(foto);
    }
    //obtener todas las fotos
    @Transactional
    public List<FotoEntity> obtenerFotoPorIds() {
        return fotoRepository.findAll();
    }
    //obtener una foto por id
    @Transactional
    public FotoEntity obtenerFotoPorId(Long id) throws EntityNotFoundException,IllegalOperationException {
        if (id == null || id <= 0) {
            throw new IllegalOperationException("El id de la foto no puede ser nulo o negativo");
        }
        if (fotoRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("No se encontró la foto con el id dado");
        }
        return fotoRepository.findById(id).orElse(null);
    }
    //eliminar una foto
    @Transactional
    public void eliminarFoto(Long id) throws EntityNotFoundException,IllegalOperationException {
        if (id == null || id <= 0) {
            throw new IllegalOperationException("El id de la foto no puede ser nulo o negativo");
        }
        if (fotoRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("No se encontró la foto con el id dado");
        }
        fotoRepository.deleteById(id);
    }
    //actualizar una foto
    @Transactional
    public FotoEntity actualizarFoto(Long id, FotoEntity foto) throws EntityNotFoundException,IllegalOperationException {
        if (id == null || id <= 0) {
            throw new IllegalOperationException("El id de la foto no puede ser nulo o negativo");
        }
        if (fotoRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("No se encontró la foto con el id dado");
        }
        if (foto.getEnlace() == null || foto.getEnlace().isEmpty()) {
            throw new IllegalOperationException("El enlace de la foto no puede ser nulo o vacío");
        }
        return fotoRepository.save(foto);
    }
    //pruebas unitarias para crear una foto
    @Test
    void testcrearFoto() throws IllegalOperationException {
        FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
        newFoto.setServicio(servicioList.get(0));
        newFoto.setReceta(recetaList.get(0));
        newFoto.setRestaurante(restauranteList.get(0));
        newFoto.setIngrediente(ingredienteList.get(0));
        newFoto.setPerfil(perfilList.get(0));
        //newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
        FotoEntity result = fotoService.crearFoto(newFoto);
        assertNotNull(result);
        FotoEntity entity = entityManager.find(FotoEntity.class, result.getId());
        assertEquals(newFoto.getEnlace(), entity.getEnlace());
        assertEquals(newFoto.getNombre(), entity.getNombre());
        assertEquals(newFoto.getServicio(), entity.getServicio());
        assertEquals(newFoto.getReceta(), entity.getReceta());
        assertEquals(newFoto.getRestaurante(), entity.getRestaurante());
        assertEquals(newFoto.getIngrediente(), entity.getIngrediente());
        assertEquals(newFoto.getPerfil(), entity.getPerfil());
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testcrearFotoConEnlaceNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setEnlace(null);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            //newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.crearFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testcrearFotoConEnlaceVacio() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setEnlace("");
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            //newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.crearFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testcrearFotoSinServicio() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(null);
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            //newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.crearFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testcrearFotoSinReceta() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(null);
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            //newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.crearFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testcrearFotoSinRestaurante() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(null);
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            //newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.crearFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testcrearFotoSinIngrediente() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(null);
            newFoto.setPerfil(perfilList.get(0));
            //newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.crearFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testcrearFotoSinPerfil() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(null);
            //newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.crearFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testcrearFotoSinUbicacionRestaurante() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            //newFoto.setUbicacionRestaurante(null);
            fotoService.crearFoto(newFoto);
        }); 
    }
    //pruebas unitarias para obtener todas las fotos
    @Test
    void testobtenerFotoPorIds() {
        List<FotoEntity> list = fotoService.obtenerTodasLasFotos();
        assertEquals(fotoList.size(), list.size());
        for (FotoEntity entity : list) {
            boolean found = false;
            for (FotoEntity storedEntity : fotoList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
    //pruebas unitarias para obtener una foto por id
    @Test
    void testobtenerFotoPorId() throws EntityNotFoundException, IllegalOperationException {
        FotoEntity entity = fotoList.get(0);
        FotoEntity resultEntity = fotoService.obtenerFotoPorId(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getEnlace(), resultEntity.getEnlace());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getServicio(), resultEntity.getServicio());
        assertEquals(entity.getReceta(), resultEntity.getReceta());
        assertEquals(entity.getRestaurante(), resultEntity.getRestaurante());
        assertEquals(entity.getIngrediente(), resultEntity.getIngrediente());
        assertEquals(entity.getPerfil(), resultEntity.getPerfil());
        //assertEquals(entity.getUbicacionRestaurante(), resultEntity.getUbicacionRestaurante());
    }
    //pruebas unitarias para obtener una foto por id inexistente
    @Test
    void testobtenerFotoPorIdInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            fotoService.obtenerFotoPorId(0L);
        });
    }
    //pruebas unitarias para obtener una foto por id con error
    @Test
    void testobtenerFotoPorIdConIdNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            fotoService.obtenerFotoPorId(null);
        });
    }
    
    //pruebas unitarias para actualizar una foto
    @Test
    void testactualizarFoto() throws EntityNotFoundException, IllegalOperationException {
        FotoEntity entity = fotoList.get(0);
        FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
        newEntity.setId(entity.getId());
        newEntity.setServicio(servicioList.get(0));
        newEntity.setReceta(recetaList.get(0));
        newEntity.setRestaurante(restauranteList.get(0));
        newEntity.setIngrediente(ingredienteList.get(0));
        newEntity.setPerfil(perfilList.get(0));
        //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
        
        fotoService.actualizarFoto(entity.getId(), newEntity);
        FotoEntity resp = entityManager.find(FotoEntity.class, entity.getId());
        
        assertEquals(newEntity.getEnlace(), resp.getEnlace());
        assertEquals(newEntity.getNombre(), resp.getNombre());
        assertEquals(newEntity.getServicio(), resp.getServicio());
        assertEquals(newEntity.getReceta(), resp.getReceta());
        assertEquals(newEntity.getRestaurante(), resp.getRestaurante());
        assertEquals(newEntity.getIngrediente(), resp.getIngrediente());
        assertEquals(newEntity.getPerfil(), resp.getPerfil());
        //assertEquals(newEntity.getUbicacionRestaurante(), resp.getUbicacionRestaurante());
    }
    //pruebas unitarias para actualizar una foto inexistente
    @Test
    void testactualizarFotoInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.actualizarFoto(0L, newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testactualizarFotoConIdNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.actualizarFoto(null, newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testactualizarFotoConEnlaceNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setEnlace(null);
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.actualizarFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testactualizarFotoConEnlaceVacio() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setEnlace("");
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.actualizarFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testactualizarFotoSinServicio() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(null);
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.actualizarFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testactualizarFotoSinReceta() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(null);
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.actualizarFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test   
    void testactualizarFotoSinRestaurante() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(null);
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.actualizarFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testactualizarFotoSinIngrediente() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(null);
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.actualizarFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test   
    void testactualizarFotoSinPerfil() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(null);
            //newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.actualizarFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testactualizarFotoSinUbicacionRestaurante() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            //newEntity.setUbicacionRestaurante(null);            
            fotoService.actualizarFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para eliminar una foto
    @Test
    void testeliminarFoto() throws EntityNotFoundException, IllegalOperationException {
        FotoEntity entity = fotoList.get(0);
        fotoService.eliminarFoto(entity.getId());
        FotoEntity deletedEntity = entityManager.find(FotoEntity.class, entity.getId());
        assertNull(deletedEntity);
    }
    //pruebas unitarias para eliminar una foto inexistente
    @Test
    void testeliminarFotoInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            fotoService.eliminarFoto(0L);
        });
    }
    //pruebas unitarias para eliminar una foto con error
    @Test
    void testeliminarFotoConIdNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            fotoService.eliminarFoto(null);
        });
    }
}
