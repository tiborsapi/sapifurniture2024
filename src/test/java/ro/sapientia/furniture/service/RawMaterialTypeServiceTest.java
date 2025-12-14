package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.RawMaterialTypeRepository;

public class RawMaterialTypeServiceTest {

	private RawMaterialTypeRepository repositoryMock;
	private RawMaterialTypeService service;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(RawMaterialTypeRepository.class);
		service = new RawMaterialTypeService(repositoryMock);
	}

	@Test
	public void testFindOrCreateByName_Existing() {
		RawMaterialType existing = new RawMaterialType("Existing Type");
		existing.setId(1L);
		existing.setCreatedAt(LocalDateTime.now().minusDays(1));
		existing.setUpdatedAt(LocalDateTime.now().minusDays(1));

		when(repositoryMock.findByName("Existing Type")).thenReturn(Optional.of(existing));

		RawMaterialType result = service.findOrCreateByName("Existing Type");

		assertNotNull(result);
		assertEquals("Existing Type", result.getName());
		assertEquals(existing.getId(), result.getId());
		verify(repositoryMock, never()).save(any(RawMaterialType.class));
	}

	@Test
	public void testFindOrCreateByName_New() {
		when(repositoryMock.findByName("New Type")).thenReturn(Optional.empty());
		when(repositoryMock.save(any(RawMaterialType.class))).thenAnswer(invocation -> {
			RawMaterialType rmt = invocation.getArgument(0);
			rmt.setId(1L);
			return rmt;
		});

		RawMaterialType result = service.findOrCreateByName("New Type");

		assertNotNull(result);
		assertEquals("New Type", result.getName());
		assertNotNull(result.getCreatedAt());
		assertNotNull(result.getUpdatedAt());
		verify(repositoryMock).save(any(RawMaterialType.class));
	}

	@Test
	public void testFindOrCreateByName_WithWhitespace() {
		when(repositoryMock.findByName("Trimmed Type")).thenReturn(Optional.empty());
		when(repositoryMock.save(any(RawMaterialType.class))).thenAnswer(invocation -> {
			RawMaterialType rmt = invocation.getArgument(0);
			rmt.setId(1L);
			return rmt;
		});

		RawMaterialType result = service.findOrCreateByName("  Trimmed Type  ");

		assertNotNull(result);
		assertEquals("Trimmed Type", result.getName());
		verify(repositoryMock).findByName("Trimmed Type");
	}

	@Test
	public void testFindOrCreateByName_Null() {
		RawMaterialType result = service.findOrCreateByName(null);

		assertNull(result);
		verify(repositoryMock, never()).findByName(any());
		verify(repositoryMock, never()).save(any());
	}

	@Test
	public void testFindOrCreateByName_EmptyString() {
		RawMaterialType result = service.findOrCreateByName("");

		assertNull(result);
		verify(repositoryMock, never()).findByName(any());
		verify(repositoryMock, never()).save(any());
	}

	@Test
	public void testFindOrCreateByName_WhitespaceOnly() {
		RawMaterialType result = service.findOrCreateByName("   ");

		assertNull(result);
		verify(repositoryMock, never()).findByName(any());
		verify(repositoryMock, never()).save(any());
	}
}

