package edu.lucas.examenrest.Services;

import java.util.List;

import edu.lucas.examenrest.Model.DTO.PeliculaDetalleDTO;
import edu.lucas.examenrest.Model.DTO.PeliculaListadoDTO;
import edu.lucas.examenrest.Model.DTO.PeliculaRequestDTO;

public interface IPeliculaService {
    PeliculaDetalleDTO create(PeliculaRequestDTO dto);

    List<PeliculaListadoDTO> findAll();

    PeliculaDetalleDTO findById(Long id);

    PeliculaDetalleDTO update(Long id, PeliculaRequestDTO dto);

    void delete(Long id);
}
