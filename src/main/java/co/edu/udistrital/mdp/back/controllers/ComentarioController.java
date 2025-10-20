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

import co.edu.udistrital.mdp.back.dto.ComentarioDTO;
import co.edu.udistrital.mdp.back.entities.ComentarioEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.services.ComentarioService;

@RestController
@RequestMapping("/Comments")
public class ComentarioController {
    
    @Autowired
    ComentarioService comentarioService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ComentarioDTO> findAll(){
        List <ComentarioEntity> comentarioEntities = comentarioService.obtenerComentarios();
        return modelMapper.map(comentarioEntities,new TypeToken<List<ComentarioDTO>>(){}.getType());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ComentarioDTO create(@RequestBody ComentarioDTO comentarioDTO) throws IllegalOperationException,EntityNotFoundException{
        ComentarioEntity comentarioEntity = comentarioService.crearComentario(modelMapper.map(comentarioDTO, ComentarioEntity.class));
        return modelMapper.map(comentarioEntity, ComentarioDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ComentarioDTO update(@PathVariable Long id,@RequestBody ComentarioDTO comentarioDTO) throws IllegalOperationException,EntityNotFoundException{
        ComentarioEntity comentarioEntity = comentarioService.actualizarComentario(id, modelMapper.map(comentarioDTO, ComentarioEntity.class));
        return modelMapper.map(comentarioEntity, ComentarioDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException{
        comentarioService.eliminarComentario(id);
    }
}
