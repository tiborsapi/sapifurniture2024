package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.HallTreeBody;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class HallTreeBodyRepositoryTest {
	
	@Autowired
	HallTreeBodyRepository repository;
	
	@Test
	public void myTest() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}
	
	@Test
	public void myTestForFindFirst() {
		HallTreeBody hb = new HallTreeBody();
		hb.setHeigth(20);
		hb.setWidth(10);
		hb.setDepth(5);
		var savedHB = repository.save(hb);
		var result = repository.findAll();
		assertEquals(1, result.size());
		
		var foundObj = repository.findHallTreeBodyById(savedHB.getId());
		
		assertEquals(savedHB,foundObj);
	}
}
