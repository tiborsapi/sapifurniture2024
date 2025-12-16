package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ManufacturedComponentTypeTest {

    @Test
    void defaultConstructor_createsEmptyObject() {
        ManufacturedComponentType type = new ManufacturedComponentType();

        assertNotNull(type);
        assertNull(type.getId());
        assertNull(type.getName());
        assertNull(type.getCreatedAt());
        assertNull(type.getUpdatedAt());
    }

    @Test
    void parameterizedConstructor_setsNameAndTimestamps() {
        ManufacturedComponentType type =
                new ManufacturedComponentType("Drawer");

        assertEquals("Drawer", type.getName());
        assertNotNull(type.getCreatedAt());
        assertNotNull(type.getUpdatedAt());
    }

    @Test
    void settersAndGetters_workCorrectly() {
        ManufacturedComponentType type = new ManufacturedComponentType();

        LocalDateTime created = LocalDateTime.now().minusDays(1);
        LocalDateTime updated = LocalDateTime.now();

        type.setId(10L);
        type.setName("Panel");
        type.setCreatedAt(created);
        type.setUpdatedAt(updated);

        assertEquals(10L, type.getId());
        assertEquals("Panel", type.getName());
        assertEquals(created, type.getCreatedAt());
        assertEquals(updated, type.getUpdatedAt());
    }

    @Test
    void prePersist_setsTimestamps_whenNull() {
        ManufacturedComponentType type = new ManufacturedComponentType();

        type.prePersist();

        assertNotNull(type.getCreatedAt());
        assertNotNull(type.getUpdatedAt());
    }

    @Test
    void prePersist_doesNotOverrideExistingTimestamps() {
        ManufacturedComponentType type = new ManufacturedComponentType();

        LocalDateTime created = LocalDateTime.now().minusDays(2);
        LocalDateTime updated = LocalDateTime.now().minusDays(1);

        type.setCreatedAt(created);
        type.setUpdatedAt(updated);

        type.prePersist();

        assertEquals(created, type.getCreatedAt());
        assertEquals(updated, type.getUpdatedAt());
    }

    @Test
    void preUpdate_updatesOnlyUpdatedAt() throws InterruptedException {
        ManufacturedComponentType type = new ManufacturedComponentType();

        LocalDateTime created = LocalDateTime.now().minusDays(1);
        type.setCreatedAt(created);

        type.prePersist();
        LocalDateTime oldUpdated = type.getUpdatedAt();

        Thread.sleep(5);

        type.preUpdate();

        assertEquals(created, type.getCreatedAt());
        assertTrue(type.getUpdatedAt().isAfter(oldUpdated));
    }

    @Test
    void toString_containsAllConcreteFieldValues() {
        ManufacturedComponentType type =
                new ManufacturedComponentType("Shelf");
        type.setId(5L);

        String str = type.toString();

        assertTrue(str.contains("id=5"));
        assertTrue(str.contains("name=Shelf"));
        assertTrue(str.contains("createdAt="));
        assertTrue(str.contains("updatedAt="));
    }

    @Test
    void toString_handlesNullFieldsCorrectly() {
        ManufacturedComponentType type = new ManufacturedComponentType();
        type.setId(3L);

        String str = type.toString();

        assertTrue(str.contains("id=3"));
        assertTrue(str.contains("name=null"));
        assertTrue(str.contains("createdAt=null"));
        assertTrue(str.contains("updatedAt=null"));
    }
}
