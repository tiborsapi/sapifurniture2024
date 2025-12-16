package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FurnitureBodyRawMaterialTest {

    @Test
    void getRawMaterials_createsComponentList_withSixMaterials_when_noMainElement() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(120);
        fb.setHeigth(90);
        fb.setDepth(40);
        fb.setThickness(18);

        ComponentList cl = fb.getRawMaterials();

        assertNotNull(cl);
        assertNotNull(cl.getRawMaterials());
        assertEquals(6, cl.getRawMaterials().size());

        // Verify quantities
        cl.getRawMaterials().forEach(r -> assertEquals(1, r.getQuantity()));
    }

    @Test
    void getRawMaterials_usesProvidedMainElement_materials_inComponentList() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(60);
        fb.setHeigth(40);
        fb.setDepth(20);
        fb.setThickness(10);

        RawMaterialType type = new RawMaterialType("TestType");
        FrontElement main = new FrontElement(fb, FrontElement.ElementType.BODY, 0, 0, 60, 40, "main", type);
        fb.setMainFrontElement(main);

        ComponentList cl = fb.getRawMaterials();

        assertNotNull(cl);
        assertEquals(6, cl.getRawMaterials().size());

        boolean hasType = cl.getRawMaterials().stream()
                .anyMatch(r -> r.getRawMaterialType() != null && "TestType".equals(r.getRawMaterialType().getName()));

        assertTrue(hasType, "Expected at least one raw material to have the provided RawMaterialType");
    }
}
