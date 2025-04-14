package edu.esiea.quizz.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.esiea.quizz.models.Question;
import edu.esiea.quizz.models.Quizz;
import edu.esiea.quizz.models.Response;
import edu.esiea.quizz.models.User;
import edu.esiea.quizz.repository.QuizzRepository;

@ExtendWith(MockitoExtension.class)
class QuizzServiceTest {
		
	@Mock
	private QuizzRepository repo;
	
	@InjectMocks
	private QuizzService service;
	
	//test data
	private static final User TEST_USER = new User();
	private static final Quizz TEST_QUIZZ = new Quizz();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TEST_USER.setId(1);
		TEST_USER.setLogin("test");
		TEST_USER.setPassword("mdp1234");
		TEST_USER.setAdmin(false);
		
		TEST_QUIZZ.setId(1);
		TEST_QUIZZ.setTheme("test");
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
		TEST_QUIZZ.setQuestions(questions);
		TEST_QUIZZ.setCreator(TEST_USER);
	}

	@Test
	void testGetAll() {
        // Given
        when(repo.findAll()).thenReturn(List.of(TEST_QUIZZ));

        // When
        List<Quizz> list = service.getAll();

        // Then
        assertNotNull(list);
        assertEquals(1, list.size());
        verify(repo, times(1)).findAll();
	}

//	@Test
//	void testGetAllString() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGet() {
        // Given
        when(repo.findById(1)).thenReturn(Optional.of(TEST_QUIZZ));
        when(repo.findById(2)).thenReturn(Optional.empty());

        // When
        Quizz quizz = service.get(1);

        // Then
        assertNotNull(quizz);
        assertEquals(TEST_QUIZZ, quizz);
        
        assertThrows(RuntimeException.class, () -> service.get(2));
        
        verify(repo, times(1)).findById(1);
        verify(repo, times(1)).findById(2);
	}

	@Test
	void testCreate() {
        // Given
        when(repo.save(any(Quizz.class))).thenReturn(TEST_QUIZZ);

        // When
        Quizz quizz = service.create(TEST_USER, "test", TEST_QUIZZ.getQuestions());

        // Then
        assertNotNull(quizz);
        assertEquals(TEST_QUIZZ, quizz);
        verify(repo, times(1)).save(any(Quizz.class));
	}

}
