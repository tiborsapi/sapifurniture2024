package ro.sapientia.chair.repository;

import static org.junit.Assert.assertEquals;

//import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.chair.model.FurnitureChair;
import ro.sapientia.chair.service.FurnitureChairService;
import ro.sapientia.furniture.FurnitureApplication;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
@ContextConfiguration(classes = FurnitureApplication.class)
public class FurnitureChairRepositoryTest {

	@Autowired
	@MockBean(FurnitureChairRepository.class)
	FurnitureChairRepository furnitureChairRepository;

	@Test
	public void myTest() {
		var result = furnitureChairRepository.findAll();
		assertEquals(0, result.size());
	}

	@Test
	public void myTestForFindFirst() {
		FurnitureChair fo = new FurnitureChair();
		fo.setName("cruel");

		var savedFO = furnitureChairRepository.save(fo);
		var result = furnitureChairRepository.findAll();
		assertEquals(1, result.size());

		var foundObj = furnitureChairRepository.findFurnitureChairById(savedFO.getId());

		assertEquals(savedFO, foundObj);

	}

}
