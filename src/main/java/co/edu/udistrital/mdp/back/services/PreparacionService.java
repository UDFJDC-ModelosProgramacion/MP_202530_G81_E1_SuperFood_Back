package co.edu.udistrital.mdp.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.PreparacionEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.repositories.PreparacionRepository;

@Service
public class PreparacionService {

    @Autowired
    private PreparacionRepository preparacionRepository;

    // Crear o modificar una preparación
    public PreparacionEntity guardarPreparacion(PreparacionEntity preparacion) throws IllegalOperationException {
        validarDatos(preparacion);

        List<PreparacionEntity> existentes = preparacionRepository.findByNombre(preparacion.getNombre());

        if (!existentes.isEmpty()) {

            PreparacionEntity existente = existentes.get(0);
            existente.setPasos(preparacion.getPasos());
            return preparacionRepository.save(existente);
        }

        // Si no existe, se crea
        return preparacionRepository.save(preparacion);
    }

    // Consultar todas las preparaciones
    public List<PreparacionEntity> consultarTodas() throws EntityNotFoundException {
        List<PreparacionEntity> lista = preparacionRepository.findAll();

        if (lista.isEmpty()) {
            throw new EntityNotFoundException("No hay preparaciones registradas");
        }

        return lista;
    }

    // Consultar por nombre
    public List<PreparacionEntity> consultarPorNombre(String nombre) throws EntityNotFoundException {
        List<PreparacionEntity> lista = preparacionRepository.findByNombre(nombre);

        if (lista.isEmpty()) {
            throw new EntityNotFoundException("No se encontró ninguna preparación con nombre: " + nombre);
        }

        return lista;
    }

    // Validaciones de nombre y datos definidos
    private void validarDatos(PreparacionEntity preparacion) throws IllegalOperationException{
        if (preparacion.getNombre() == null || preparacion.getNombre().trim().isEmpty()) {
            throw new IllegalOperationException("La preparación debe tener un nombre");
        }

        if (preparacion.getPasos() == null || preparacion.getPasos().trim().isEmpty()) {
            throw new IllegalOperationException("La preparación debe tener pasos definidos");
        }
    }
}
