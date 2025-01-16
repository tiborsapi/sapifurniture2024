package ro.sapientia.furniture.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.FurnitureWineRack;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class FurnitureWineRackRepositoryTest {

	@Autowired
	FurnitureWineRackRepository furnitureWineRackRepository;

	@Test
	public void findAllWineRacks() {
		FurnitureWineRack wineRack1 = new FurnitureWineRack();

		wineRack1.setDepth(20);
		wineRack1.setHeigth(100);
		wineRack1.setWidth(60);
		wineRack1.setCapacity(100);

		FurnitureWineRack wineRack2 = new FurnitureWineRack();
		wineRack2.setDepth(30);
		wineRack2.setHeigth(120);
		wineRack2.setWidth(80);
		wineRack2.setCapacity(200);

		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);

		var result = furnitureWineRackRepository.findAll();

		assertEquals(result.size(), 2);
	}
	
	@Test
	public void findAllFurnitureWineRacksByCapacityBetween() {
		FurnitureWineRack wineRack1 = new FurnitureWineRack();
		wineRack1.setDepth(20);
		wineRack1.setHeigth(100);
		wineRack1.setWidth(60);
		wineRack1.setCapacity(100);

		FurnitureWineRack wineRack2 = new FurnitureWineRack();
		wineRack2.setDepth(30);
		wineRack2.setHeigth(120);
		wineRack2.setWidth(80);
		wineRack2.setCapacity(200);

		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);

		var result = furnitureWineRackRepository.findAllFurnitureWineRackByCapacityBetween(0,300);

		assertEquals(result.size(), 2);
	}

	@Test
	public void findAllFurnitureWineRacksByCurrentLoadBetween() {
		FurnitureWineRack wineRack1 = new FurnitureWineRack();
		wineRack1.setDepth(20);
		wineRack1.setHeigth(100);
		wineRack1.setWidth(60);
		wineRack1.setCurrentLoad(100);

		FurnitureWineRack wineRack2 = new FurnitureWineRack();
		wineRack2.setDepth(30);
		wineRack2.setHeigth(120);
		wineRack2.setWidth(80);
		wineRack2.setCurrentLoad(200);

		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);

		var result = furnitureWineRackRepository.findAllFurnitureWineRackByCurrentLoadBetween(0,300);

		assertEquals(result.size(), 2);
	}

	@Test
	public void findAllFurnitureWineRacksByTransparentFront() {
		FurnitureWineRack wineRack1 = new FurnitureWineRack();
		wineRack1.setDepth(20);
		wineRack1.setHeigth(100);
		wineRack1.setWidth(60);
		wineRack1.setTransparentFront(true);

		FurnitureWineRack wineRack2 = new FurnitureWineRack();
		wineRack2.setDepth(30);
		wineRack2.setHeigth(120);
		wineRack2.setWidth(80);
		wineRack2.setTransparentFront(false);

		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);

		var result = furnitureWineRackRepository.findAllFurnitureWineRackByTransparentFront(true);

		assertEquals(result.size(), 1);
	}




}
