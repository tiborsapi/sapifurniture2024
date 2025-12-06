package ro.sapientia.furniture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.exception.CutOptimizationException;
import ro.sapientia.furniture.model.dto.CutRequestDTO;
import ro.sapientia.furniture.model.dto.CutResponseDTO;
import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.model.dto.PlacedElementDTO;

public class CutOptimizationServiceTest {

    private CutOptimizationService service;

    @BeforeEach
    void setup() {
        service = new CutOptimizationService();
    }

    @Test
    void optimizeCutting_placesAllWithoutRotation() {
        FurnitureBodyDTO e1 = new FurnitureBodyDTO(); e1.setId(1L); e1.setWidth(10); e1.setHeight(10);
        FurnitureBodyDTO e2 = new FurnitureBodyDTO(); e2.setId(2L); e2.setWidth(15); e2.setHeight(10);
        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(30); req.setSheetHeight(20);
        req.setElements(List.of(e1, e2));

        CutResponseDTO resp = service.optimizeCutting(req);
        assertEquals(2, resp.getPlacements().size());
        assertEquals(1L, resp.getPlacements().get(0).getId());
        assertEquals(2L, resp.getPlacements().get(1).getId());
    }

    @Test
    void optimizeCutting_usesRotationWhenNeeded() {
        FurnitureBodyDTO e1 = new FurnitureBodyDTO(); e1.setId(1L); e1.setWidth(20); e1.setHeight(10);
        FurnitureBodyDTO e2 = new FurnitureBodyDTO(); e2.setId(2L); e2.setWidth(15); e2.setHeight(20);
        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(20); req.setSheetHeight(40);
        req.setElements(List.of(e1, e2));

        CutResponseDTO resp = service.optimizeCutting(req);
        assertEquals(2, resp.getPlacements().size());
        resp.getPlacements().forEach(p -> {
            assertTrue(p.getX() >= 0 && p.getY() >= 0);
            assertTrue(p.getWidth() <= 20 && p.getHeight() <= 40);
        });
    }

    @Test
    void optimizeCutting_throwsWhenNoElements() {
        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(10); req.setSheetHeight(10);
        req.setElements(List.of());
        assertThrows(CutOptimizationException.class, () -> service.optimizeCutting(req));
    }

    @Test
    void optimizeCutting_throwsTooLargeForSheet() {
        FurnitureBodyDTO e1 = new FurnitureBodyDTO(); e1.setId(1L); e1.setWidth(100); e1.setHeight(100);
        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(50); req.setSheetHeight(50);
        req.setElements(List.of(e1));
        assertThrows(CutOptimizationException.class, () -> service.optimizeCutting(req));
    }

    /**
     * This tests the branch in performFFDHPacking where elements would fit individually
     * (validateRequest passes them), but due to total area or shape, they no longer fit on the sheet.
     * This covers the "if (!placed)" branch.
     */
    @Test
    void optimizeCutting_throwsWhenElementsDoNotFitAlgorithmically() {
        // Sheet size: 10x10 (Area: 100)
        // Element 1: 6x10 (Area: 60)
        // Element 2: 6x10 (Area: 60)
        // Total 120, so impossible to place, but valid individually.
        FurnitureBodyDTO e1 = new FurnitureBodyDTO(); e1.setId(1L); e1.setWidth(6); e1.setHeight(10);
        FurnitureBodyDTO e2 = new FurnitureBodyDTO(); e2.setId(2L); e2.setWidth(6); e2.setHeight(10);

        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(10); req.setSheetHeight(10);
        req.setElements(List.of(e1, e2));

        assertThrows(CutOptimizationException.class, () -> service.optimizeCutting(req));
    }

