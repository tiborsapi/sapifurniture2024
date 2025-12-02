package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class FurnitureBodyDTORepositoryTest {

	@Autowired
	FurnitureBodyRepository repository;
	
	@Test
	public void myTest() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void myTestForFindFirst() {
		FurnitureBodyDTO fb = new FurnitureBodyDTO();
		fb.setHeigth(20);
		fb.setWidth(10);
		fb.setDepth(6);
		var savedFB = repository.save(fb);
		var result = repository.findAll();
		assertEquals(1, result.size());
		
		var foundObj = repository.findFurnitureBodyById(savedFB.getId());
		
		assertEquals(savedFB, foundObj);
	}
	
}
