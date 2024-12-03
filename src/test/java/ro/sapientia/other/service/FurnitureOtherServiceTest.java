package ro.sapientia.other.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.repository.FurnitureBodyRepository;
import ro.sapientia.furniture.service.FurnitureBodyService;
import ro.sapientia.other.model.FurnitureOther;
import ro.sapientia.other.repository.FurnitureOtherRepository;

public class FurnitureOtherServiceTest {

	private FurnitureOtherRepository furnitureOtherRepositoryMock;

	private FurnitureOtherService furnitureOtherService;

	@BeforeEach
	public void setUp() {
		furnitureOtherRepositoryMock = mock(FurnitureOtherRepository.class);
		furnitureOtherService = new FurnitureOtherService(furnitureOtherRepositoryMock);
	}

	@Test
	public void testFindAllFurnitureBodies_emptyList() {
		when(furnitureOtherRepositoryMock.findAll()).thenReturn(Collections.emptyList());

		final List<FurnitureOther> furnitureOthers = furnitureOtherService.findAllFurnitureOthers();

		assertEquals(0, furnitureOthers.size());

	}

	@Test
	public void testFindAllFurnitureBodies_null() {
		when(furnitureOtherRepositoryMock.findAll()).thenReturn(null);

		final List<FurnitureOther> furnitureOthers = furnitureOtherService.findAllFurnitureOthers();

		assertNull(furnitureOthers);

	}

}
