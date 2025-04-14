package edu.esiea.quizz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.esiea.quizz.models.Question;
import edu.esiea.quizz.models.Quizz;
import edu.esiea.quizz.models.User;
import edu.esiea.quizz.repository.QuizzRepository;

@Service
public class QuizzService {
	
	@Autowired
	private final QuizzRepository repo;

	public QuizzService(QuizzRepository repo) {
		this.repo = repo;
	}
	
	public List<Quizz> getAll(){
		return repo.findAll();
	}
	
//	public List<Quizz> getAll(String theme){
//		return repo.findByTheme(theme);
//	}
	
	public Quizz get(int index) {
		return repo.findById(index).orElseThrow(()-> new RuntimeException("Quizz not found"));
	}
	
	public Quizz create(User user, String theme, List<Question> questions) {
		Quizz q = new Quizz();
		q.setCreator(user);
		q.setTheme(theme);
		q.setQuestions(questions);
		return repo.save(q);
	}
	
}
