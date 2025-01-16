package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.BabyCotBody;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class BabyCotBodyRepositoryTest {

	@Autowired
	BabyCotBodyRepository repository;
	
	@Test
	public void myTest() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}
	
	@Test
	public void findFirstBabyCot() {
		BabyCotBody babyCot = new BabyCotBody();
		babyCot.setHeigth(7);
		
		var savedBabyCot = repository.save(babyCot);
		var babyCots = repository.findAll();
		
		assertEquals(1, babyCots.size());
		
		var foundBabyCot = repository.findBabyCotBodyById(savedBabyCot.getId());
		
		assertEquals(savedBabyCot, foundBabyCot);
	}
}
