package edu.lucas.examenrest.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.lucas.examenrest.Model.DTO.PeliculaDetalleDTO;
import edu.lucas.examenrest.Model.DTO.PeliculaListadoDTO;
import edu.lucas.examenrest.Model.DTO.PeliculaRequestDTO;
import edu.lucas.examenrest.Model.Entities.Pelicula;
import edu.lucas.examenrest.Model.Repositories.IPeliculaRepository;

@Service
public class PeliculaServiceImpl implements IPeliculaService {

    private final IPeliculaRepository peliculaRepository;

    public PeliculaServiceImpl(IPeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public PeliculaDetalleDTO create(PeliculaRequestDTO dto) {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(dto.titulo());
        pelicula.setDirector(dto.director());
        pelicula.setAnio(dto.anio());
        pelicula.setDuracion(dto.duracion());

        Pelicula guardada = peliculaRepository.save(pelicula);
        return new PeliculaDetalleDTO(guardada.getId(), guardada.getTitulo(), guardada.getDirector(),
                guardada.getAnio(), guardada.getDuracion(), List.of());
    }

    @Override 
    public List<PeliculaListadoDTO> findAll() {
        return peliculaRepository.findAll().stream().map(pelicula -> new PeliculaListadoDTO(pelicula.getId(), pelicula.getTitulo(),
                        pelicula.getDirector(), pelicula.getAnio())).toList();
    }
    @Override 
    public PeliculaDetalleDTO 

}
