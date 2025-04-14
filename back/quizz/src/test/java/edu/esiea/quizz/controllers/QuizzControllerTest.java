package edu.esiea.quizz.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.esiea.quizz.models.Question;
import edu.esiea.quizz.models.Quizz;
import edu.esiea.quizz.models.Response;
import edu.esiea.quizz.models.User;
import edu.esiea.quizz.services.QuestionService;
import edu.esiea.quizz.services.QuizzService;
import edu.esiea.quizz.services.ResponseService;

@WebMvcTest(QuizzController.class)
@ExtendWith(MockitoExtension.class)
class QuizzControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
	private QuizzService quizzService;
    @MockitoBean
	private QuestionService questionService;
    @MockitoBean
	private ResponseService responseService;

    private final ObjectMapper mapper = new ObjectMapper();

	//test data
	private static final User TEST_USER = new User();
	private static final Quizz TEST_QUIZZ1 = new Quizz();
	private static final Quizz TEST_QUIZZ2 = new Quizz();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TEST_USER.setId(1);
		TEST_USER.setLogin("test");
		TEST_USER.setPassword("mdp1234");
		TEST_USER.setAdmin(false);
		
		TEST_QUIZZ1.setId(1);
		TEST_QUIZZ1.setTheme("test");
		List<Question> questions = new ArrayList<>();
		for(int i = 0; i<5; i++) {
			Question q = new Question();
			q.setId(i+1);
			q.setQuestion("q"+q.getId());
			List<Response> responses = new ArrayList<>();
			for(int j = 0; j<3; j++) {
				Response r = new Response();
				r.setId((i+1)*j+1);
				r.setTrue(j==0);
				r.setResponse("r"+r.getId());
				responses.add(r);
			}
			q.setResponses(responses);
			questions.add(q);
		}
		TEST_QUIZZ1.setQuestions(questions);
		TEST_QUIZZ1.setCreator(TEST_USER);

		TEST_QUIZZ2.setId(2);
		TEST_QUIZZ2.setTheme("test");
		List<Question> questions2 = new ArrayList<>();
		for(int i = 5; i<10; i++) {
			Question q = new Question();
			q.setId(i+1);
			q.setQuestion("q"+q.getId());
			List<Response> responses = new ArrayList<>();
			for(int j = 0; j<3; j++) {
				Response r = new Response();
				r.setId((i+1)*j+1);
				r.setTrue(j==0);
				r.setResponse("r"+r.getId());
				responses.add(r);
			}
			q.setResponses(responses);
			questions2.add(q);
		}
		TEST_QUIZZ2.setQuestions(questions);
		TEST_QUIZZ2.setCreator(TEST_USER);
	}

	@Test
	void testGetAll() {
        // Simuler la réponse du service
		List<Quizz> mockedList = new ArrayList<>();
		mockedList.add(TEST_QUIZZ1);
		mockedList.add(TEST_QUIZZ2);
        when(quizzService.getAll()).thenReturn(mockedList);

        // Exécuter la requête GET
        try {
			mockMvc.perform(get("/users/all")
			        .contentType(MediaType.APPLICATION_JSON))
			        .andExpect(status().isOk())
			        .andExpect(jsonPath("$[0].id").value(mockedList.getFirst().getId()))
			        .andExpect(jsonPath("$[1].theme").value(mockedList.getLast().getTheme()));
			//TODO verifier tout les champs
		} catch (Exception e) {
			fail(e);
		}

        // Vérifier que la méthode du service a été appelée une fois
        verify(quizzService, times(1)).getAll();
	}

//	@Test
//	void testGetAllString() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetOne() {
        // Simuler la réponse du service
        when(quizzService.get(1)).thenReturn(TEST_QUIZZ1);

        // Exécuter la requête GET
        try {
			mockMvc.perform(get("/users/1")
			        .contentType(MediaType.APPLICATION_JSON))
			        .andExpect(status().isOk())
			        .andExpect(jsonPath("$.id").value(TEST_QUIZZ1.getId()))
			        .andExpect(jsonPath("$.theme").value(TEST_QUIZZ1.getTheme()));
			//TODO verifier tout les champs
		} catch (Exception e) {
			fail(e);
		}

        // Vérifier que la méthode du service a été appelée une fois
        verify(quizzService, times(1)).get(1);
	}

	@Test
	void testCreate() {
		// Given
        when(quizzService.create(any(User.class), anyString(), anyList())).thenReturn(TEST_QUIZZ1); //n'importe quelle entrée

        // When & Then
        try {
			mockMvc.perform(post("/Quizz")
			                .contentType(MediaType.APPLICATION_JSON)
			                .content(mapper.writeValueAsString(TEST_QUIZZ1)))
			        .andExpect(status().isCreated())
			        .andExpect(jsonPath("$.id").value(TEST_QUIZZ1.getId()));
		} catch (Exception e) {
			fail(e);
		}

        // Verify
        verify(quizzService, times(1)).create(any(User.class), anyString(), anyList());
	}

}
