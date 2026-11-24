package edu.lucas.examenrest.Controllers;

import org.springframework.http.HttpStatus;
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

import edu.lucas.examenrest.Model.DTO.PeliculaDetalleDTO;
import edu.lucas.examenrest.Model.DTO.PeliculaRequestDTO;
import edu.lucas.examenrest.Services.IPeliculaService;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private final IPeliculaService peliculaService;

    public PeliculaController(IPeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PeliculaDetalleDTO create(@RequestBody PeliculaRequestDTO dto) {
        return peliculaService.create(dto);
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

    @GetMapping("/{id}")
    public PeliculaDetalleDTO findById(@PathVariable("id") Long id) {
        return peliculaService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        peliculaService.delete(id);
    }

}
