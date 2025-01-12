package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.Door;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class DoorRepositoryTest {
	
	//DoorRepository - val letrehozok egy valtozot 
	@Autowired
	DoorRepository repository;
	
	//ez a test az adatbazis lekerdezesenek eredmenyet ellenorzi -> a rekordok szam a 0
	@Test
	public void myTest() {
		var result = repository.findAll();
		assertEquals(0, result.size());
	}
	//ez a test a Door menteset és lekerdezeset vizsgalja egy adatbazisban
	@Test
	public void myTestForFindFirst() {
		//letrehozunk egy obijektumot
		Door door = new Door();
		//beallitjuk a parametereit
		door.setHeigth(200);
		door.setWidth(90);
		door.setDepth(3);
		door.setColor("brown");
		door.setMaterial("lumber");
		door.setHasGlass(true);
		door.setNumberOfGlassPanels(3);
		//elmentjuk a respositoryba
		var savedD = repository.save(door);
		//majd lekrjuk
		var result = repository.findAll();
		//ellenorizzuk azzal hogy lekerjuk a meretet
		assertEquals(1, result.size());
	
		//az talalt obijektum megfelel-e az elmentett obijectumnak
		var foundObj = repository.findDoorById(savedD.getId());	
		assertEquals(savedD, foundObj);
	}
	

}
