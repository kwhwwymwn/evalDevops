package edu.esiea.quizz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.esiea.quizz.models.Response;
import edu.esiea.quizz.repository.ResponseRepository;

@Service
public class ResponseService {

	@Autowired
	private final ResponseRepository repo;
	
	public ResponseService(ResponseRepository repo) {
		this.repo = repo;
	}
	
	public Response create(String response, boolean isTrue) {
		Response r = new Response();
		r.setResponse(response);
		r.setTrue(isTrue);
		return repo.save(r);
	}
}
