package ro.sapientia.furniture.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class TvStandRepositoryTest {

	@Autowired
	TvStandRepository repository;
	
	@Test
	public void myTest() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void myTestForFindFirst() {
		Tvstand ts = new Tvstand();
		ts.setHeigth(20);
		ts.setWidth(10);
		ts.setDepth(6);
		var savedTS = repository.save(ts);
		var result = repository.findAll();
		assertEquals(1, result.size());
		
		var foundObj = repository.findTvstandById(savedTS.getId());
		
		assertEquals(savedTS, foundObj);
	}
	
}
