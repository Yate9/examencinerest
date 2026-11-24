package edu.lucas.examenrest.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.lucas.examenrest.dto.PeliculaDetalleDTO;
import edu.lucas.examenrest.dto.PeliculaListadoDTO;
import edu.lucas.examenrest.dto.PeliculaRequestDTO;
import edu.lucas.examenrest.Services.IPeliculaService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private final IPeliculaService peliculaService;

    public PeliculaController(IPeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @PostMapping
    public ResponseEntity<PeliculaDetalleDTO> create(@RequestBody PeliculaRequestDTO dto) {
        PeliculaDetalleDTO creada = peliculaService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .pathSegment("{id}").buildAndExpand(creada.id()).toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @GetMapping
    public List<PeliculaListadoDTO> findAll() {
        return peliculaService.findAll();
    }

    @PutMapping("/{id}")
    public PeliculaDetalleDTO update(@PathVariable("id") Long id, @RequestBody PeliculaRequestDTO dto) {
        return peliculaService.update(id, dto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String errorDeValidacion(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String tituloDuplicado(IllegalStateException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String peliculaNoEncontrada(EntityNotFoundException ex) {
        return ex.getMessage();
    }

    @GetMapping("/{id}")
    public PeliculaDetalleDTO findById(@PathVariable("id") Long id) {
        return peliculaService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        peliculaService.delete(id);
    }

}
