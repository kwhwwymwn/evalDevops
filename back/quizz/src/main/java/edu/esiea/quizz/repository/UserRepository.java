package edu.esiea.quizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.esiea.quizz.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
