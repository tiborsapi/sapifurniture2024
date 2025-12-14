package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComponentListTest {

	private FurnitureBody furnitureBody;
	private RawMaterialType rawMaterialType;

	@BeforeEach
	void setUp() {
		furnitureBody = new FurnitureBody();
		furnitureBody.setId(1L);
		rawMaterialType = new RawMaterialType("Test Material");
	}

	@Test
	void testDefaultConstructor() {
		ComponentList cl = new ComponentList();
		assertNull(cl.getId());
		assertNull(cl.getCreatedBy());
		assertNull(cl.getFurnitureBody());
		assertNull(cl.getRawMaterials());
		assertNull(cl.getCreatedAt());
		assertNull(cl.getUpdatedAt());
	}

	@Test
	void testConstructorWithCreatedBy() {
		ComponentList cl = new ComponentList(100L);

		assertEquals(Long.valueOf(100L), cl.getCreatedBy());
		assertNotNull(cl.getCreatedAt());
		assertNotNull(cl.getUpdatedAt());
	}

	@Test
	void testConstructorWithFurnitureBodyAndRawMaterials() {
		List<RawMaterial> rawMaterials = new ArrayList<>();
		rawMaterials.add(new RawMaterial(10, 20, 30, rawMaterialType, 5));
		rawMaterials.add(new RawMaterial(15, 25, 35, rawMaterialType, 3));

		ComponentList cl = new ComponentList(furnitureBody, rawMaterials);

		assertEquals(furnitureBody, cl.getFurnitureBody());
		assertEquals(2, cl.getRawMaterials().size());
		assertNotNull(cl.getCreatedAt());
		assertNotNull(cl.getUpdatedAt());
	}

	@Test
	void testSettersAndGetters() {
		ComponentList cl = new ComponentList();
		cl.setId(1L);
		cl.setCreatedBy(200L);
		cl.setFurnitureBody(furnitureBody);
		cl.setCreatedAt(LocalDateTime.now().minusDays(1));
		cl.setUpdatedAt(LocalDateTime.now());

		List<RawMaterial> rawMaterials = new ArrayList<>();
		rawMaterials.add(new RawMaterial(10, 20, 30, rawMaterialType, 5));
		cl.setRawMaterials(rawMaterials);

		assertEquals(Long.valueOf(1L), cl.getId());
		assertEquals(Long.valueOf(200L), cl.getCreatedBy());
		assertEquals(furnitureBody, cl.getFurnitureBody());
		assertEquals(1, cl.getRawMaterials().size());
		assertNotNull(cl.getCreatedAt());
		assertNotNull(cl.getUpdatedAt());
	}

	@Test
	void testToString() {
		ComponentList cl = new ComponentList(furnitureBody, new ArrayList<>());
		cl.setId(1L);
		cl.setCreatedBy(100L);
		String str = cl.toString();

		assertNotNull(str);
		assertNotNull(str.contains("ComponentList"));
		assertNotNull(str.contains("1"));
	}
}

