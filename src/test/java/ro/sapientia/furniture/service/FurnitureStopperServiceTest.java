package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.FurnitureStopper;
import ro.sapientia.furniture.repository.FurnitureStopperRepository;

public class FurnitureStopperServiceTest {

    @Mock
    private FurnitureStopperRepository furnitureStopperRepository;

    @Autowired
    private FurnitureStopperService furnitureStopperService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        furnitureStopperService = new FurnitureStopperService(furnitureStopperRepository);
    }

    @Test
    public void testFindAllFurnitureStoppers() {
        FurnitureStopper stopper1 = new FurnitureStopper();
        FurnitureStopper stopper2 = new FurnitureStopper();
        List<FurnitureStopper> stoppers = Arrays.asList(stopper1, stopper2);

        when(furnitureStopperRepository.findAll()).thenReturn(stoppers);

        List<FurnitureStopper> result = furnitureStopperService.findAllFurnitureStoppers();
        assertEquals(result.size(), 2);
    }

    @Test
    public void testFindFurnitureStopperById() {
        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setStartTimeMs(420);
        when(furnitureStopperRepository.findFurnitureStopperById(1L)).thenReturn(Optional.of(stopper));

        Optional<FurnitureStopper> result = furnitureStopperService.findFurnitureStopperById(1L);
        assertTrue(result.isPresent());
        assertEquals(result.get().getStartTimeMs(), stopper.getStartTimeMs());
    }

    @Test
    public void testCreateFurnitureStopper() {
        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setStartTimeMs(420);
        when(furnitureStopperRepository.saveAndFlush(stopper)).thenReturn(stopper);

        FurnitureStopper result = furnitureStopperService.create(stopper);
        assertNotNull(result);
        assertEquals(result.getStartTimeMs(), stopper.getStartTimeMs());
    }

    @Test
    public void testUpdateFurnitureStopper() {
        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setStartTimeMs(420);
        when(furnitureStopperRepository.saveAndFlush(stopper)).thenReturn(stopper);

        FurnitureStopper result = furnitureStopperService.update(stopper);
        assertNotNull(result);
        assertEquals(result.getStartTimeMs(), stopper.getStartTimeMs());
    }

    @Test
    public void testDeleteFurnitureStopper() {
        doNothing().when(furnitureStopperRepository).deleteById(1L);

        furnitureStopperService.delete(1L);
        verify(furnitureStopperRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testStopFurniture() {
        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setId(1L);
        stopper.setStartTimeMs(0);
        stopper.setStopTimeMs(500);
        stopper.setFurnitureBody(null);

        FurnitureBody furnitureBody = new FurnitureBody();
        when(furnitureStopperRepository.findFurnitureStopperById(stopper.getId())).thenReturn(Optional.of(stopper));
        when(furnitureStopperRepository.saveAndFlush(stopper)).thenReturn(stopper);

        Optional<FurnitureStopper> result = furnitureStopperService.stopFurniture(stopper.getId(), furnitureBody);
        assertTrue(result.isPresent());
        assertEquals(result.get().getFurnitureBody(), furnitureBody);
    }

    @Test
    public void testStopFurnitureNotFound() {
        when(furnitureStopperRepository.findFurnitureStopperById(1L)).thenReturn(Optional.empty());

        // There is no furniture stopper
        Optional<FurnitureStopper> result = furnitureStopperService.stopFurniture(1L, new FurnitureBody());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testStopFurnitureCannotChangeState() {
        FurnitureStopper stopper = new FurnitureStopper();
        stopper.setId(1L);
        stopper.setStartTimeMs(System.currentTimeMillis());
        stopper.setStopTimeMs(5000);

        // The furniture should not be able to change state now because it can only do
        // so in 5 seconds
        when(furnitureStopperRepository.findFurnitureStopperById(stopper.getId())).thenReturn(Optional.of(stopper));

        try {
            furnitureStopperService.stopFurniture(stopper.getId(), new FurnitureBody());
            assertFalse("Expected an IllegalStateException to be thrown", true);
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Furniture stopper cannot change state at the moment");
        }
    }
}