    /**
     * This test uses Reflection to access the PRIVATE validatePlacements method.
     * Goal: To cover the "overlap" error branch.
     */
    @Test
    void validatePlacements_throwsOnOverlap_viaReflection() throws Exception {
        // Two elements starting at the same position (0,0) -> Overlap!
        PlacedElementDTO p1 = new PlacedElementDTO(1L, 0, 0, 10, 10);
        PlacedElementDTO p2 = new PlacedElementDTO(2L, 5, 5, 10, 10);

        // Reflection call
        Method method = CutOptimizationService.class.getDeclaredMethod("validatePlacements", List.class, int.class, int.class);
        method.setAccessible(true); // Making private method accessible

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(service, List.of(p1, p2), 100, 100);
        });

        // Verify that the wrapped exception is indeed CutOptimizationException
        assertTrue(exception.getCause() instanceof CutOptimizationException);
        assertTrue(exception.getCause().getMessage().contains("overlap"));
    }

    /**
     * This test uses Reflection to access the PRIVATE validatePlacements method.
     * Goal: To cover the "out of bounds" error branch.
     */
    @Test
    void validatePlacements_throwsOutOfBounds_viaReflection() throws Exception {
        // Element beyond the sheet edge (X=95, Width=10 -> End=105, Sheet=100)
        PlacedElementDTO p1 = new PlacedElementDTO(1L, 95, 0, 10, 10);

        // Reflection call
        Method method = CutOptimizationService.class.getDeclaredMethod("validatePlacements", List.class, int.class, int.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(service, List.of(p1), 100, 100);
        });

        assertTrue(exception.getCause() instanceof CutOptimizationException);
        assertTrue(exception.getCause().getMessage().contains("out of sheet bounds"));
    }

    /**
     * Forces rotation within the SAME level.
     * Sheet: 100x100
     * Element 1: 50x100 (This is first after sorting, occupies the left side)
     * Element 2: 100x50 (This is second. Original shape is 100 wide, but only 50 space remains. Rotated, it's 50 wide -> Fits!)
     */
    @Test
    void optimizeCutting_forcesRotationInSameLevel() {
        FurnitureBodyDTO e1 = new FurnitureBodyDTO(); e1.setId(1L); e1.setWidth(50); e1.setHeight(100);
        FurnitureBodyDTO e2 = new FurnitureBodyDTO(); e2.setId(2L); e2.setWidth(100); e2.setHeight(50);

        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(100); req.setSheetHeight(100);
        req.setElements(List.of(e1, e2));

        CutResponseDTO resp = service.optimizeCutting(req);

        assertEquals(2, resp.getPlacements().size());

        // Check the second element (ID 2) to see if it was rotated
        // Original size: 100x50. If rotated, the placedDTO width will be 50, height will be 100.
        PlacedElementDTO p2 = resp.getPlacements().stream().filter(p -> p.getId() == 2L).findFirst().orElseThrow();
        assertEquals(50, p2.getWidth(), "Element 2 should have been rotated to fit!");
        assertEquals(100, p2.getHeight());
    }

    /**
     * Forces rotation in a NEW level.
     * Sheet: 10x30
     * Element 1: 10x10 (This goes to the bottom. Remaining Y space: 20)
     * Element 2: 15x5  (Width 15 > Sheet width 10. Cannot fit as is. Rotated, it's 5x15 -> Fits!)
     */
    @Test
    void optimizeCutting_forcesRotationInNewLevel() {
        FurnitureBodyDTO e1 = new FurnitureBodyDTO(); e1.setId(1L); e1.setWidth(10); e1.setHeight(10);
        FurnitureBodyDTO e2 = new FurnitureBodyDTO(); e2.setId(2L); e2.setWidth(15); e2.setHeight(5);

        CutRequestDTO req = new CutRequestDTO();
        req.setSheetWidth(10); req.setSheetHeight(30);
        req.setElements(List.of(e1, e2));

        CutResponseDTO resp = service.optimizeCutting(req);

        assertEquals(2, resp.getPlacements().size());

        // Check the second element (ID 2)
        PlacedElementDTO p2 = resp.getPlacements().stream().filter(p -> p.getId() == 2L).findFirst().orElseThrow();

        // Original 15x5 -> Rotated 5x15
        assertEquals(5, p2.getWidth(), "The too-wide element should have been rotated!");
        assertEquals(15, p2.getHeight());
        // Check that it was placed in a new level (Y >= 10)
        assertTrue(p2.getY() >= 10);
    }
}