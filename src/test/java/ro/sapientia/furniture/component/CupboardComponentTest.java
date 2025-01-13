package ro.sapientia.furniture.component;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.Cupboard;

public class CupboardComponentTest {

    private Cupboard cupboard;

    @BeforeEach
    public void setUp() {
        cupboard = new Cupboard();
        cupboard.setWidth(500);
        cupboard.setHeight(500);
        cupboard.setNumberOfCab(5);
        cupboard.setNumberOfDrawer(5);
    }

    @Test
    public void testCupboardWidth() {
        assertEquals(500, cupboard.getWidth());
        cupboard.setWidth(600);
        assertEquals(600, cupboard.getWidth());
    }

    @Test
    public void testCupboardHeight() {
        assertEquals(500, cupboard.getHeight());
        cupboard.setHeight(600);
        assertEquals(600, cupboard.getHeight());
    }

    @Test
    public void testCupboardCabinets() {
        assertEquals(5, cupboard.getNumberOfCab());
        cupboard.setNumberOfCab(6);
        assertEquals(6, cupboard.getNumberOfCab());
    }

    @Test
    public void testCupboardDrawers() {
        assertEquals(5, cupboard.getNumberOfDrawer());
        cupboard.setNumberOfDrawer(6);
        assertEquals(6, cupboard.getNumberOfDrawer());
    }

    @Test
    public void testToString() {
        String expected = "Cupboard{" +
                "id=" + null +
                ", width=" + 500 +
                ", height=" + 500 +
                ", numberOfCab=" + 5 +
                ", numberOfDrawer=" + 5 +
                '}';
        assertEquals(expected, cupboard.toString());
    }
}