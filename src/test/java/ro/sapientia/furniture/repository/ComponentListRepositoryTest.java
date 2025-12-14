package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class ComponentListRepositoryTest {

	@Autowired
	ComponentListRepository repository;

	@Autowired
	FurnitureBodyRepository furnitureBodyRepository;

	@Test
	public void testFindAll() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void testSaveAndFind() {
		FurnitureBody fb = new FurnitureBody();
		fb.setHeigth(20);
		fb.setWidth(10);
		fb.setDepth(6);
		fb.setThickness(18);
		FurnitureBody savedFb = furnitureBodyRepository.save(fb);

		ComponentList cl = new ComponentList();
		cl.setFurnitureBody(savedFb);
		cl.setCreatedBy(100L);
		ComponentList savedCl = repository.save(cl);

		assertNotNull(savedCl.getId());
		assertEquals(savedFb.getId(), savedCl.getFurnitureBody().getId());
		assertEquals(Long.valueOf(100L), savedCl.getCreatedBy());

		var found = repository.findById(savedCl.getId());
		assertNotNull(found);
		assertEquals(savedCl.getId(), found.get().getId());
	}

	@Test
	public void testFindByFurnitureBody_Id() {
		FurnitureBody fb1 = new FurnitureBody();
		fb1.setHeigth(20);
		fb1.setWidth(10);
		fb1.setDepth(6);
		FurnitureBody savedFb1 = furnitureBodyRepository.save(fb1);

		FurnitureBody fb2 = new FurnitureBody();
		fb2.setHeigth(30);
		fb2.setWidth(15);
		fb2.setDepth(8);
		FurnitureBody savedFb2 = furnitureBodyRepository.save(fb2);

		ComponentList cl1 = new ComponentList();
		cl1.setFurnitureBody(savedFb1);
		cl1.setCreatedBy(100L);
		repository.save(cl1);

		ComponentList cl2 = new ComponentList();
		cl2.setFurnitureBody(savedFb1);
		cl2.setCreatedBy(200L);
		repository.save(cl2);

		ComponentList cl3 = new ComponentList();
		cl3.setFurnitureBody(savedFb2);
		cl3.setCreatedBy(300L);
		repository.save(cl3);

		var result = repository.findByFurnitureBody_Id(savedFb1.getId());
		assertEquals(2, result.size());
		assertEquals(savedFb1.getId(), result.get(0).getFurnitureBody().getId());
		assertEquals(savedFb1.getId(), result.get(1).getFurnitureBody().getId());

		var result2 = repository.findByFurnitureBody_Id(savedFb2.getId());
		assertEquals(1, result2.size());
		assertEquals(savedFb2.getId(), result2.get(0).getFurnitureBody().getId());
	}
}

