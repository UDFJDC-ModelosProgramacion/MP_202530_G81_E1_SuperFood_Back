
package co.edu.udistrital.mdp.back.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.udistrital.mdp.back.entities.EventoEntity;
import co.edu.udistrital.mdp.back.entities.UsuarioEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de lógica de Evento
 */
@DataJpaTest
@Transactional
@Import(EventoService.class)
public class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EventoEntity> eventoList = new ArrayList<>();
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
        entityManager.getEntityManager().createQuery("delete from EventoEntity").executeUpdate();
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

        // Crear eventos
        for (int i = 0; i < 3; i++) {
            EventoEntity eventoEntity = factory.manufacturePojo(EventoEntity.class);
            eventoEntity.setFecha(LocalDateTime.now().plusDays(i + 1)); // Fechas futuras
            eventoEntity.setUsuarios(new ArrayList<>()); // Inicializar lista vacía
            entityManager.persist(eventoEntity);
            eventoList.add(eventoEntity);
        }

        // Agregar usuarios al primer evento para probar eliminación con usuarios inscritos
        eventoList.get(0).getUsuarios().add(usuarioList.get(0));
        usuarioList.get(0).getEventos().add(eventoList.get(0));
        
        entityManager.flush(); // Asegurar persistencia de datos
    }

    /**
     * Prueba para crear un Evento.
     */
    @Test
    void testCrearEvento() {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setFecha(LocalDateTime.now().plusDays(1)); // Fecha futura
        newEntity.setUsuarios(new ArrayList<>());

        EventoEntity result = eventoService.crearEvento(newEntity);
        assertNotNull(result);

        EventoEntity entity = entityManager.find(EventoEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEntity.getUbicacion(), entity.getUbicacion());
        assertEquals(newEntity.getFecha(), entity.getFecha());
    }

        /**
     * Prueba para crear un Evento con el mismo nombre y fecha de un Evento que ya existe.
     */
    @Test
    void testCrearEventoDuplicado() {
        // Crear un evento con datos específicos
        String nombreEvento = "Evento Test Duplicado";
        LocalDateTime fechaEvento = LocalDateTime.of(2025, 12, 25, 15, 30);
        
        EventoEntity primerEvento = new EventoEntity();
        primerEvento.setNombre(nombreEvento);
        primerEvento.setFecha(fechaEvento);
        primerEvento.setDescripcion("Primera descripción");
        primerEvento.setUbicacion("Primera ubicación");
        primerEvento.setUsuarios(new ArrayList<>());
        
        // Crear el primer evento
        eventoService.crearEvento(primerEvento);
        
        // Intentar crear otro con exactamente el mismo nombre y fecha
        assertThrows(IllegalStateException.class, () -> {
            EventoEntity duplicado = new EventoEntity();
            duplicado.setNombre(nombreEvento);
            duplicado.setFecha(fechaEvento);
            duplicado.setDescripcion("Segunda descripción");
            duplicado.setUbicacion("Segunda ubicación");
            duplicado.setUsuarios(new ArrayList<>());

            eventoService.crearEvento(duplicado);
        });
    }

    /**
     * Prueba para crear un Evento con fecha pasada.
     */
    @Test
    void testCrearEventoFechaPasada() {
        assertThrows(IllegalArgumentException.class, () -> {
            EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
            newEntity.setFecha(LocalDateTime.now().minusDays(1)); // Fecha pasada
            newEntity.setUsuarios(new ArrayList<>());

            eventoService.crearEvento(newEntity);
        });
    }

    /**
     * Prueba para crear un Evento sin nombre.
     */
    @Test
    void testCrearEventoSinNombre() {
        assertThrows(IllegalArgumentException.class, () -> {
            EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
            newEntity.setNombre(""); // Nombre vacío
            newEntity.setFecha(LocalDateTime.now().plusDays(1));
            newEntity.setUsuarios(new ArrayList<>());

            eventoService.crearEvento(newEntity);
        });
    }

    /**
     * Prueba para crear un Evento sin ubicación.
     */
    @Test
    void testCrearEventoSinUbicacion() {
        assertThrows(IllegalArgumentException.class, () -> {
            EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
            newEntity.setUbicacion(""); // Ubicación vacía
            newEntity.setFecha(LocalDateTime.now().plusDays(1));
            newEntity.setUsuarios(new ArrayList<>());

            eventoService.crearEvento(newEntity);
        });
    }

    /**
     * Prueba para crear un Evento sin descripción.
     */
    @Test
    void testCrearEventoSinDescripcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
            newEntity.setDescripcion(""); // Descripción vacía
            newEntity.setFecha(LocalDateTime.now().plusDays(1));
            newEntity.setUsuarios(new ArrayList<>());

            eventoService.crearEvento(newEntity);
        });
    }

    /**
     * Prueba para actualizar un Evento.
     */
    @Test
    void testActualizarEvento() {
        EventoEntity entity = eventoList.get(0);
        EventoEntity datosActualizacion = factory.manufacturePojo(EventoEntity.class);
        datosActualizacion.setFecha(LocalDateTime.now().plusDays(5)); // Fecha futura

        EventoEntity result = eventoService.actualizarEvento(entity.getId(), datosActualizacion);
        
        assertNotNull(result);
        EventoEntity resp = entityManager.find(EventoEntity.class, entity.getId());
        assertEquals(datosActualizacion.getNombre(), resp.getNombre());
        assertEquals(datosActualizacion.getDescripcion(), resp.getDescripcion());
        assertEquals(datosActualizacion.getUbicacion(), resp.getUbicacion());
        assertEquals(datosActualizacion.getFecha(), resp.getFecha());
    }

    /**
     * Prueba para actualizar un Evento que no existe.
     */
    @Test
    void testActualizarEventoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            EventoEntity datosActualizacion = factory.manufacturePojo(EventoEntity.class);
            eventoService.actualizarEvento(0L, datosActualizacion);
        });
    }

    /**
     * Prueba para ver un Evento.
     */
    @Test
    void testVerEvento() {
        EventoEntity entity = eventoList.get(0);
        EventoEntity resultEntity = eventoService.verEvento(entity.getId());
        
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getUbicacion(), resultEntity.getUbicacion());
        assertEquals(entity.getFecha(), resultEntity.getFecha());
    }

    /**
     * Prueba para ver un Evento que no existe.
     */
    @Test
    void testVerEventoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventoService.verEvento(0L);
        });
    }

    /**
     * Prueba para buscar un Evento por nombre y fecha.
     */
    @Test
    void testBuscarPorNombreYFecha() {
        // Crear un evento con datos específicos
        String nombreEvento = "Evento Test Búsqueda";
        LocalDateTime fechaEvento = LocalDateTime.of(2025, 12, 31, 18, 0);
        
        EventoEntity eventoParaBuscar = new EventoEntity();
        eventoParaBuscar.setNombre(nombreEvento);
        eventoParaBuscar.setFecha(fechaEvento);
        eventoParaBuscar.setDescripcion("Descripción para búsqueda");
        eventoParaBuscar.setUbicacion("Ubicación para búsqueda");
        eventoParaBuscar.setUsuarios(new ArrayList<>());
        
        // Crear el evento
        EventoEntity eventoPersistido = eventoService.crearEvento(eventoParaBuscar);
        
        // Buscar el evento por nombre y fecha exactos
        EventoEntity resultEntity = eventoService.buscarPorNombreYFecha(nombreEvento, fechaEvento);
        
        assertNotNull(resultEntity);
        assertEquals(eventoPersistido.getId(), resultEntity.getId());
        assertEquals(nombreEvento, resultEntity.getNombre());
        assertEquals(fechaEvento, resultEntity.getFecha());
    }

    /**
     * Prueba para buscar un Evento que no existe por nombre y fecha.
     */
    @Test
    void testBuscarPorNombreYFechaInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventoService.buscarPorNombreYFecha("EventoNoExiste", LocalDateTime.now().plusDays(10));
        });
    }

    /**
     * Prueba para ver todos los eventos.
     */
    @Test
    void testVerTodosLosEventos() {
        List<EventoEntity> list = eventoService.verTodosLosEventos();
        assertEquals(eventoList.size(), list.size());
        for (EventoEntity entity : list) {
            boolean found = false;
            for (EventoEntity storedEntity : eventoList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    /**
     * Prueba para eliminar un Evento sin usuarios inscritos.
     */
    @Test
    void testEliminarEvento() {
        EventoEntity entity = eventoList.get(1); // Evento sin usuarios
        eventoService.eliminarEvento(entity.getId());
        EventoEntity deleted = entityManager.find(EventoEntity.class, entity.getId());
        assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Evento que no existe.
     */
    @Test
    void testEliminarEventoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventoService.eliminarEvento(0L);
        });
    }

    /**
     * Prueba para eliminar un Evento con usuarios inscritos.
     */
    @Test
    void testEliminarEventoConUsuarios() {
        assertThrows(IllegalStateException.class, () -> {
            EventoEntity entity = eventoList.get(0); // Evento con usuarios
            eventoService.eliminarEvento(entity.getId());
        });
    }
}
