package co.edu.udistrital.mdp.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.ServicioEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.repositories.ServicioRepository;
import co.edu.udistrital.mdp.back.repositories.ChefProfesionalRepository;
import co.edu.udistrital.mdp.back.repositories.ComentarioRepository;
import co.edu.udistrital.mdp.back.repositories.FotoRepository;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private ChefProfesionalRepository chefProfesionalRepository;
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private FotoRepository fotoRepository;
    
    //Obtener servicio por id
    public ServicioEntity obtenerServicioPorId(Long id) throws EntityNotFoundException {
        ServicioEntity servicio = servicioRepository.findById(id).orElse(null);
        if (servicio == null) {
            throw new EntityNotFoundException("Servicio no encontrado con id: " + id);
        }
        return servicio;
    }

    //Crear servicio
    public ServicioEntity crearServicio(ServicioEntity servicio) throws IllegalOperationException, EntityNotFoundException {
        // Validar que tenga chef profesional
        if (servicio.getChefProfesional() == null) {
            throw new IllegalOperationException("El servicio debe tener un chef profesional asociado");
        }
        
        // Validar que el chef profesional existe
        if (!chefProfesionalRepository.existsById(servicio.getChefProfesional().getId())) {
            throw new EntityNotFoundException("Chef profesional no encontrado con id: " + servicio.getChefProfesional().getId());
        }
        
        // Validar costo
        if (servicio.getCosto() == null) {
            throw new IllegalOperationException("El costo del servicio no puede ser nulo");
        }
        
        if (servicio.getCosto() <= 0) {
            throw new IllegalOperationException("El costo del servicio debe ser mayor a cero");
        }
        
        return servicioRepository.save(servicio);
    }
    
    //Actualizar servicio
    public ServicioEntity actualizarServicio(Long id, ServicioEntity servicioActualizado) throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicioRepository.findById(id).orElse(null);
        if (servicio == null) {
            throw new EntityNotFoundException("Servicio no encontrado con id: " + id);
        }
        
        // Validar costo (no puede ser nulo)
        if (servicioActualizado.getCosto() == null) {
            throw new IllegalOperationException("El costo del servicio no puede ser nulo");
        }
        if (servicioActualizado.getCosto() <= 0) {
            throw new IllegalOperationException("El costo del servicio debe ser mayor a cero");
        }
        servicio.setCosto(servicioActualizado.getCosto());
        
        if (servicioActualizado.getNombre() != null) {
            servicio.setNombre(servicioActualizado.getNombre());
        }
        
        if (servicioActualizado.getDescripcion() != null) {
            servicio.setDescripcion(servicioActualizado.getDescripcion());
        }
        
        if (servicioActualizado.getCategoria() != null) {
            servicio.setCategoria(servicioActualizado.getCategoria());
        }
        
        if (servicioActualizado.getChefProfesional() != null) {
            // Validar que el chef profesional existe
            if (!chefProfesionalRepository.existsById(servicioActualizado.getChefProfesional().getId())) {
                throw new EntityNotFoundException("Chef profesional no encontrado con id: " + servicioActualizado.getChefProfesional().getId());
            }
            servicio.setChefProfesional(servicioActualizado.getChefProfesional());
        }
        
        return servicioRepository.save(servicio);
    }
    
    //Eliminar servicio
    public boolean eliminarServicio(Long id) throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicioRepository.findById(id).orElse(null);
        if (servicio == null) {
            throw new EntityNotFoundException("Servicio no encontrado con id: " + id);
        }
        
        // Validar que el servicio no tiene comentarios asociados usando el repositorio
        if (comentarioRepository.findByServicio(servicio).size() > 0) {
            throw new IllegalOperationException("No se puede eliminar el servicio porque tiene comentarios asociados");
        }
        
        // Validar que el servicio no tiene fotos asociadas usando el repositorio
        if (fotoRepository.findByServicio(servicio).size() > 0) {
            throw new IllegalOperationException("No se puede eliminar el servicio porque tiene fotos asociadas");
        }
        
        servicioRepository.delete(servicio);
        return true;
    }
    
    //Obtener todos los servicios
    public java.util.List<ServicioEntity> obtenerServicios() {
        return servicioRepository.findAll();
    }
}
