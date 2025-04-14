package edu.esiea.quizz.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.esiea.quizz.models.Question;
import edu.esiea.quizz.models.Quizz;
import edu.esiea.quizz.models.Response;
import edu.esiea.quizz.services.QuestionService;
import edu.esiea.quizz.services.QuizzService;
import edu.esiea.quizz.services.ResponseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Quizz")
public class QuizzController {

    @Autowired
	private final QuizzService quizzService;
    @Autowired
	private final QuestionService questionService;
    @Autowired
	private final ResponseService responseService;
    
	public QuizzController(QuizzService quizzService, QuestionService questionService, ResponseService responseService) {
		this.quizzService = quizzService;
		this.questionService = questionService;
		this.responseService = responseService;
	}
	
	@GetMapping
	public ResponseEntity<List<Quizz>> getAll(){
		return ResponseEntity.ok(quizzService.getAll());
	}

//	@GetMapping("/{theme}")
//	public ResponseEntity<List<Quizz>> getAll(@PathVariable String theme){
//		List<Quizz> list = quizzService.getAll(theme);
//		if (list.size() == 0) {
//			ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(list);
//	}

	@GetMapping("/{id}")
	public ResponseEntity<Quizz> get(@PathVariable int id){
		try {
			return ResponseEntity.ok(quizzService.get(id));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
    
	@PostMapping
    public ResponseEntity<Quizz> create(@Valid @RequestBody Quizz quizz) {
		//verifications
		if (quizz.getQuestions().size() <3 || quizz.getQuestions().size() >10) {
			return ResponseEntity.badRequest().build();
		}
		for (Question q : quizz.getQuestions()) {
			if (q.getResponses().size() <3 || q.getResponses().size() >5) {
				return ResponseEntity.badRequest().build();
			}
			int goodResponses = 0;
			for(Response r : q.getResponses()) {
				if (r.isTrue()) {
					goodResponses++;
				}
			}
			if (goodResponses != 1) {
				return ResponseEntity.badRequest().build();
			}
		}
		//persistance
		List<Question> questions = new ArrayList<>();
		for (Question q : quizz.getQuestions()) {
			List<Response> responses = new ArrayList<>();
			for(Response r : q.getResponses()) {
				responses.add(responseService.create(r.getResponse(), r.isTrue()));
			}
			questions.add(questionService.create(q.getQuestion(), responses));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(quizzService.create(quizz.getCreator(), quizz.getTheme(), questions));
	}

}
