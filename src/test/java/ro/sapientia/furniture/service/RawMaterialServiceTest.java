package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.RawMaterialRepository;

public class RawMaterialServiceTest {

	private RawMaterialRepository repositoryMock;
	private RawMaterialService service;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(RawMaterialRepository.class);
		service = new RawMaterialService(repositoryMock);
	}

	@Test
	public void testFindOrCreateAndIncreaseQuantity_NewMaterial() {
		RawMaterialType type = new RawMaterialType("Test Type");
		RawMaterial candidate = new RawMaterial(10, 20, 30, type, 5);

		when(repositoryMock.findByRawMaterialTypeAndLengthAndWidthAndHeight(type, 30, 20, 10))
				.thenReturn(Optional.empty());
		when(repositoryMock.save(any(RawMaterial.class))).thenAnswer(invocation -> {
			RawMaterial rm = invocation.getArgument(0);
			rm.setId(1L);
			return rm;
		});

		RawMaterial result = service.findOrCreateAndIncreaseQuantity(candidate);

		assertNotNull(result);
		assertEquals(Integer.valueOf(5), result.getQuantity());
		assertNotNull(result.getCreatedAt());
		assertNotNull(result.getUpdatedAt());
		verify(repositoryMock).save(any(RawMaterial.class));
	}

	@Test
	public void testFindOrCreateAndIncreaseQuantity_ExistingMaterial() {
		RawMaterialType type = new RawMaterialType("Test Type");
		RawMaterial existing = new RawMaterial(10, 20, 30, type, 3);
		existing.setId(1L);
		existing.setCreatedAt(LocalDateTime.now().minusDays(1));
		existing.setUpdatedAt(LocalDateTime.now().minusDays(1));

		RawMaterial candidate = new RawMaterial(10, 20, 30, type, 2);

		when(repositoryMock.findByRawMaterialTypeAndLengthAndWidthAndHeight(type, 30, 20, 10))
				.thenReturn(Optional.of(existing));
		when(repositoryMock.save(any(RawMaterial.class))).thenAnswer(invocation -> invocation.getArgument(0));

		RawMaterial result = service.findOrCreateAndIncreaseQuantity(candidate);

		assertNotNull(result);
		assertEquals(Integer.valueOf(5), result.getQuantity());
		assertEquals(existing.getId(), result.getId());
		verify(repositoryMock).save(existing);
	}

	@Test
	public void testFindOrCreateAndIncreaseQuantity_NullQuantity() {
		RawMaterialType type = new RawMaterialType("Test Type");
		RawMaterial candidate = new RawMaterial(10, 20, 30, type, null);

		when(repositoryMock.findByRawMaterialTypeAndLengthAndWidthAndHeight(type, 30, 20, 10))
				.thenReturn(Optional.empty());
		when(repositoryMock.save(any(RawMaterial.class))).thenAnswer(invocation -> {
			RawMaterial rm = invocation.getArgument(0);
			rm.setId(1L);
			return rm;
		});

		RawMaterial result = service.findOrCreateAndIncreaseQuantity(candidate);

		assertNotNull(result);
		assertNotNull(result.getCreatedAt());
		verify(repositoryMock).save(any(RawMaterial.class));
	}

	@Test
	public void testFindOrCreateAndIncreaseQuantity_ExistingWithNullQuantity() {
		RawMaterialType type = new RawMaterialType("Test Type");
		RawMaterial existing = new RawMaterial(10, 20, 30, type, 5);
		existing.setId(1L);

		RawMaterial candidate = new RawMaterial(10, 20, 30, type, null);

		when(repositoryMock.findByRawMaterialTypeAndLengthAndWidthAndHeight(type, 30, 20, 10))
				.thenReturn(Optional.of(existing));
		when(repositoryMock.save(any(RawMaterial.class))).thenAnswer(invocation -> invocation.getArgument(0));

		RawMaterial result = service.findOrCreateAndIncreaseQuantity(candidate);

		assertEquals(Integer.valueOf(5), result.getQuantity());
	}
}

