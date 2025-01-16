package ro.sapientia.chair.integration;
import static org.junit.Assert.assertEquals;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Test;
import ro.sapientia.chair.model.FurnitureChair;
import ro.sapientia.chair.repository.FurnitureChairRepository;
import ro.sapientia.chair.service.FurnitureChairService;
import ro.sapientia.furniture.FurnitureApplication;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
@ContextConfiguration(classes = FurnitureApplication.class)
@EntityScan("ro.sapientia")
public class FurnitureChairIntegrationTest {

	@Autowired
	private FurnitureChairService chairService;

	@Autowired
	private FurnitureChairRepository chairRepository;

	@Test
	public void testSaveAndRetrieveChair() {
		// Create test data
		FurnitureChair chair = new FurnitureChair();
		chair.setName("testChair");
		chair.setNumOfLegs(4);
		chair.setMaterial("wood");

		// Save using service
		chairService.create(chair);

		// Retrieve using repository
		List<FurnitureChair> chairs = chairRepository.findAll();
		assertEquals(1, chairs.size());
		assertEquals("testChair", chairs.get(0).getName());
		assertEquals(4, chairs.get(0).getNumOfLegs());

	}
}