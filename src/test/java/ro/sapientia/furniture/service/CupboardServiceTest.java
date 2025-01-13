package ro.sapientia.furniture.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.Cupboard;
import ro.sapientia.furniture.repository.CupboardRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CupboardServiceTest {
    private CupboardRepository repositoryMock;
    private CupboardService cupboardService;

    @BeforeEach
    public void setUp(){
        repositoryMock = mock(CupboardRepository.class);
        cupboardService = new CupboardService(repositoryMock);
    }

    @Test
    public void testFindAllFurnitureBodies_emptyList() {
        when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
        final List<Cupboard> cupboards =  cupboardService.findAllCupboard();

        assertEquals(0, cupboards.size());
    }

    @Test
    public void testFindAllFurnitureBodies_null() {
        when(repositoryMock.findAll()).thenReturn(null);
        final List<Cupboard> cupboards = cupboardService.findAllCupboard();

        Assertions.assertNull(cupboards);
    }

    @Test
    public void testFindCupboardById_found() {
        Cupboard cupboard = new Cupboard();
        cupboard.setId(1L);
        cupboard.setWidth(500);
        cupboard.setHeight(500);
        cupboard.setNumberOfCab(5);
        cupboard.setNumberOfDrawer(5);

        when(repositoryMock.findCupboardById(1L)).thenReturn(cupboard);

        Cupboard result = cupboardService.findCupboardById(1L);
        assertEquals(cupboard, result);
        assertEquals(500, result.getWidth());
        assertEquals(500, result.getHeight());
        assertEquals(5, result.getNumberOfCab());
        assertEquals(5, result.getNumberOfDrawer());
    }

    @Test
    public void testFindCupboardById_notFound() {
        when(repositoryMock.findCupboardById(1L)).thenReturn(null);
        Cupboard result = cupboardService.findCupboardById(1L);
        assertNull(result);
    }

    @Test
    public void testCreateCupboard() {
        Cupboard cupboard = new Cupboard();
        cupboard.setWidth(500);
        cupboard.setHeight(500);
        cupboard.setNumberOfCab(5);
        cupboard.setNumberOfDrawer(5);

        when(repositoryMock.saveAndFlush(cupboard)).thenReturn(cupboard);

        Cupboard result = cupboardService.create(cupboard);
        assertEquals(cupboard, result);
        verify(repositoryMock, times(1)).saveAndFlush(cupboard);
    }

    @Test
    public void testUpdateCupboard() {
        Cupboard cupboard = new Cupboard();
        cupboard.setWidth(600);
        cupboard.setHeight(600);
        cupboard.setNumberOfCab(6);
        cupboard.setNumberOfDrawer(6);

        when(repositoryMock.saveAndFlush(cupboard)).thenReturn(cupboard);

        Cupboard result = cupboardService.update(cupboard);
        assertEquals(cupboard, result);
        verify(repositoryMock, times(1)).saveAndFlush(cupboard);
    }

    @Test
    public void testDeleteCupboard() {
        Long id = 1L;
        doNothing().when(repositoryMock).deleteById(id);
        cupboardService.delete(id);
        verify(repositoryMock, times(1)).deleteById(id);
    }

}
