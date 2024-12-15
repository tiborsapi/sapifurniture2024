package ro.sapientia.furniture.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ro.sapientia.furniture.model.Drawer;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DrawerComponentTest {

    private Validator validator;
    private Drawer drawer;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        drawer = Drawer.builder()
                .material("Wood")
                .color("Brown")
                .height(15.0)
                .width(50.0)
                .depth(45.0)
                .weight(5.0)
                .isOpen(false)
                .maxOpenDistance(40.0)
                .currentOpenDistance(0.0)
                .weightCapacity(25.0)
                .status(Drawer.DrawerStatus.FUNCTIONAL)
                .description("Test drawer")
                .build();
    }

    @Test
    void whenDrawerIsValid_thenNoValidationViolations() {
        var violations = validator.validate(drawer);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenMaterialIsNull_thenValidationFails() {
        drawer.setMaterial(null);
        var violations = validator.validate(drawer);
        assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, 0.0})
    void whenDimensionsAreNotPositive_thenValidationFails(double invalidValue) {
        drawer.setHeight(invalidValue);
        assertThrows(IllegalStateException.class, () -> drawer.prePersist());
    }

    @Test
    void whenWeightExceedsCapacity_thenValidationFails() {
        drawer.setWeight(30.0); // Weight > weightCapacity (25.0)
        assertThrows(IllegalStateException.class, () -> drawer.prePersist());
    }

    @Test
    void openDrawer_WhenFunctional() {
        drawer.open();
        assertTrue(drawer.isOpen());
        assertEquals(drawer.getMaxOpenDistance(), drawer.getCurrentOpenDistance());
    }

    @Test
    void openDrawer_WhenDamaged() {
        drawer.setStatus(Drawer.DrawerStatus.DAMAGED);
        drawer.open();
        assertFalse(drawer.isOpen());
        assertEquals(0.0, drawer.getCurrentOpenDistance());
    }

    @Test
    void closeDrawer_WhenOpen() {
        drawer.setOpen(true);
        drawer.setCurrentOpenDistance(drawer.getMaxOpenDistance());

        drawer.close();
        assertFalse(drawer.isOpen());
        assertEquals(0.0, drawer.getCurrentOpenDistance());
    }

    @Test
    void slidePartially_WithValidDistance() {
        double partialDistance = 20.0;
        drawer.slidePartially(partialDistance);
        assertTrue(drawer.isOpen());
        assertEquals(partialDistance, drawer.getCurrentOpenDistance());
    }

    @Test
    void slidePartially_WithInvalidDistance() {
        double invalidDistance = drawer.getMaxOpenDistance() + 10.0;
        drawer.slidePartially(invalidDistance);
        // Should maintain previous state
        assertFalse(drawer.isOpen());
        assertEquals(0.0, drawer.getCurrentOpenDistance());
    }

    @Test
    void prePersist_SetsDefaultStatus() {
        Drawer newDrawer = Drawer.builder()
                .material("Wood")
                .color("Brown")
                .height(15.0)
                .width(50.0)
                .depth(45.0)
                .weight(5.0)
                .maxOpenDistance(40.0)
                .currentOpenDistance(0.0)
                .weightCapacity(25.0)
                .build();

        newDrawer.prePersist();
        assertEquals(Drawer.DrawerStatus.FUNCTIONAL, newDrawer.getStatus());
    }

    @Test
    void validateVersioning() {
        assertNull(drawer.getVersion());
        drawer.setVersion(1L);
        assertEquals(1L, drawer.getVersion());
    }

    @Test
    void validateTimestamps() {
        LocalDateTime now = LocalDateTime.now();
        drawer.setCreatedAt(now);
        drawer.setUpdatedAt(now);

        assertEquals(now, drawer.getCreatedAt());
        assertEquals(now, drawer.getUpdatedAt());
    }

    @Test
    void builderTest() {
        Drawer builtDrawer = Drawer.builder()
                .material("Metal")
                .color("Silver")
                .height(20.0)
                .width(60.0)
                .depth(50.0)
                .weight(8.0)
                .isOpen(false)
                .maxOpenDistance(45.0)
                .currentOpenDistance(0.0)
                .weightCapacity(30.0)
                .status(Drawer.DrawerStatus.FUNCTIONAL)
                .description("Built with builder")
                .build();

        assertNotNull(builtDrawer);
        assertEquals("Metal", builtDrawer.getMaterial());
        assertEquals("Silver", builtDrawer.getColor());
    }

    @Test
    void equalsAndHashCodeTest() {
        Drawer drawer1 = Drawer.builder()
                .id(1L)
                .material("Wood")
                .build();

        Drawer drawer2 = Drawer.builder()
                .id(1L)
                .material("Wood")
                .build();

        assertEquals(drawer1, drawer2);
        assertEquals(drawer1.hashCode(), drawer2.hashCode());
    }

    @Test
    void toStringTest() {
        String drawerString = drawer.toString();
        assertTrue(drawerString.contains("material=" + drawer.getMaterial()));
        assertTrue(drawerString.contains("color=" + drawer.getColor()));
    }
}