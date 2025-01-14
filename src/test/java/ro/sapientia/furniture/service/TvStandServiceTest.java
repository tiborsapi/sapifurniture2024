package ro.sapientia.furniture.service;

import org.elasticsearch.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;
import ro.sapientia.furniture.repository.TvStandRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

public class TvStandServiceTest {

	private TvStandRepository repositoryMock;

	private TvStandService tvstandService;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(TvStandRepository.class);
		tvstandService = new TvStandService(repositoryMock);
	}

	@Test
	public void testFindAllTvStand_emptyList() {
		when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
		final List<Tvstand> tvStands =  tvstandService.findAllTvStand();
		
		assertEquals(0, tvStands.size());
	}

	@Test
	public void testFindAllTvStand_null() {
		when(repositoryMock.findAll()).thenReturn(null);
		final List<Tvstand> tvStands =  tvstandService.findAllTvStand();
		
		assertNull(tvStands);
	}

	@Test
	public void testFindTvstandById_found() {
		Tvstand tvstand = new Tvstand();
		tvstand.setId(1L);
		tvstand.setWidth(500);
		tvstand.setHeigth(500);
		tvstand.setDepth(300);
		tvstand.setDoors(2);
		tvstand.setMaterial("wood");

		when(repositoryMock.findById(1L)).thenReturn(Optional.of(tvstand));

		Tvstand result = tvstandService.findTvstandById(1L);
		assertEquals(tvstand, result);
		assertEquals(500, result.getWidth());
		assertEquals(500, result.getHeigth());
		assertEquals(300, result.getDepth());
		assertEquals(2, result.getDoors());
		assertEquals("wood", result.getMaterial());
	}

	@Test
	public void testFindTvstandById_notFound() {
		when(repositoryMock.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			tvstandService.findTvstandById(1L);
		});
	}

	@Test
	public void testCreateTvstand() {
		Tvstand tvstand = new Tvstand();
		tvstand.setWidth(500);
		tvstand.setHeigth(500);
		tvstand.setDepth(300);
		tvstand.setDoors(2);
		tvstand.setMaterial("wood");

		when(repositoryMock.saveAndFlush(tvstand)).thenReturn(tvstand);

		Tvstand result = tvstandService.create(tvstand);

		assertEquals(tvstand, result);
		verify(repositoryMock, times(1)).saveAndFlush(tvstand);
	}


	@Test
	public void testUpdateTvstand() {
		Tvstand tvstand = new Tvstand();
		tvstand.setId(1L);
		tvstand.setWidth(600);
		tvstand.setHeigth(600);
		tvstand.setDepth(400);
		tvstand.setDoors(3);
		tvstand.setMaterial("metal");

		when(repositoryMock.findById(1L)).thenReturn(Optional.of(tvstand));
		when(repositoryMock.saveAndFlush(tvstand)).thenReturn(tvstand);

		Tvstand result = tvstandService.update(tvstand);

		assertEquals(tvstand, result);
		verify(repositoryMock, times(1)).saveAndFlush(tvstand);
	}


	@Test
	public void testDeleteTvstand() {
		Long id = 1L;
		Tvstand tvstand = new Tvstand();
		tvstand.setId(id);

		when(repositoryMock.findById(id)).thenReturn(Optional.of(tvstand));
		doNothing().when(repositoryMock).deleteById(id);

		tvstandService.delete(id);

		verify(repositoryMock, times(1)).deleteById(id);
	}

	@Test
	public void testDeleteTvstand_notFound() {
		Long id = 1L;
		when(repositoryMock.findById(id)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			tvstandService.delete(id);
		});
	}

}
