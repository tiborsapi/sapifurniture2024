package ro.sapientia.chair.repository;

//import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.chair.model.FurnitureChair;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
@ContextConfiguration(classes = FurnitureChairRepositoryTest.class)
public class FurnitureChairRepositoryTest {

	@Autowired
	FurnitureChairRepository furnitureOtherRepository;

	@Test
	public void myTest() {
		var result = furnitureOtherRepository.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void myTestForFindFirst() {
		FurnitureChair fo = new FurnitureChair();
		fo.setName("cruel");

		var savedFO = furnitureOtherRepository.save(fo);
		var result = furnitureOtherRepository.findAll();
		assertEquals(1, result.size());

		var foundObj = furnitureOtherRepository.findFurnitureChairById(savedFO.getId());

		assertEquals(savedFO, foundObj);

	}

}
