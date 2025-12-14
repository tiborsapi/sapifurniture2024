package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class RawMaterialTypeTest {

	@Test
	void testDefaultConstructor() {
		RawMaterialType rmt = new RawMaterialType();
		assertNull(rmt.getId());
		assertNull(rmt.getName());
		assertNull(rmt.getCreatedAt());
		assertNull(rmt.getUpdatedAt());
	}

	@Test
	void testConstructorWithName() {
		RawMaterialType rmt = new RawMaterialType("Test Material");

		assertEquals("Test Material", rmt.getName());
		assertNotNull(rmt.getCreatedAt());
		assertNotNull(rmt.getUpdatedAt());
	}

	@Test
	void testSettersAndGetters() {
		RawMaterialType rmt = new RawMaterialType();
		rmt.setId(1L);
		rmt.setName("Updated Material");
		rmt.setCreatedAt(LocalDateTime.now().minusDays(1));
		rmt.setUpdatedAt(LocalDateTime.now());

		assertEquals(Long.valueOf(1L), rmt.getId());
		assertEquals("Updated Material", rmt.getName());
		assertNotNull(rmt.getCreatedAt());
		assertNotNull(rmt.getUpdatedAt());
	}

	@Test
	void testToString() {
		RawMaterialType rmt = new RawMaterialType("Test Material");
		rmt.setId(1L);
		String str = rmt.toString();

		assertNotNull(str);
		assertNotNull(str.contains("RawMaterialType"));
		assertNotNull(str.contains("Test Material"));
	}
}

