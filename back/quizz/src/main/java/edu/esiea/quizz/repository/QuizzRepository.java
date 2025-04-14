package edu.esiea.quizz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.esiea.quizz.models.Quizz;

public interface QuizzRepository extends JpaRepository<Quizz, Integer> {
	public List<Quizz> findByTheme(String theme);
}
