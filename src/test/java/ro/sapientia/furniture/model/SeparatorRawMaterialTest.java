package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

class SeparatorRawMaterialTest {

    @Test
    void horizontalSeparator_returns_verticalEdge_and_children_materials() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(60);
        fb.setHeigth(30);
        fb.setDepth(20);
        fb.setThickness(10);

        RawMaterialType t = new RawMaterialType("MDF");

        FrontElement parent = new FrontElement(fb, FrontElement.ElementType.BODY, 0, 0, 60, 30, "p", t);
        FrontElement first = new FrontElement(fb, FrontElement.ElementType.DRAWER, 0, 0, 60, 15, "f", t);
        FrontElement second = new FrontElement(fb, FrontElement.ElementType.DRAWER, 0, 15, 60, 15, "s", t);

        Separator sep = new Separator();
        sep.setParent(parent);
        sep.setSeparatorType(Separator.SeparatorType.HORIZONTAL);
        sep.setRelativePosition(0.5);
        sep.setPosX(0);
        sep.setPosY(0);
        sep.setWidth(60);
        sep.setHeight(30);
        sep.setFirstFrontElement(first);
        sep.setSecondFrontElement(second);

        LinkedList<RawMaterial> materials = sep.getMaterials();

        assertNotNull(materials);
        // horizontal separator should add firstFrontElement.getVerticalMaterial() + materials from both children
        assertEquals(materials.size(), 3);

        // verify the first material matches first vertical dimensions
        RawMaterial firstMat = materials.get(0);
        assertEquals(first.getHeight(), firstMat.getHeight());
        assertEquals(fb.getThickness(), firstMat.getWidth());
        assertEquals(fb.getDepth(), firstMat.getLength());
    }

    @Test
    void verticalSeparator_returns_horizontalEdge_and_children_materials() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(60);
        fb.setHeigth(30);
        fb.setDepth(20);
        fb.setThickness(8);

        RawMaterialType t = new RawMaterialType("Pine");

        FrontElement parent = new FrontElement(fb, FrontElement.ElementType.BODY, 0, 0, 60, 30, "p", t);
        FrontElement first = new FrontElement(fb, FrontElement.ElementType.DRAWER, 0, 0, 30, 30, "f", t);
        FrontElement second = new FrontElement(fb, FrontElement.ElementType.DRAWER, 30, 0, 30, 30, "s", t);

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

        LinkedList<RawMaterial> materials = sep.getMaterials();

        assertNotNull(materials);
        // vertical separator should add firstFrontElement.getHorizontalMaterial() + materials from both children
        assertEquals(materials.size(), 3);

        RawMaterial firstMat = materials.get(0);
        assertEquals(fb.getThickness(), firstMat.getHeight());
        assertEquals(first.getWidth(), firstMat.getWidth());
        assertEquals(fb.getDepth(), firstMat.getLength());
    }
}
