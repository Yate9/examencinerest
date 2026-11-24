package edu.lucas.examenrest.Services;

import java.util.List;

import edu.lucas.examenrest.dto.PeliculaDetalleDTO;
import edu.lucas.examenrest.dto.PeliculaListadoDTO;
import edu.lucas.examenrest.dto.PeliculaRequestDTO;

public interface IPeliculaService {
    PeliculaDetalleDTO create(PeliculaRequestDTO dto);

    List<PeliculaListadoDTO> findAll();

    PeliculaDetalleDTO findById(Long id);

    PeliculaDetalleDTO update(Long id, PeliculaRequestDTO dto);

    void delete(Long id);
}
