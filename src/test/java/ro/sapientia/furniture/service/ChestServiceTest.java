package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.Chest;
import ro.sapientia.furniture.repository.ChestRepository;

public class ChestServiceTest {

	private ChestRepository repositoryMock;

	private ChestService service;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(ChestRepository.class);
		service = new ChestService(repositoryMock);
	}

	@Test
	public void testFindAllChests_emptyList() {
		when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
		final List<Chest> chests =  service.findAllChests();
		
		assertEquals(0, chests.size());
	}

	@Test
	public void testFindAllFurnitureBodies_null() {
		when(repositoryMock.findAll()).thenReturn(null);
		final List<Chest> chests =  service.findAllChests();
		
		assertNull(chests);
	}

}
