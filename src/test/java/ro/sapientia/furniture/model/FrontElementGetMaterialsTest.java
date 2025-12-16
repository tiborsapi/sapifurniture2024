package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

class FrontElementGetMaterialsTest {

    @Test
    void getMaterials_withoutSeparator_returnsOppositeMaterialOnly() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(80);
        fb.setHeigth(60);
        fb.setDepth(30);
        fb.setThickness(18);

        RawMaterialType t = new RawMaterialType("TestMat");

        FrontElement el = new FrontElement(fb, FrontElement.ElementType.DRAWER, 0, 0, 40, 20, "el", t);

        LinkedList<RawMaterial> materials = el.getMaterials();

        assertNotNull(materials);
        assertEquals(1, materials.size(), "Expect single opposite material when no separator present");

        RawMaterial opp = el.getOppositeMaterial();
        RawMaterial got = materials.get(0);

        assertEquals(opp.getHeight(), got.getHeight());
        assertEquals(opp.getWidth(), got.getWidth());
        assertEquals(opp.getLength(), got.getLength());
        assertEquals(opp.getQuantity(), got.getQuantity());
    }

    @Test
    void getMaterials_withSeparator_delegatesToSeparatorMaterials() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(60);
        fb.setHeigth(30);
        fb.setDepth(20);
        fb.setThickness(10);

        RawMaterialType t = new RawMaterialType("EdgeMat");

        FrontElement parent = new FrontElement(fb, FrontElement.ElementType.BODY, 0, 0, 60, 30, "parent", t);
        FrontElement first = new FrontElement(fb, FrontElement.ElementType.DRAWER, 0, 0, 30, 30, "first", t);
        FrontElement second = new FrontElement(fb, FrontElement.ElementType.DRAWER, 30, 0, 30, 30, "second", t);

        Separator sep = new Separator();
        sep.setParent(parent);
        sep.setSeparatorType(Separator.SeparatorType.VERTICAL);
        sep.setRelativePosition(0.5);
        sep.setPosX(0);
        sep.setPosY(0);
        sep.setWidth(60);
        sep.setHeight(30);
        sep.setFirstFrontElement(first);
        sep.setSecondFrontElement(second);

        parent.setSeparator(sep);

        LinkedList<RawMaterial> fromFront = parent.getMaterials();
        LinkedList<RawMaterial> fromSep = sep.getMaterials();

        assertNotNull(fromFront);
        assertNotNull(fromSep);
        assertEquals(fromSep.size(), fromFront.size(), "Parent getMaterials should delegate to separator.getMaterials");

        // verify first element equality
        RawMaterial a = fromFront.get(0);
        RawMaterial b = fromSep.get(0);
        assertEquals(a.getHeight(), b.getHeight());
        assertEquals(a.getWidth(), b.getWidth());
        assertEquals(a.getLength(), b.getLength());
    }
}
