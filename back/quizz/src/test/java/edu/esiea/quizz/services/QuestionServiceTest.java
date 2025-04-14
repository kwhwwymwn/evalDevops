package edu.esiea.quizz.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import edu.esiea.quizz.models.Question;
import edu.esiea.quizz.models.Response;
import edu.esiea.quizz.repository.QuestionRepository;

class QuestionServiceTest {
	
	@Mock
	private QuestionRepository repo;
	
	@InjectMocks
	private QuestionService service;
	
	//test data
	private static final Question TEST_QUESTION = new Question();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TEST_QUESTION.setId(1);
		TEST_QUESTION.setQuestion("test");
		List<Response> responses = new ArrayList<>();
		for(int i = 0; i<3; i++) {
			Response r = new Response();
			r.setId(i+1);
			r.setTrue(i==0);
			r.setResponse("r"+r.getId());
			responses.add(r);
		}
		TEST_QUESTION.setResponses(responses);
	}

	@Test
	void testCreate() {
        // Given
        when(repo.save(any(Question.class))).thenReturn(TEST_QUESTION);

        // When
        Question question = service.create("test", TEST_QUESTION.getResponses());

        // Then
        assertNotNull(question);
        assertEquals(TEST_QUESTION, question);
        verify(repo, times(1)).save(any(Question.class));
	}

}
