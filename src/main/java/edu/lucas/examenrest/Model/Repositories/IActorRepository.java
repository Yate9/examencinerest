package edu.lucas.examenrest.Model.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.lucas.examenrest.Model.Entities.Actor;

public interface IActorRepository extends JpaRepository<Actor, Long> {

}
