package ro.sapientia.furniture.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FrontElementRawMaterialTest {

	private FurnitureBody furnitureBody;
	private FrontElement frontElement;
	private RawMaterialType rawMaterialType;

	@BeforeEach
	void setUp() {
		// Create a basic furniture body
		furnitureBody = new FurnitureBody();
		furnitureBody.setId(1L);
		furnitureBody.setHeigth(100);
		furnitureBody.setWidth(80);
		furnitureBody.setDepth(40);
		furnitureBody.setThickness(18);

		// Create a raw material type
		rawMaterialType = new RawMaterialType("Test Material");

		// Create a front element without separator
		frontElement = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			0, 
			0, 
			60, 
			30, 
			"Test element",
			rawMaterialType
		);
	}

	@Test
	void testGetRawMaterialsWithoutSeparator() {
		// When separator is null, should return 6 materials
		LinkedList<RawMaterial> materials = frontElement.getRawMaterials();
		
		assertNotNull(materials);
		assertEquals(6, materials.size());
		
		// Check that all materials have the correct raw material type
		for (RawMaterial material : materials) {
			assertEquals(rawMaterialType, material.getRawMaterialType());
			assertEquals(1, material.getQuantity());
		}
	}

	@Test
	void testGetRawMaterialsWithSeparator() {
		// Create a first front element for separator
		FrontElement firstElement = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			0, 0, 30, 30, 
			"First element", 
			rawMaterialType
		);
		
		// Create a second front element for separator  
		FrontElement secondElement = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			30, 0, 30, 30,
			"Second element", 
			rawMaterialType
		);

		// Create a separator with proper setup
		Separator separator = new Separator();
		separator.setParent(frontElement);
		separator.setSeparatorType(Separator.SeparatorType.VERTICAL);
		separator.setRelativePosition(0.5);
		separator.setPosX(0);
		separator.setPosY(0);
		separator.setWidth(60);
		separator.setHeight(30);
		separator.setFirstFrontElement(firstElement);
		separator.setSecondFrontElement(secondElement);
		
		frontElement.setSeparator(separator);
		
		// When separator is present, should return separator's materials
		LinkedList<RawMaterial> materials = frontElement.getRawMaterials();
		
		assertNotNull(materials);
		// Should contain 7 materials
		assertEquals(7, materials.size());
	}

	@Test
	void testGetRawMaterialsWithNullRawMaterialType() {
		// Create front element without raw material type
		FrontElement elementWithoutType = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.BODY,
			10, 
			10, 
			50, 
			25
		);
		
		LinkedList<RawMaterial> materials = elementWithoutType.getRawMaterials();
		
		assertNotNull(materials);
		assertEquals(6, materials.size());
		
		// Check that materials can be created even without raw material type
		for (RawMaterial material : materials) {
			assertEquals(1, material.getQuantity());
		}
	}

	@Test
	void testGetHorizontalMaterial() {
		RawMaterial horizontal = frontElement.getHorizontalMaterial();
		
		assertNotNull(horizontal);
		assertEquals(furnitureBody.getThickness(), horizontal.getHeight());
		assertEquals(frontElement.getWidth(), horizontal.getWidth());
		assertEquals(furnitureBody.getDepth(), horizontal.getLength());
		assertEquals(rawMaterialType, horizontal.getRawMaterialType());
		assertEquals(1, horizontal.getQuantity());
	}

	@Test
	void testGetVerticalMaterial() {
		RawMaterial vertical = frontElement.getVerticalMaterial();
		
		assertNotNull(vertical);
		assertEquals(frontElement.getHeight(), vertical.getHeight());
		assertEquals(furnitureBody.getThickness(), vertical.getWidth());
		assertEquals(furnitureBody.getDepth(), vertical.getLength());
		assertEquals(rawMaterialType, vertical.getRawMaterialType());
		assertEquals(1, vertical.getQuantity());
	}

	@Test
	void testGetOppositeMaterial() {
		RawMaterial opposite = frontElement.getOppositeMaterial();
		
		assertNotNull(opposite);
		assertEquals(frontElement.getHeight(), opposite.getHeight());
		assertEquals(frontElement.getWidth(), opposite.getWidth());
		assertEquals(furnitureBody.getThickness(), opposite.getLength());
		assertEquals(rawMaterialType, opposite.getRawMaterialType());
		assertEquals(1, opposite.getQuantity());
	}

	@Test
	void testGetRawMaterialsComposition() {
		LinkedList<RawMaterial> materials = frontElement.getRawMaterials();
		
		// Should contain: 2 horizontal, 2 vertical, 2 opposite materials
		assertEquals(6, materials.size());
		
		// First two should be horizontal materials
		RawMaterial horizontal1 = materials.get(0);
		RawMaterial horizontal2 = materials.get(1);
		assertEquals(horizontal1.getHeight(), horizontal2.getHeight());
		assertEquals(horizontal1.getWidth(), horizontal2.getWidth());
		assertEquals(horizontal1.getLength(), horizontal2.getLength());
		
		// Next two should be vertical materials
		RawMaterial vertical1 = materials.get(2);
		RawMaterial vertical2 = materials.get(3);
		assertEquals(vertical1.getHeight(), vertical2.getHeight());
		assertEquals(vertical1.getWidth(), vertical2.getWidth());
		assertEquals(vertical1.getLength(), vertical2.getLength());
		
		// Last two should be opposite materials
		RawMaterial opposite1 = materials.get(4);
		RawMaterial opposite2 = materials.get(5);
		assertEquals(opposite1.getHeight(), opposite2.getHeight());
		assertEquals(opposite1.getWidth(), opposite2.getWidth());
		assertEquals(opposite1.getLength(), opposite2.getLength());
	}

	@Test
	void testElementTypeEnum() {
		// Test different element types
		FrontElement doorElement = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DOOR,
			0, 0, 40, 80
		);
		assertEquals(FrontElement.ElementType.DOOR, doorElement.getElementType());
		
		FrontElement bodyElement = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.BODY,
			0, 0, 60, 30
		);
		assertEquals(FrontElement.ElementType.BODY, bodyElement.getElementType());
		
		FrontElement drawerElement = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			0, 0, 50, 20
		);
		assertEquals(FrontElement.ElementType.DRAWER, drawerElement.getElementType());
	}

	@Test
	void testGetRawMaterialsWithNestedSeparators() {
		// Create first level separator elements
		FrontElement firstLevelLeft = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			0, 0, 30, 30, 
			"First level left", 
			rawMaterialType
		);
		
		FrontElement firstLevelRight = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			30, 0, 30, 30,
			"First level right", 
			rawMaterialType
		);

		// Create first level separator (main separator)
		Separator mainSeparator = new Separator();
		mainSeparator.setParent(frontElement);
		mainSeparator.setSeparatorType(Separator.SeparatorType.VERTICAL);
		mainSeparator.setRelativePosition(0.5);
		mainSeparator.setPosX(0);
		mainSeparator.setPosY(0);
		mainSeparator.setWidth(60);
		mainSeparator.setHeight(30);
		mainSeparator.setFirstFrontElement(firstLevelLeft);
		mainSeparator.setSecondFrontElement(firstLevelRight);
		
		frontElement.setSeparator(mainSeparator);

		// Create second level separators for the left element
		FrontElement leftTop = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			0, 0, 30, 15,
			"Left top", 
			rawMaterialType
		);
		
		FrontElement leftBottom = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			0, 15, 30, 15,
			"Left bottom", 
			rawMaterialType
		);

		Separator leftSeparator = new Separator();
		leftSeparator.setParent(firstLevelLeft);
		leftSeparator.setSeparatorType(Separator.SeparatorType.HORIZONTAL);
		leftSeparator.setRelativePosition(0.5);
		leftSeparator.setPosX(0);
		leftSeparator.setPosY(0);
		leftSeparator.setWidth(30);
		leftSeparator.setHeight(30);
		leftSeparator.setFirstFrontElement(leftTop);
		leftSeparator.setSecondFrontElement(leftBottom);
		
		firstLevelLeft.setSeparator(leftSeparator);

		// Create second level separators for the right element
		FrontElement rightTop = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			30, 0, 30, 15,
			"Right top", 
			rawMaterialType
		);
		
		FrontElement rightBottom = new FrontElement(
			furnitureBody, 
			FrontElement.ElementType.DRAWER,
			30, 15, 30, 15,
			"Right bottom", 
			rawMaterialType
		);

		Separator rightSeparator = new Separator();
		rightSeparator.setParent(firstLevelRight);
		rightSeparator.setSeparatorType(Separator.SeparatorType.HORIZONTAL);
		rightSeparator.setRelativePosition(0.5);
		rightSeparator.setPosX(30);
		rightSeparator.setPosY(0);
		rightSeparator.setWidth(30);
		rightSeparator.setHeight(30);
		rightSeparator.setFirstFrontElement(rightTop);
		rightSeparator.setSecondFrontElement(rightBottom);
		
		firstLevelRight.setSeparator(rightSeparator);

		// Test the nested structure
		LinkedList<RawMaterial> materials = frontElement.getRawMaterials();
		
		assertNotNull(materials);
		// Should contain materials from all nested separators
		assertEquals(9, materials.size());

		// Check that all materials have the correct raw material type
		for (RawMaterial material : materials) {
			assertNotNull(material.getRawMaterialType());
		}
	}
}