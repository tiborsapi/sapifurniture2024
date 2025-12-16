package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.RawMaterialType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class RawMaterialTypeRepositoryTest {

	@Autowired
	RawMaterialTypeRepository repository;

	@Test
	public void testFindAll() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void testSaveAndFind() {
		RawMaterialType type = new RawMaterialType("Test Type");
		RawMaterialType savedType = repository.save(type);

		assertNotNull(savedType.getId());
		assertEquals("Test Type", savedType.getName());
		assertNotNull(savedType.getCreatedAt());
		assertNotNull(savedType.getUpdatedAt());

		var found = repository.findById(savedType.getId());
		assertTrue(found.isPresent());
		assertEquals("Test Type", found.get().getName());
	}

	@Test
	public void testFindByName() {
		RawMaterialType type1 = new RawMaterialType("Type 1");
		repository.save(type1);

		RawMaterialType type2 = new RawMaterialType("Type 2");
		repository.save(type2);

		var result = repository.findByName("Type 1");
		assertTrue(result.isPresent());
		assertEquals("Type 1", result.get().getName());

		var result2 = repository.findByName("Type 2");
		assertTrue(result2.isPresent());
		assertEquals("Type 2", result2.get().getName());

		var result3 = repository.findByName("Non Existent");
		assertTrue(result3.isEmpty());
	}
}

