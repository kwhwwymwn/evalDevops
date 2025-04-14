package edu.esiea.quizz.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.esiea.quizz.models.Response;
import edu.esiea.quizz.repository.ResponseRepository;

@ExtendWith(MockitoExtension.class)
class ResponseServiceTest {
	
	@Mock
	private ResponseRepository repo;
	
	@InjectMocks
	private ResponseService service;
	
	//test data
	private static final Response TEST_RESPONSE = new Response();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TEST_RESPONSE.setId(1);
		TEST_RESPONSE.setResponse("test");
		TEST_RESPONSE.setTrue(true);
	}

	@Test
	void testCreate() {
        // Given
        when(repo.save(any(Response.class))).thenReturn(TEST_RESPONSE);

        // When
        Response response = service.create("test", true);

        // Then
        assertNotNull(response);
        assertEquals(TEST_RESPONSE, response);
        verify(repo, times(1)).save(any(Response.class));
	}

}
