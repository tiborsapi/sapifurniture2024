package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.Chest;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class ChestRepositoryTest {

	@Autowired
	ChestRepository repository;
	
	@Test
	public void myTest() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}
	
	@Test
	public void myTestForFindFirst() {
		Chest chest = new Chest() ;
		chest.setHeigth(20);
		chest.setWidth(10);
		chest.setDepth(6);
		chest.setType("Bachelors");
		var savedChest = repository.save(chest) ;
		var result = repository.findAll();
		assertEquals(1, result.size());
		
		var foundObj = repository.findChestById(savedChest.getId()) ;
		
		assertEquals(savedChest, foundObj) ;
	}
	
}
