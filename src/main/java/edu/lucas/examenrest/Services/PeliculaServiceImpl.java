package edu.lucas.examenrest.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.lucas.examenrest.dto.PeliculaDetalleDTO;
import edu.lucas.examenrest.dto.PeliculaListadoDTO;
import edu.lucas.examenrest.dto.PeliculaRequestDTO;
import edu.lucas.examenrest.dto.RepartoDTO;
import edu.lucas.examenrest.Model.Entities.Pelicula;
import edu.lucas.examenrest.Model.Repositories.IPeliculaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PeliculaServiceImpl implements IPeliculaService {

    private final IPeliculaRepository peliculaRepository;

    public PeliculaServiceImpl(IPeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public PeliculaDetalleDTO create(PeliculaRequestDTO dto) {
        validarPelicula(null);
        Pelicula pelicula = new Pelicula();
        aplicarDatos(pelicula, dto);

        Pelicula guardada = guardarPelicula(pelicula);
        return new PeliculaDetalleDTO(guardada.getId(), guardada.getTitulo(), guardada.getDirector(),
                guardada.getAnio(), guardada.getDuracion(), List.of());
    }

    @Override
    public List<PeliculaListadoDTO> findAll() {
        return peliculaRepository.findAll().stream()
                .map(pelicula -> new PeliculaListadoDTO(pelicula.getId(), pelicula.getTitulo(),
                        pelicula.getDirector(), pelicula.getAnio()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PeliculaDetalleDTO findById(Long id) {
        return convertirADetalle(buscarPelicula(id));
    }

    private PeliculaDetalleDTO convertirADetalle(Pelicula pelicula) {
        List<RepartoDTO> reparto = pelicula.getRepartos().stream()
                .map(r -> new RepartoDTO(r.getActor().getId(), r.getActor().getNombre(),
                        r.getPersonaje(), r.getSalario()))
                .toList();
        return new PeliculaDetalleDTO(pelicula.getId(), pelicula.getTitulo(), pelicula.getDirector(),
                pelicula.getAnio(), pelicula.getDuracion(), reparto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        peliculaRepository.delete(buscarPelicula(id));
    }

    @Override
    @Transactional
    public PeliculaDetalleDTO update(Long id, PeliculaRequestDTO dto) {
        validarPelicula(dto);
        Pelicula pelicula = buscarPelicula(id);
        aplicarDatos(pelicula, dto);
        return convertirADetalle(guardarPelicula(pelicula));
    }

    private Pelicula guardarPelicula(Pelicula pelicula) {
        return peliculaRepository.saveAndFlush(pelicula);
    }

    private void validarPelicula(PeliculaRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos de la pelicula son obligatorios.");
        }
        if (dto.titulo() == null || dto.titulo().isBlank()) {
            throw new IllegalArgumentException("El titulo es obligatorio.");
        }
        if (dto.director() == null || dto.director().isBlank()) {
            throw new IllegalArgumentException("El director es obligatorio.");
        }
        if (dto.anio() < 1888) {
            throw new IllegalArgumentException("El año debe ser 1888 o posterior.");
        }
        if (dto.duracion() <= 0) {
            throw new IllegalArgumentException("La duracion debe mayor que 0.");
        }
    }

    private void aplicarDatos(Pelicula pelicula, PeliculaRequestDTO dto) {
        pelicula.setTitulo(dto.titulo());
        pelicula.setDirector(dto.director());
        pelicula.setAnio(dto.anio());
        pelicula.setDuracion(dto.duracion());
    }

    private Pelicula buscarPelicula(Long id) {
        return peliculaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la película con el id: " + id));
    }
}
