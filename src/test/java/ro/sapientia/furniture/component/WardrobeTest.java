package ro.sapientia.furniture.component;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.Wardrobe;

public class WardrobeTest {

    private Wardrobe wardrobe;

    @BeforeEach
    public void setUp() {
        wardrobe = new Wardrobe();
        wardrobe.setWidth(100);
        wardrobe.setHeigth(200);
        wardrobe.setDepth(60);
        wardrobe.setNumberOfDoors(3);
        wardrobe.setHasMirror(true);
        wardrobe.setNumberOfShelves(4);
    }

    @Test
    public void testWardrobeGetters() {
        assertEquals(100, wardrobe.getWidth());
        assertEquals(200, wardrobe.getHeigth());
        assertEquals(60, wardrobe.getDepth());
        assertEquals(3, wardrobe.getNumberOfDoors());
        assertTrue(wardrobe.isHasMirror());
        assertEquals(4, wardrobe.getNumberOfShelves());
    }

    @Test
    public void testWardrobeDefaults() {
        Wardrobe defaultWardrobe = new Wardrobe();
        assertEquals(0, defaultWardrobe.getNumberOfDoors());
        assertFalse(defaultWardrobe.isHasMirror());
        assertEquals(0, defaultWardrobe.getNumberOfShelves());
    }

    @Test
    public void testCalculateShelfHeight() {
        wardrobe.setNumberOfShelves(4);
        assertEquals(50.0, wardrobe.calculateShelfHeight());

        wardrobe.setNumberOfShelves(0);
        assertEquals(0.0, wardrobe.calculateShelfHeight());

        wardrobe.setNumberOfShelves(-1);
        assertEquals(0.0, wardrobe.calculateShelfHeight());
    }

    @Test
    public void testToString() {
        String expected = "Wardrobe [id=null, width=100, height=200, depth=60, numberOfDoors=3, hasMirror=true, numberOfShelves=4]";
        assertEquals(expected, wardrobe.toString());
    }
}
