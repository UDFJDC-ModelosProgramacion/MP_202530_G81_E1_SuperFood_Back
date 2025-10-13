package co.edu.udistrital.mdp.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.ServicioEntity;
import co.edu.udistrital.mdp.back.repositories.ServicioRepository;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;
    
    //Obtener servicio por id
    public ServicioEntity obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    //Crear servicio
    public ServicioEntity crearServicio(ServicioEntity servicio) {
        return servicioRepository.save(servicio);
    }
    //Actualizar servicio
    public ServicioEntity actualizarServicio(Long id, ServicioEntity servicioActualizado) {
        ServicioEntity servicio = servicioRepository.findById(id).orElse(null);
        if (servicio != null) {
            servicio.setNombre(servicioActualizado.getNombre());
            servicio.setDescripcion(servicioActualizado.getDescripcion());
            servicio.setCosto(servicioActualizado.getCosto());
            return servicioRepository.save(servicio);
        }
        return null;
    }
    //Eliminar servicio
    public boolean eliminarServicio(Long id) {
        ServicioEntity servicio = servicioRepository.findById(id).orElse(null);
        if (servicio != null) {
            servicioRepository.delete(servicio);
            return true;
        }
        return false;
    }
    //Obtener todos los servicios
    public java.util.List<ServicioEntity> obtenerServicios() {
        return servicioRepository.findAll();
    }
}
