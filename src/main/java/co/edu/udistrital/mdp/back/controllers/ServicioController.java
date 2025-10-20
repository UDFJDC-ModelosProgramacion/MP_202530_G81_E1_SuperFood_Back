package co.edu.udistrital.mdp.back.controllers;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.back.dto.ServicioDTO;
import co.edu.udistrital.mdp.back.dto.ServicioDetailDTO;
import co.edu.udistrital.mdp.back.entities.ServicioEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.ServicioService;

@RestController

@RequestMapping("/servicios")
public class ServicioController {
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioDetailDTO> findAll(){
        List <ServicioEntity> servicioEntities = servicioService.obtenerServicios();
        return modelMapper.map(servicioEntities, new TypeToken<List<ServicioDetailDTO>>(){}.getType());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioDTO create(@RequestBody ServicioDTO servicioDTO) throws IllegalOperationException, EntityNotFoundException{
        ServicioEntity servicioEntity = servicioService.crearServicio(modelMapper.map(servicioDTO, ServicioEntity.class));

        return modelMapper.map(servicioEntity, ServicioDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioDTO update(@PathVariable Long id, @RequestBody ServicioDTO servicioDTO) throws EntityNotFoundException, IllegalOperationException{
        ServicioEntity servicioEntity = servicioService.actualizarServicio(id,modelMapper.map(servicioDTO,ServicioEntity.class));
        return modelMapper.map(servicioEntity, ServicioDTO.class); 
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id)throws EntityNotFoundException,IllegalOperationException{
        servicioService.eliminarServicio(id);
    }
}
