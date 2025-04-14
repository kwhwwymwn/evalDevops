package edu.esiea.quizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.esiea.quizz.models.Response;

public interface ResponseRepository extends JpaRepository<Response, Integer> {

}
