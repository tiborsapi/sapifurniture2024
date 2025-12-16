package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RawMaterialTest {

	private RawMaterialType rawMaterialType;

	@BeforeEach
	void setUp() {
		rawMaterialType = new RawMaterialType("Test Material");
	}

	@Test
	void testDefaultConstructor() {
		RawMaterial rm = new RawMaterial();
		assertNull(rm.getId());
		assertNull(rm.getHeight());
		assertNull(rm.getWidth());
		assertNull(rm.getLength());
		assertNull(rm.getRawMaterialType());
		assertNull(rm.getQuantity());
	}

	@Test
	void testConstructorWithParameters() {
		RawMaterial rm = new RawMaterial(10, 20, 30, rawMaterialType, 5);

		assertEquals(Integer.valueOf(10), rm.getHeight());
		assertEquals(Integer.valueOf(20), rm.getWidth());
		assertEquals(Integer.valueOf(30), rm.getLength());
		assertEquals(rawMaterialType, rm.getRawMaterialType());
		assertEquals(Integer.valueOf(5), rm.getQuantity());
		assertNotNull(rm.getCreatedAt());
		assertNotNull(rm.getUpdatedAt());
	}

	@Test
	void testConstructorWithComponentList() {
		ComponentList cl = new ComponentList();
		cl.setId(1L);
		RawMaterial rm = new RawMaterial(10, 20, 30, rawMaterialType, 5, cl);

		assertEquals(Integer.valueOf(10), rm.getHeight());
		assertEquals(cl, rm.getComponentList());
		assertNotNull(rm.getCreatedAt());
		assertNotNull(rm.getUpdatedAt());
	}

	@Test
	void testSettersAndGetters() {
		RawMaterial rm = new RawMaterial();
		rm.setId(1L);
		rm.setHeight(15);
		rm.setWidth(25);
		rm.setLength(35);
		rm.setRawMaterialType(rawMaterialType);
		rm.setQuantity(10);
		rm.setCreatedAt(LocalDateTime.now());
		rm.setUpdatedAt(LocalDateTime.now());

		assertEquals(Long.valueOf(1L), rm.getId());
		assertEquals(Integer.valueOf(15), rm.getHeight());
		assertEquals(Integer.valueOf(25), rm.getWidth());
		assertEquals(Integer.valueOf(35), rm.getLength());
		assertEquals(rawMaterialType, rm.getRawMaterialType());
		assertEquals(Integer.valueOf(10), rm.getQuantity());
		assertNotNull(rm.getCreatedAt());
		assertNotNull(rm.getUpdatedAt());
	}

	@Test
	void testToString() {
		RawMaterial rm = new RawMaterial(10, 20, 30, rawMaterialType, 5);
		rm.setId(1L);
		String str = rm.toString();

		assertNotNull(str);
		assertNotNull(str.contains("RawMaterial"));
		assertNotNull(str.contains("1"));
	}
}

