package ro.sapientia.furniture.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.Door;

public class DoorTest {

	//Ezzel fogom tesztelni a modelt: Door
	
	//Letrehozok a modelbol egy valtozot
	private Door door;
	
	//Beallitgatom a parametereit
	@BeforeEach
	public void setUp() {
		door = new Door(); //letrehozok egy peldanyt
		door.setWidth(80); //80 cm
		door.setHeigth(200); //200 cm
		door.setDepth(3);  //3 cm vagyis a vastagsaga
		door.setColor("brown");  // barna a szine
		door.setMaterial("lumber"); //fabol
		door.setHasGlass(true); // van benne ablak
		door.setNumberOfGlassPanels(3); // 3 uvegpanel van benne
		
		
	}
	//Erre amit beallitottam megirom a tesztet
	//Lekerem get-el hogy azok a parameterek lettek e bealitva
	@Test
	public void testDoorGetters() {
		assertEquals(80,door.getWidth());
		assertEquals(200,door.getHeigth());
		assertEquals("brown",door.getColor());
		assertEquals("lumber",door.getMaterial());
		assertTrue(door.isHasGlass());
		assertEquals(3,door.getNumberOfGlassPanels());

	}
	//Default ertekekre test
	@Test
    public void testDoorDefault() {
		Door default_door = new Door();
		assertNull(default_door.getColor());
		assertNull(default_door.getMaterial());
		assertFalse(default_door.isHasGlass());
		assertEquals(0,default_door.getNumberOfGlassPanels());

	}
	//ellenorzes: ha a nincs benne uveg akkor ay uvegek szama kiadja e a megfelo 0 erteket
	@Test
	public void testNumberOfGlassPanelsWhenNoGlass() {
		//letrehozzuk az ajtot uveg nelkul
	    Door door_test = new Door();
	    door_test.setHasGlass(false);  // nincs uveg
	    door_test.setNumberOfGlassPanels(6);  // 6 paneles uveg

	    //ha nincs uveg akkor a paneles uvegek szama 0 kell legyen
	    assertEquals(0, door_test.getNumberOfGlassPanels());
	}
	
	//ToString test
	@Test
    public void testToString() {
        // Ellenőrizzük, hogy a toString megfelelően működik
        String expectedToString = "Door [id=null, width=80, heigth=200, depth=3, material=lumber, color=brown, hasGlass=true, numberOfGlassPanels =3]";
        assertEquals(expectedToString, door.toString());
    }
	
}
