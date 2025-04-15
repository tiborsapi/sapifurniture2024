package ro.sapientia.chair.service;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import ro.sapientia.chair.model.FurnitureChair;
import ro.sapientia.chair.repository.FurnitureChairRepository;
import ro.sapientia.chair.FurnitureApplication;

@SpringBootTest(classes = FurnitureChairServiceTest.class)
public class FurnitureChairServiceTest {

	@Mock
	private FurnitureChairRepository furnitureChairRepositoryMock;

	@MockBean(FurnitureChairService.class)
	private FurnitureChairService furnitureChairService;

	@BeforeEach
	public void setUp() {
		furnitureChairRepositoryMock = mock(FurnitureChairRepository.class);
		furnitureChairService = new FurnitureChairService(furnitureChairRepositoryMock);
	}

	@Test
	public void testFindAllFurnitureBodies_emptyList() {
		when(furnitureChairRepositoryMock.findAll()).thenReturn(Collections.emptyList());

		final List<FurnitureChair> furnitureChairs = furnitureChairService.findAllFurnitureChairs();

		assertEquals(0, furnitureChairs.size());

	}

	@Test
	public void testFindAllFurnitureBodies_null() {
		when(furnitureChairRepositoryMock.findAll()).thenReturn(null);

		final List<FurnitureChair> furnitureChairs = furnitureChairService.findAllFurnitureChairs();

		assertNull(furnitureChairs);

	}

}
