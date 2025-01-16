package ro.sapientia.furniture.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.BabyCotBody;

public class BabyCotBodyComponentTest {

	private BabyCotBody babyCot;
	
	@BeforeEach
	public void setUp() {
		babyCot = new BabyCotBody();
		
		babyCot.setHeigth(7);
		babyCot.setWidth(3);
		babyCot.setDepth(8);
		babyCot.setMaxAge(10);
		babyCot.setRating(9);
	}
	
	@Test
	public void testBabyCotGetters() {
		assertEquals(7, babyCot.getHeigth());
		assertEquals(3, babyCot.getWidth());
		assertEquals(8, babyCot.getDepth());
		assertEquals(10, babyCot.getMaxAge());
		assertEquals(9, babyCot.getRating());
	}
	
	@Test
	public void testBabyCotDefaults() {
		BabyCotBody babyCot = new BabyCotBody();
		
		assertEquals(0, babyCot.getHeigth());
		assertEquals(0, babyCot.getWidth());
		assertEquals(0, babyCot.getDepth());
		assertEquals(0, babyCot.getMaxAge());
		assertEquals(0, babyCot.getRating());
	}
	
	@Test
	public void testBabyCotArea() {		
		assertEquals(24, babyCot.getWidth() * babyCot.getDepth());
	}
	
	@Test
	public void testBabyCotToString() {
		String expectedToString = "BabyCotBody [id=null, width=3, heigth=7, depth=8, maxAge=10, rating=9]";
		
		assertEquals(expectedToString, babyCot.toString());
	}

}
