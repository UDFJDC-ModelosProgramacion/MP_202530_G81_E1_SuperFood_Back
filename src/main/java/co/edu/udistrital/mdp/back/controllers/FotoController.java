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

import co.edu.udistrital.mdp.back.dto.FotoDTO;
import co.edu.udistrital.mdp.back.entities.FotoEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.FotoService;

@RestController
@RequestMapping("/Photos")
public class FotoController {
    @Autowired
    FotoService fotoService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<FotoDTO> findAll (){
        List <FotoEntity> fotoEntities = fotoService.obtenerTodasLasFotos();
        return modelMapper.map(fotoEntities, new TypeToken<List<FotoDTO>>(){}.getType());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public FotoDTO create(@RequestBody FotoDTO fotoDTO) throws EntityNotFoundException, IllegalOperationException{
        FotoEntity fotoEntity = fotoService.crearFoto(modelMapper.map(fotoDTO, FotoEntity.class));
        return modelMapper.map(fotoEntity, FotoDTO.class);
    }
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public FotoDTO update(@PathVariable Long id,@RequestBody FotoDTO fotoDTO) throws IllegalOperationException,EntityNotFoundException{
        FotoEntity fotoEntity = fotoService.actualizarFoto(id,modelMapper.map(fotoDTO, FotoEntity.class));
        return modelMapper.map(fotoEntity, FotoDTO.class);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException,IllegalOperationException{
        fotoService.eliminarFoto(id);
    }
}
