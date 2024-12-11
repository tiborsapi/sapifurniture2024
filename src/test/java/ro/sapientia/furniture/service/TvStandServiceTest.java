package ro.sapientia.furniture.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;
import ro.sapientia.furniture.repository.TvStandRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TvStandServiceTest {

	private TvStandRepository repositoryMock;

	private TvStandService service;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(TvStandRepository.class);
		service = new TvStandService(repositoryMock);
	}

	@Test
	public void testFindAllTvStand_emptyList() {
		when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
		final List<Tvstand> tvStands =  service.findAllTvStand();
		
		assertEquals(0, tvStands.size());
	}

	@Test
	public void testFindAllTvStand_null() {
		when(repositoryMock.findAll()).thenReturn(null);
		final List<Tvstand> tvStands =  service.findAllTvStand();
		
		assertNull(tvStands);
	}

}
