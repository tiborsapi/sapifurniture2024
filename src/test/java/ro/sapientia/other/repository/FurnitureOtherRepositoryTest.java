package ro.sapientia.other.repository;

//import static org.junit.Assert.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.other.model.FurnitureOther;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class FurnitureOtherRepositoryTest {

	@Autowired
	FurnitureOtherRepository furnitureOtherRepository;

	@Test
	public void myTest() {
		var result = furnitureOtherRepository.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void myTestForFindFirst() {
		FurnitureOther fo = new FurnitureOther();
		fo.setName("cruel");

		var savedFO = furnitureOtherRepository.save(fo);
		var result = furnitureOtherRepository.findAll();
		assertEquals(1, result.size());

		var foundObj = furnitureOtherRepository.findFurnitureOtherById(savedFO.getId());

		assertEquals(savedFO, foundObj);

	}

}
