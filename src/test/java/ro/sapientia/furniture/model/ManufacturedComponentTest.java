package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ManufacturedComponentTest {

    @Test
    void defaultConstructor_createsEmptyObject() {
        ManufacturedComponent mc = new ManufacturedComponent();

        assertNotNull(mc);
        assertNull(mc.getId());
        assertNull(mc.getComponentList());
        assertNull(mc.getManufacturedComponentType());
        assertNull(mc.getQuantity());
        assertNull(mc.getCreatedAt());
        assertNull(mc.getUpdatedAt());
    }

    @Test
    void parameterizedConstructor_setsFieldsAndTimestamps() {
        ComponentList cl = new ComponentList();
        ManufacturedComponentType type = new ManufacturedComponentType();
        type.setName("TestType");

        ManufacturedComponent mc =
                new ManufacturedComponent(cl, type, 5);

        assertEquals(cl, mc.getComponentList());
        assertEquals(type, mc.getManufacturedComponentType());
        assertEquals(5, mc.getQuantity());

        assertNotNull(mc.getCreatedAt());
        assertNotNull(mc.getUpdatedAt());
    }

    @Test
    void settersAndGetters_workCorrectly() {
        ManufacturedComponent mc = new ManufacturedComponent();

        ComponentList cl = new ComponentList();
        ManufacturedComponentType type = new ManufacturedComponentType();
        type.setName("Panel");

        LocalDateTime created = LocalDateTime.now().minusDays(1);
        LocalDateTime updated = LocalDateTime.now();

        mc.setId(10L);
        mc.setComponentList(cl);
        mc.setManufacturedComponentType(type);
        mc.setQuantity(12);
        mc.setCreatedAt(created);
        mc.setUpdatedAt(updated);

        assertEquals(10L, mc.getId());
        assertEquals(cl, mc.getComponentList());
        assertEquals(type, mc.getManufacturedComponentType());
        assertEquals(12, mc.getQuantity());
        assertEquals(created, mc.getCreatedAt());
        assertEquals(updated, mc.getUpdatedAt());
    }

    @Test
    void prePersist_setsTimestamps_whenNull() {
        ManufacturedComponent mc = new ManufacturedComponent();

        mc.prePersist();

        assertNotNull(mc.getCreatedAt());
        assertNotNull(mc.getUpdatedAt());
    }

    @Test
    void prePersist_doesNotOverrideExistingTimestamps() {
        ManufacturedComponent mc = new ManufacturedComponent();

        LocalDateTime created = LocalDateTime.now().minusDays(2);
        LocalDateTime updated = LocalDateTime.now().minusDays(1);

        mc.setCreatedAt(created);
        mc.setUpdatedAt(updated);

        mc.prePersist();

        assertEquals(created, mc.getCreatedAt());
        assertEquals(updated, mc.getUpdatedAt());
    }

    @Test
    void preUpdate_updatesOnlyUpdatedAt() throws InterruptedException {
        ManufacturedComponent mc = new ManufacturedComponent();

        LocalDateTime created = LocalDateTime.now().minusDays(1);
        mc.setCreatedAt(created);

        mc.prePersist();
        LocalDateTime oldUpdated = mc.getUpdatedAt();

        Thread.sleep(5);

        mc.preUpdate();

        assertEquals(created, mc.getCreatedAt());
        assertTrue(mc.getUpdatedAt().isAfter(oldUpdated));
    }

    @Test
    void toString_containsAllConcreteFieldValues() {
        ComponentList cl = new ComponentList();
        cl.setId(99L);

        ManufacturedComponentType type = new ManufacturedComponentType();
        type.setName("Drawer");

        ManufacturedComponent mc =
                new ManufacturedComponent(cl, type, 3);
        mc.setId(1L);

        String str = mc.toString();

        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("componentList=99"));
        assertTrue(str.contains("manufacturedComponentType=Drawer"));
        assertTrue(str.contains("quantity=3"));
        assertTrue(str.contains("createdAt="));
        assertTrue(str.contains("updatedAt="));
    }

    @Test
    void toString_handlesNullReferencesCorrectly() {
        ManufacturedComponent mc = new ManufacturedComponent();
        mc.setId(5L);
        mc.setQuantity(0);

        String str = mc.toString();

        assertTrue(str.contains("id=5"));
        assertTrue(str.contains("componentList=null"));
        assertTrue(str.contains("manufacturedComponentType=null"));
        assertTrue(str.contains("quantity=0"));
    }
}
