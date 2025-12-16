package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

public class FurnitureBodyServiceExtendedTest {

	private FurnitureBodyRepository repositoryMock;
	private FurnitureBodyService service;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(FurnitureBodyRepository.class);
		service = new FurnitureBodyService(repositoryMock);
	}

	@Test
	public void testFindFurnitureBodyById() {
		FurnitureBody fb = new FurnitureBody();
		fb.setId(1L);
		fb.setHeigth(20);
		fb.setWidth(10);
		fb.setDepth(6);

		when(repositoryMock.findFurnitureBodyById(1L)).thenReturn(fb);

		FurnitureBody result = service.findFurnitureBodyById(1L);

		assertNotNull(result);
		assertEquals(Long.valueOf(1L), result.getId());
		assertEquals(Integer.valueOf(20), result.getHeigth());
		verify(repositoryMock).findFurnitureBodyById(1L);
	}

	@Test
	public void testFindFurnitureBodyById_NotFound() {
		when(repositoryMock.findFurnitureBodyById(999L)).thenReturn(null);

		FurnitureBody result = service.findFurnitureBodyById(999L);

		assertNull(result);
		verify(repositoryMock).findFurnitureBodyById(999L);
	}

	@Test
	public void testCreate() {
		FurnitureBody fb = new FurnitureBody();
		fb.setHeigth(20);
		fb.setWidth(10);
		fb.setDepth(6);
		fb.setThickness(18);

		FurnitureBody savedFb = new FurnitureBody();
		savedFb.setId(1L);
		savedFb.setHeigth(20);
		savedFb.setWidth(10);
		savedFb.setDepth(6);
		savedFb.setThickness(18);

		when(repositoryMock.saveAndFlush(fb)).thenReturn(savedFb);

		FurnitureBody result = service.create(fb);

		assertNotNull(result);
		assertEquals(Long.valueOf(1L), result.getId());
		verify(repositoryMock).saveAndFlush(fb);
	}

	@Test
	public void testUpdate() {
		FurnitureBody fb = new FurnitureBody();
		fb.setId(1L);
		fb.setHeigth(25);
		fb.setWidth(15);
		fb.setDepth(8);
		fb.setThickness(20);

		FurnitureBody updatedFb = new FurnitureBody();
		updatedFb.setId(1L);
		updatedFb.setHeigth(25);
		updatedFb.setWidth(15);
		updatedFb.setDepth(8);
		updatedFb.setThickness(20);

		when(repositoryMock.saveAndFlush(fb)).thenReturn(updatedFb);

		FurnitureBody result = service.update(fb);

		assertNotNull(result);
		assertEquals(Integer.valueOf(25), result.getHeigth());
		verify(repositoryMock).saveAndFlush(fb);
	}

	@Test
	public void testDelete() {
		service.delete(1L);
		verify(repositoryMock).deleteById(1L);
	}

	@Test
	public void testFindAllFurnitureBodies_WithData() {
		FurnitureBody fb1 = new FurnitureBody();
		fb1.setId(1L);
		fb1.setHeigth(20);

		FurnitureBody fb2 = new FurnitureBody();
		fb2.setId(2L);
		fb2.setHeigth(30);

		when(repositoryMock.findAll()).thenReturn(List.of(fb1, fb2));

		List<FurnitureBody> result = service.findAllFurnitureBodies();

		assertEquals(2, result.size());
		assertEquals(Long.valueOf(1L), result.get(0).getId());
		assertEquals(Long.valueOf(2L), result.get(1).getId());
	}
}

