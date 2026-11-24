package edu.lucas.examenrest.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.lucas.examenrest.Model.DTO.PeliculaDetalleDTO;
import edu.lucas.examenrest.Model.Entities.Pelicula;
import edu.lucas.examenrest.Services.IPeliculaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private final IPeliculaService peliculaService;

    public PeliculaController(IPeliculaService peliculaService) {
        PeliculaService = peliculaService;
    }

    @GetMapping("/{id}")
    public PeliculaDetalleDTO findById(@RequestParam Long id) {
        return peliculaService.findById(id);
    }

}
