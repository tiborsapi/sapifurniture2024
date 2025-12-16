package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class RawMaterialRepositoryTest {

	@Autowired
	RawMaterialRepository repository;

	@Autowired
	RawMaterialTypeRepository rawMaterialTypeRepository;

	@Test
	public void testFindAll() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void testSaveAndFind() {
		RawMaterialType type = new RawMaterialType("Test Type");
		RawMaterialType savedType = rawMaterialTypeRepository.save(type);

		RawMaterial rm = new RawMaterial(10, 20, 30, savedType, 5);
		RawMaterial savedRm = repository.save(rm);

		assertNotNull(savedRm.getId());
		assertEquals(Integer.valueOf(10), savedRm.getHeight());
		assertEquals(Integer.valueOf(20), savedRm.getWidth());
		assertEquals(Integer.valueOf(30), savedRm.getLength());
		assertEquals(Integer.valueOf(5), savedRm.getQuantity());
		assertEquals(savedType.getId(), savedRm.getRawMaterialType().getId());
	}

	@Test
	public void testFindByRawMaterialTypeAndLengthAndWidthAndHeight() {
		RawMaterialType type1 = new RawMaterialType("Type 1");
		RawMaterialType savedType1 = rawMaterialTypeRepository.save(type1);

		RawMaterialType type2 = new RawMaterialType("Type 2");
		RawMaterialType savedType2 = rawMaterialTypeRepository.save(type2);

		RawMaterial rm1 = new RawMaterial(10, 20, 30, savedType1, 5);
		repository.save(rm1);

		RawMaterial rm2 = new RawMaterial(10, 20, 30, savedType2, 3);
		repository.save(rm2);

		RawMaterial rm3 = new RawMaterial(15, 20, 30, savedType1, 2);
		repository.save(rm3);

		var result = repository.findByRawMaterialTypeAndLengthAndWidthAndHeight(savedType1, 30, 20, 10);
		assertTrue(result.isPresent());
		assertEquals(Integer.valueOf(5), result.get().getQuantity());
		assertEquals(savedType1.getId(), result.get().getRawMaterialType().getId());

		var result2 = repository.findByRawMaterialTypeAndLengthAndWidthAndHeight(savedType2, 30, 20, 10);
		assertTrue(result2.isPresent());
		assertEquals(Integer.valueOf(3), result2.get().getQuantity());

		var result3 = repository.findByRawMaterialTypeAndLengthAndWidthAndHeight(savedType1, 30, 20, 15);
		assertTrue(result3.isPresent());
		assertEquals(Integer.valueOf(2), result3.get().getQuantity());
	}

	@Test
	public void testFindByRawMaterialTypeAndLengthAndWidthAndHeight_NotFound() {
		RawMaterialType type = new RawMaterialType("Test Type");
		RawMaterialType savedType = rawMaterialTypeRepository.save(type);

		var result = repository.findByRawMaterialTypeAndLengthAndWidthAndHeight(savedType, 100, 200, 300);
		assertTrue(result.isEmpty());
	}
}

