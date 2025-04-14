package edu.esiea.quizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.esiea.quizz.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
