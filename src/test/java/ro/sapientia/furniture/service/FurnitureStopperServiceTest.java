package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
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
}
