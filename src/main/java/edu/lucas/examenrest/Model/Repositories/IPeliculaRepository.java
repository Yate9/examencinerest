package edu.lucas.examenrest.Model.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.lucas.examenrest.Model.Entities.Pelicula;

public interface IPeliculaRepository extends JpaRepository<Pelicula, Long> {

}
