package edu.lucas.examenrest.Model.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.lucas.examenrest.Model.Entities.Pelicula;

public interface IPeliculaRepository extends JpaRepository<Pelicula, Long> {

    boolean existsByTituloIgnoreCase(String titulo);

    List<Pelicula> findByAnioGreaterThanOrderByAnioAsc(int anio);
}
