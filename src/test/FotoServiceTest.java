import java.util.List;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import co.edu.udistrital.mdp.back.entities.FotoEntity;
import jakarta.transaction.Transactional;
import lombok.Data;

@DataJpaTest
@Transactional
@Import(FotoService.class)
public class FotoServiceTest {
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
    private List<UbicacionRestauranteEntity> ubicacionRestauranteList = new ArrayList<>();
    
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
            ubicacionRestauranteList.add(ubicacionRestaurante);
        }
        for (int i = 0; i < 3; i++) {
            FotoEntity foto = factory.manufacturePojo(FotoEntity.class);
            foto.setServicio(servicioList.get(0));
            foto.setReceta(recetaList.get(0));
            foto.setRestaurante(restauranteList.get(0));
            foto.setIngrediente(ingredienteList.get(0));
            foto.setPerfil(perfilList.get(0));
            foto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            entityManager.persist(foto);
            fotoList.add(foto);
        }
    }
    //crear una foto
    @Transactional
    private FotoEntity createFoto(FotoEntity foto) {
        log.info("Creando una nueva foto");
        if (foto.getEnlace() == null || foto.getEnlace().isEmpty()) {
            throw new IllegalOperationException("El enlace de la foto no puede ser nulo o vacío");
        }
        if (foto.getIngrediente() == null || foto.getReceta() == null || foto.getRestaurante() == null || foto.getServicio() == null || foto.getPerfil() == null || foto.getUbicacionRestaurante() == null) {
            throw new IllegalOperationException("La foto debe estar asociada a un ingrediente, receta, restaurante, servicio, perfil y ubicacionRestaurante");
        }
        return fotoRepository.save(foto);
    }
    //obtener todas las fotos
    @Transactional
    public List<FotoEntity> getFotos() {
        log.info("Consultando todas las fotos");
        return fotoRepository.findAll();
    }
    //obtener una foto por id
    @Transactional
    public FotoEntity getFoto(Long id) throws EntityNotFoundException {
        log.info("Consultando la foto con id = {}", id);
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
    public void deleteFoto(Long id) throws EntityNotFoundException {
        log.info("Eliminando la foto con id = {}", id);
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
    public FotoEntity updateFoto(Long id, FotoEntity foto) throws EntityNotFoundException {
        log.info("Actualizando la foto con id = {}", id);
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
    void testCreateFoto() throws IllegalOperationException {
        FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
        newFoto.setServicio(servicioList.get(0));
        newFoto.setReceta(recetaList.get(0));
        newFoto.setRestaurante(restauranteList.get(0));
        newFoto.setIngrediente(ingredienteList.get(0));
        newFoto.setPerfil(perfilList.get(0));
        newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
        FotoEntity result = fotoService.createFoto(newFoto);
        assertNotNull(result);
        FotoEntity entity = entityManager.find(FotoEntity.class, result.getId());
        assertEquals(newFoto.getEnlace(), entity.getEnlace());
        assertEquals(newFoto.getNombre(), entity.getNombre());
        assertEquals(newFoto.getServicio(), entity.getServicio());
        assertEquals(newFoto.getReceta(), entity.getReceta());
        assertEquals(newFoto.getRestaurante(), entity.getRestaurante());
        assertEquals(newFoto.getIngrediente(), entity.getIngrediente());
        assertEquals(newFoto.getPerfil(), entity.getPerfil());
        assertEquals(newFoto.getUbicacionRestaurante(), entity.getUbicacionRestaurante);
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testCreateFotoConEnlaceNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setEnlace(null);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.createFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testCreateFotoConEnlaceVacio() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setEnlace("");
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.createFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testCreateFotoSinServicio() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(null);
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.createFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testCreateFotoSinReceta() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(null);
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.createFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testCreateFotoSinRestaurante() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(null);
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.createFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testCreateFotoSinIngrediente() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(null);
            newFoto.setPerfil(perfilList.get(0));
            newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.createFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testCreateFotoSinPerfil() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(null);
            newFoto.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
            fotoService.createFoto(newFoto);
        });
    }
    //pruebas unitarias para crear una foto con errores
    @Test
    void testCreateFotoSinUbicacionRestaurante() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newFoto = factory.manufacturePojo(FotoEntity.class);
            newFoto.setServicio(servicioList.get(0));
            newFoto.setReceta(recetaList.get(0));
            newFoto.setRestaurante(restauranteList.get(0));
            newFoto.setIngrediente(ingredienteList.get(0));
            newFoto.setPerfil(perfilList.get(0));
            newFoto.setUbicacionRestaurante(null);
            fotoService.createFoto(newFoto);
        }); 
    }
    //pruebas unitarias para obtener todas las fotos
    @Test
    void testGetFotos() {
        List<FotoEntity> list = fotoService.getFotos();
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
    void testGetFoto() throws EntityNotFoundException, IllegalOperationException {
        FotoEntity entity = fotoList.get(0);
        FotoEntity resultEntity = fotoService.getFoto(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getEnlace(), resultEntity.getEnlace());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getServicio(), resultEntity.getServicio());
        assertEquals(entity.getReceta(), resultEntity.getReceta());
        assertEquals(entity.getRestaurante(), resultEntity.getRestaurante());
        assertEquals(entity.getIngrediente(), resultEntity.getIngrediente());
        assertEquals(entity.getPerfil(), resultEntity.getPerfil());
        assertEquals(entity.getUbicacionRestaurante(), resultEntity.getUbicacionRestaurante());
    }
    //pruebas unitarias para obtener una foto por id inexistente
    @Test
    void testGetFotoInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            fotoService.getFoto(0L);
        });
    }
    //pruebas unitarias para obtener una foto por id con error
    @Test
    void testGetFotoConIdNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            fotoService.getFoto(null);
        });
    }
    
    //pruebas unitarias para actualizar una foto
    @Test
    void testUpdateFoto() throws EntityNotFoundException, IllegalOperationException {
        FotoEntity entity = fotoList.get(0);
        FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
        newEntity.setId(entity.getId());
        newEntity.setServicio(servicioList.get(0));
        newEntity.setReceta(recetaList.get(0));
        newEntity.setRestaurante(restauranteList.get(0));
        newEntity.setIngrediente(ingredienteList.get(0));
        newEntity.setPerfil(perfilList.get(0));
        newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));
        
        fotoService.updateFoto(entity.getId(), newEntity);
        FotoEntity resp = entityManager.find(FotoEntity.class, entity.getId());
        
        assertEquals(newEntity.getEnlace(), resp.getEnlace());
        assertEquals(newEntity.getNombre(), resp.getNombre());
        assertEquals(newEntity.getServicio(), resp.getServicio());
        assertEquals(newEntity.getReceta(), resp.getReceta());
        assertEquals(newEntity.getRestaurante(), resp.getRestaurante());
        assertEquals(newEntity.getIngrediente(), resp.getIngrediente());
        assertEquals(newEntity.getPerfil(), resp.getPerfil());
        assertEquals(newEntity.getUbicacionRestaurante(), resp.getUbicacionRestaurante());
    }
    //pruebas unitarias para actualizar una foto inexistente
    @Test
    void testUpdateFotoInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(0L, newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testUpdateFotoConIdNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(null, newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testUpdateFotoConEnlaceNulo() {
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
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testUpdateFotoConEnlaceVacio() {
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
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testUpdateFotoSinServicio() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(null);
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testUpdateFotoSinReceta() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(null);
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test   
    void testUpdateFotoSinRestaurante() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(null);
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testUpdateFotoSinIngrediente() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(null);
            newEntity.setPerfil(perfilList.get(0));
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test   
    void testUpdateFotoSinPerfil() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(null);
            newEntity.setUbicacionRestaurante(ubicacionRestauranteList.get(0));            
            fotoService.updateFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar una foto con errores
    @Test
    void testUpdateFotoSinUbicacionRestaurante() {
        assertThrows(IllegalOperationException.class, ()->{
            FotoEntity entity = fotoList.get(0);
            FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
            newEntity.setId(entity.getId());
            newEntity.setServicio(servicioList.get(0));
            newEntity.setReceta(recetaList.get(0));
            newEntity.setRestaurante(restauranteList.get(0));
            newEntity.setIngrediente(ingredienteList.get(0));
            newEntity.setPerfil(perfilList.get(0));
            newEntity.setUbicacionRestaurante(null);            
            fotoService.updateFoto(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para eliminar una foto
    @Test
    void testDeleteFoto() throws EntityNotFoundException, IllegalOperationException {
        FotoEntity entity = fotoList.get(0);
        fotoService.deleteFoto(entity.getId());
        FotoEntity deletedEntity = entityManager.find(FotoEntity.class, entity.getId());
        assertNull(deletedEntity);
    }
    //pruebas unitarias para eliminar una foto inexistente
    @Test
    void testDeleteFotoInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            fotoService.deleteFoto(0L);
        });
    }
    //pruebas unitarias para eliminar una foto con error
    @Test
    void testDeleteFotoConIdNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            fotoService.deleteFoto(null);
        });
    }
}
