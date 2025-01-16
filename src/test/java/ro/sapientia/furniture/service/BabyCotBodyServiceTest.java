package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.BabyCotBody;
import ro.sapientia.furniture.repository.BabyCotBodyRepository;

public class BabyCotBodyServiceTest {

	private BabyCotBodyRepository repositoryMock;

	private BabyCotBodyService service;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(BabyCotBodyRepository.class);
		service = new BabyCotBodyService(repositoryMock);
	}

	@Test
	public void testFindAllBabyCotBodies_emptyList() {
		when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
		final List<BabyCotBody> babyCotBodies =  service.findAllBabyCotBodies();
		
		assertEquals(0, babyCotBodies.size());
	}

	@Test
	public void testFindAllBabyCotBodies_null() {
		when(repositoryMock.findAll()).thenReturn(null);
		final List<BabyCotBody> babyCotBodies =  service.findAllBabyCotBodies();
		
		assertNull(babyCotBodies);
	}

}
