package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.HallTreeBody;
import ro.sapientia.furniture.repository.HallTreeBodyRepository;

public class HallTreeBodyServiceTest {
	
	private HallTreeBodyRepository repositoryMock;

	private HallTreeBodyService service;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(HallTreeBodyRepository.class);
		service = new HallTreeBodyService(repositoryMock);
	}

	@Test
	public void testFindAllHallTreeBodies_emptyList() {
		when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
		final List<HallTreeBody> hallTreeBodies =  service.findAllHallTreeBodies();
		
		assertEquals(0, hallTreeBodies.size());
	}

	@Test
	public void testFindAllHallTreeBodies_null() {
		when(repositoryMock.findAll()).thenReturn(null);
		final List<HallTreeBody> hallTreeBodies =  service.findAllHallTreeBodies();
		
		assertNull(hallTreeBodies);
	}
}
