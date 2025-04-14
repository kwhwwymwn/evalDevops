package edu.esiea.quizz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.esiea.quizz.models.Question;
import edu.esiea.quizz.models.Response;
import edu.esiea.quizz.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	private final QuestionRepository repo;

	public QuestionService(QuestionRepository repo) {
		this.repo = repo;
	}
	
	public Question create(String question, List<Response> responses) {
		Question q = new Question();
		q.setQuestion(question);
		q.setResponses(responses);
		return repo.save(q);
	}
	
}
