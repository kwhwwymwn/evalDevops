package edu.esiea.quizz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.esiea.quizz.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private final UserRepository repo;
	
	public UserService(UserRepository repo) {
		this.repo = repo;
	}
}
