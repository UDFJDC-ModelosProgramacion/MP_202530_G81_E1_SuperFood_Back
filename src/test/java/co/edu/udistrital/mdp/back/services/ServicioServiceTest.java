package co.edu.udistrital.mdp.back.services;
import java.util.List;

import co.edu.udistrital.mdp.back.entities.*;
import co.edu.udistrital.mdp.back.exceptions.*;
import co.edu.udistrital.mdp.back.repositories.ServicioRepository;
import co.edu.udistrital.mdp.back.repositories.ChefProfesionalRepository;

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
import co.edu.udistrital.mdp.back.entities.ServicioEntity;

@DataJpaTest
@Transactional
@Import(ServicioService.class)
public class ServicioServiceTest {

    @Autowired
    private ChefProfesionalRepository chefProfesionalRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;   
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    private List<ServicioEntity> servicioList = new ArrayList<>();

    private List<ChefProfesionalEntity> chefList = new ArrayList<>();
    private List<FotoEntity> fotoList = new ArrayList<>();
    private List<ComentarioEntity> comentarioList = new ArrayList<>();
    private List<EventoEntity> eventoList = new ArrayList<>();
    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from FotoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EventoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
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
            servicio.setChefProfesional(chefList.get(0));
            entityManager.persist(servicio);
            servicioList.add(servicio);
        }
        for (int i = 0; i < 3; i++) {
            FotoEntity foto = factory.manufacturePojo(FotoEntity.class);
            foto.setServicio(servicioList.get(0));
            entityManager.persist(foto);
            fotoList.add(foto);
        }
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
            comentario.setServicio(servicioList.get(0));
            entityManager.persist(comentario);
            comentarioList.add(comentario);
        }
        for (int i = 0; i < 3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            //evento.setServicio(servicioList.get(0));
            entityManager.persist(evento);
            eventoList.add(evento);
        }
    }

    //Crear un servicio
    @Transactional
    private ServicioEntity createServicio(ServicioEntity servicio) throws EntityNotFoundException, IllegalOperationException {
        

        if(servicio.getChefProfesional() == null){
            throw new IllegalOperationException("El servicio debe estar asociado a un chef profesional");
        }
        Optional<ChefProfesionalEntity> chefEntity = chefProfesionalRepository.findById(servicio.getChefProfesional().getId());
        if(chefEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontro el chef profesional con el id dado");
        }
        if(servicio.getCosto() <= 0){
            throw new IllegalOperationException("El costo del servicio debe ser mayor a 0");
        }
        if(servicio.getCosto() == null){
            throw new IllegalOperationException("El costo del servicio no puede ser nulo");
        }
        return servicioRepository.save(servicio);
    }
    //Obtener todos los servicios 
    @Transactional
    private List<ServicioEntity> getServicios() {
        return servicioRepository.findAll();
    }
    //Obtener un servicio por id
    @Transactional
    private ServicioEntity getServicio(Long id) throws EntityNotFoundException {
        
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(id);
        if(servicioEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontro el servicio con el id dado");
        }
        return servicioEntity.get();
    }
    //Actualizar un servicio
    @Transactional
    private ServicioEntity updateServicio(Long id, ServicioEntity servicio) throws EntityNotFoundException, IllegalOperationException {
        
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(id);
        if(servicioEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontro el servicio con el id dado");
        }
        if(servicio.getCosto() <= 0){
            throw new IllegalOperationException("El costo del servicio debe ser mayor a 0");
        }
        if(servicio.getCosto() == null){
            throw new IllegalOperationException("El costo del servicio no puede ser nulo");
        }
        ServicioEntity newServicio = servicioEntity.get();
        newServicio.setNombre(servicio.getNombre());
        newServicio.setDescripcion(servicio.getDescripcion());
        newServicio.setCosto(servicio.getCosto());
        newServicio.setCategoria(servicio.getCategoria());
        return servicioRepository.save(newServicio);
    }
    //Eliminar un servicio
    @Transactional
    private void deleteServicio(Long id) throws EntityNotFoundException, IllegalOperationException {
        
        ServicioEntity servicioEntity = entityManager.find(ServicioEntity.class, id);
        Optional<ServicioEntity> servicio = servicioRepository.findById(id);
        if(servicio.isEmpty()){
            throw new EntityNotFoundException("No se encontro el servicio con el id dado");
        }
        ChefProfesionalEntity chef = servicioEntity.getChefProfesional();
        if(chef.equals(null) ){
            throw new IllegalOperationException("No se puede eliminar el servicio porque tiene chefs asociados");
        }
        servicioRepository.delete(servicioEntity);
        
    }
    //Prueba unitaria para crear un servicio
    @Test
    void testCreateServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
        newServicio.setChefProfesional(chefList.get(0));
        ServicioEntity result = servicioService.crearServicio(newServicio);
        assertNotNull(result);

        ServicioEntity entity = entityManager.find(ServicioEntity.class, result.getId());
        assertEquals(newServicio.getNombre(), entity.getNombre());
        assertEquals(newServicio.getDescripcion(), entity.getDescripcion());
        assertEquals(newServicio.getCosto(), entity.getCosto());
        assertEquals(newServicio.getCategoria(), entity.getCategoria());
        assertEquals(newServicio.getChefProfesional().getId(), entity.getChefProfesional().getId());
    }
    //pruebas unitarias para crear un servicio con errores
    @Test
    void testCreateServicioSinChef() {
        ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
        assertThrows(IllegalOperationException.class, ()->{
            servicioService.crearServicio(newServicio);
        });
    }
    //pruebas unitarias para crear un servicio con errores
    @Test
    void testCreateServicioConChefInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
            ChefProfesionalEntity chef = factory.manufacturePojo(ChefProfesionalEntity.class);
            chef.setId(0L);
            newServicio.setChefProfesional(chef);
            servicioService.crearServicio(newServicio);
        });
    }
    //pruebas unitarias para crear un servicio con errores
    @Test
    void testCreateServicioConCostoCero() {
        assertThrows(IllegalOperationException.class, ()->{
            ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
            newServicio.setChefProfesional(chefList.get(0));
            newServicio.setCosto(0.0);
            servicioService.crearServicio(newServicio);
        });
    }
    //pruebas unitarias para crear un servicio con errores
    @Test
    void testCreateServicioConCostoNegativo() {
        assertThrows(IllegalOperationException.class, ()->{
            ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
            newServicio.setChefProfesional(chefList.get(0));
            newServicio.setCosto(-50.0);
            servicioService.crearServicio(newServicio);
        });
    }
    @Test
    //pruebas unitarias para crear un servicio con errores
    void testCreateServicioConCostoNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
            newServicio.setChefProfesional(chefList.get(0));
            newServicio.setCosto(null);
            servicioService.crearServicio(newServicio);
        });
    }
    @Test
    //pruebas unitarias para obtener todos los servicios
    void testGetServicios() {
        List<ServicioEntity> list = servicioService.obtenerServicios();
        assertEquals(servicioList.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : servicioList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
    @Test
    //pruebas unitarias para obtener un servicio por id
    void testGetServicio() throws EntityNotFoundException {
        ServicioEntity entity = servicioList.get(0);
        ServicioEntity resultEntity = servicioService.obtenerServicioPorId(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getCosto(), resultEntity.getCosto());
        assertEquals(entity.getCategoria(), resultEntity.getCategoria());
        assertEquals(entity.getChefProfesional().getId(), resultEntity.getChefProfesional().getId());
    }
    //pruebas unitarias para obtener un servicio por id inexistente
    @Test
    void testGetServicioInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            servicioService.obtenerServicioPorId(0L);
        });
    }
    //pruebas unitarias para actualizar un servicio
    @Test
    void testUpdateServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(0);
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setChefProfesional(chefList.get(0));
        ServicioEntity result = servicioService.actualizarServicio(entity.getId(), newEntity);
        assertNotNull(result);

        ServicioEntity updatedEntity = entityManager.find(ServicioEntity.class, entity.getId());
        assertEquals(newEntity.getNombre(), updatedEntity.getNombre());
        assertEquals(newEntity.getDescripcion(), updatedEntity.getDescripcion());
        assertEquals(newEntity.getCosto(), updatedEntity.getCosto());
        assertEquals(newEntity.getCategoria(), updatedEntity.getCategoria());
        assertEquals(newEntity.getChefProfesional().getId(), updatedEntity.getChefProfesional().getId());
    }
    //pruebas unitarias para actualizar un servicio inexistente
    @Test
    void testUpdateServicioInexistente() {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setChefProfesional(chefList.get(0));
        assertThrows(EntityNotFoundException.class, ()->{
            servicioService.actualizarServicio(0L, newEntity);
        });
    }
    //pruebas unitarias para actualizar un servicio con errores
    @Test
    void testUpdateServicioConCostoCero() {
        assertThrows(IllegalOperationException.class, ()->{
            ServicioEntity entity = servicioList.get(0);
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setChefProfesional(chefList.get(0));
            newEntity.setCosto(0.0);
            servicioService.actualizarServicio(entity.getId(), newEntity);
        });
    }
    @Test
    //pruebas unitarias para actualizar un servicio con errores
    void testUpdateServicioConCostoNegativo() {
        assertThrows(IllegalOperationException.class, ()->{
            ServicioEntity entity = servicioList.get(0);
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setChefProfesional(chefList.get(0));
            newEntity.setCosto(-50.0);
            servicioService.actualizarServicio(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para actualizar un servicio con errores
    @Test
    void testUpdateServicioConCostoNulo() {
        assertThrows(IllegalOperationException.class, ()->{
            ServicioEntity entity = servicioList.get(0);
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setChefProfesional(chefList.get(0));
            newEntity.setCosto(null);
            servicioService.actualizarServicio(entity.getId(), newEntity);
        });
    }
    //pruebas unitarias para eliminar un servicio
    @Test
    void testDeleteServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(2); // Usar servicio sin comentarios ni fotos asociadas
        servicioService.eliminarServicio(entity.getId());
        ServicioEntity deletedEntity = entityManager.find(ServicioEntity.class, entity.getId());
        assertNull(deletedEntity);
    }
    //pruebas unitarias para eliminar un servicio inexistente
    @Test
    void testDeleteServicioInexistente() {
        assertThrows(EntityNotFoundException.class, ()->{
            servicioService.eliminarServicio(0L);
        });
    }
    //pruebas unitarias para eliminar un servicio con chefs asociados
    @Test
    void testDeleteServicioConChefsAsociados() {
        assertThrows(IllegalOperationException.class, ()->{
            ServicioEntity entity = servicioList.get(0);
            servicioService.eliminarServicio(entity.getId());
        });
    }
}
