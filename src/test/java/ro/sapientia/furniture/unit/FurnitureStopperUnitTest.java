package ro.sapientia.furniture.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.FurnitureStopper;

public class FurnitureStopperUnitTest {

    private FurnitureStopper stopper;

    @BeforeEach
    public void setup() {
        FurnitureBody fb = new FurnitureBody();

        stopper = new FurnitureStopper();
        stopper.setActive(true);
        stopper.setStartTimeMs(500);
        stopper.setStopTimeMs(1000);
        stopper.setFurnitureBody(fb);
    }

    @Test
    public void dataShouldBeConsistent() {
        assertEquals(true, stopper.isActive());
        assertEquals(500, stopper.getStartTimeMs());
        assertEquals(1000, stopper.getStopTimeMs());
    }

    @Test
    void whenEnoughTimeHasPassed() {
        // Simulate that enough time has passed for state change
        long currentTime = System.currentTimeMillis();
        stopper.setStartTimeMs(currentTime - 1000); // 1000 ms ago

        // Test that the FurnitureStopper can change state
        assertTrue(stopper.canChangeState());
    }

    @Test
    void whenNotEnoughTimeHasPassed() {
        // Simulate that not enough time has passed
        long currentTime = System.currentTimeMillis();
        stopper.setStartTimeMs(currentTime - 999); // 999 ms ago

        // Test that the FurnitureStopper cannot change state
        assertFalse(stopper.canChangeState());
    }
}
