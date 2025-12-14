package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FrontElement;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

public class PersistenceServiceTest {

	private FurnitureBodyRepository furnitureBodyRepositoryMock;
	private ComponentListRepository componentListRepositoryMock;
	private RawMaterialService rawMaterialServiceMock;
	private PersistenceService service;

	@BeforeEach
	public void setUp() {
		furnitureBodyRepositoryMock = mock(FurnitureBodyRepository.class);
		componentListRepositoryMock = mock(ComponentListRepository.class);
		rawMaterialServiceMock = mock(RawMaterialService.class);
		service = new PersistenceService(furnitureBodyRepositoryMock, componentListRepositoryMock, rawMaterialServiceMock);
	}

	@Test
	public void testSaveFurnitureBodyAndComponents() {
		FurnitureBody fb = new FurnitureBody();
		fb.setWidth(100);
		fb.setHeigth(200);
		fb.setDepth(50);
		fb.setThickness(18);

		RawMaterialType type = new RawMaterialType("Test Type");
		FrontElement fe = new FrontElement(fb, FrontElement.ElementType.BODY, 0, 0, 100, 200, "Test", type);
		fb.setFrontElements(List.of(fe));

		FurnitureBody savedFb = new FurnitureBody();
		savedFb.setId(1L);
		savedFb.setWidth(100);
		savedFb.setHeigth(200);
		savedFb.setDepth(50);
		savedFb.setThickness(18);
		savedFb.setFrontElements(List.of(fe));

		ComponentList cl = new ComponentList();
		cl.setFurnitureBody(savedFb);
		LinkedList<RawMaterial> rawMaterials = new LinkedList<>();
		RawMaterial rm1 = new RawMaterial(18, 100, 50, type, 2);
		RawMaterial rm2 = new RawMaterial(200, 18, 50, type, 2);
		rawMaterials.add(rm1);
		rawMaterials.add(rm2);
		cl.setRawMaterials(rawMaterials);

		ComponentList savedCl = new ComponentList();
		savedCl.setId(1L);
		savedCl.setFurnitureBody(savedFb);
		savedCl.setCreatedBy(100L);
		savedCl.setCreatedAt(LocalDateTime.now());
		savedCl.setUpdatedAt(LocalDateTime.now());

		RawMaterial persistedRm1 = new RawMaterial(18, 100, 50, type, 2);
		persistedRm1.setId(1L);
		RawMaterial persistedRm2 = new RawMaterial(200, 18, 50, type, 2);
		persistedRm2.setId(2L);

		when(furnitureBodyRepositoryMock.save(fb)).thenReturn(savedFb);
		when(componentListRepositoryMock.save(any(ComponentList.class))).thenReturn(savedCl);
		when(rawMaterialServiceMock.findOrCreateAndIncreaseQuantity(any(RawMaterial.class)))
				.thenReturn(persistedRm1, persistedRm2);

		FurnitureBody result = service.saveFurnitureBodyAndComponents(fb, 100L);

		assertNotNull(result);
		assertEquals(savedFb.getId(), result.getId());
		verify(furnitureBodyRepositoryMock).save(fb);
		verify(componentListRepositoryMock).save(any(ComponentList.class));
		verify(rawMaterialServiceMock).findOrCreateAndIncreaseQuantity(rm1);
		verify(rawMaterialServiceMock).findOrCreateAndIncreaseQuantity(rm2);
	}

	@Test
	public void testSaveFurnitureBodyAndComponents_WithNullRawMaterials() {
		FurnitureBody fb = new FurnitureBody();
		fb.setWidth(100);
		fb.setHeigth(200);
		fb.setDepth(50);
		fb.setThickness(18);

		FurnitureBody savedFb = new FurnitureBody();
		savedFb.setId(1L);
		savedFb.setWidth(100);
		savedFb.setHeigth(200);
		savedFb.setDepth(50);
		savedFb.setThickness(18);

		ComponentList cl = new ComponentList();
		cl.setFurnitureBody(savedFb);
		cl.setRawMaterials(null);

		ComponentList savedCl = new ComponentList();
		savedCl.setId(1L);
		savedCl.setFurnitureBody(savedFb);
		savedCl.setCreatedBy(100L);

		when(furnitureBodyRepositoryMock.save(fb)).thenReturn(savedFb);
		when(componentListRepositoryMock.save(any(ComponentList.class))).thenReturn(savedCl);

		FurnitureBody result = service.saveFurnitureBodyAndComponents(fb, 100L);

		assertNotNull(result);
		verify(furnitureBodyRepositoryMock).save(fb);
		verify(componentListRepositoryMock).save(any(ComponentList.class));
	}

	@Test
	public void testSaveFurnitureBodyAndComponents_WithEmptyRawMaterials() {
		FurnitureBody fb = new FurnitureBody();
		fb.setWidth(100);
		fb.setHeigth(200);
		fb.setDepth(50);
		fb.setThickness(18);

		FurnitureBody savedFb = new FurnitureBody();
		savedFb.setId(1L);

		ComponentList cl = new ComponentList();
		cl.setFurnitureBody(savedFb);
		cl.setRawMaterials(new ArrayList<>());

		ComponentList savedCl = new ComponentList();
		savedCl.setId(1L);
		savedCl.setCreatedBy(100L);

		when(furnitureBodyRepositoryMock.save(fb)).thenReturn(savedFb);
		when(componentListRepositoryMock.save(any(ComponentList.class))).thenReturn(savedCl);

		FurnitureBody result = service.saveFurnitureBodyAndComponents(fb, 100L);

		assertNotNull(result);
		verify(furnitureBodyRepositoryMock).save(fb);
		verify(componentListRepositoryMock).save(any(ComponentList.class));
	}
}

