package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.Wardrobe;
import ro.sapientia.furniture.repository.WardrobeRepository;

public class WardrobeServiceTest {

    private WardrobeRepository repositoryMock;

    private WardrobeService service;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(WardrobeRepository.class);
        service = new WardrobeService(repositoryMock);
    }

    @Test
    public void testFindAllWardrobes_emptyList() {
        when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
        final List<Wardrobe> wardrobes = service.findAllWardrobe();

        assertEquals(0, wardrobes.size());
    }

    @Test
    public void testFindAllWardrobes_null() {
        when(repositoryMock.findAll()).thenReturn(null);
        final List<Wardrobe> wardrobes = service.findAllWardrobe();

        assertNull(wardrobes);
    }

    @Test
    public void testFindWardrobeById_found() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setId(1L);
        wardrobe.setWidth(100);
        wardrobe.setHeigth(200);
        wardrobe.setDepth(60);
        wardrobe.setNumberOfDoors(3);
        wardrobe.setHasMirror(true);
        wardrobe.setNumberOfShelves(4);

        when(repositoryMock.findWardrobeById(1L)).thenReturn(wardrobe);

        Wardrobe result = service.findWardrobeById(1L);

        assertEquals(wardrobe, result);
        assertEquals(100, result.getWidth());
        assertEquals(200, result.getHeigth());
        assertEquals(60, result.getDepth());
        assertEquals(3, result.getNumberOfDoors());
        assertTrue(result.isHasMirror());
        assertEquals(4, result.getNumberOfShelves());
    }

    @Test
    public void testFindWardrobeById_notFound() {
        when(repositoryMock.findWardrobeById(1L)).thenReturn(null);

        Wardrobe result = service.findWardrobeById(1L);

        assertNull(result);
    }

    @Test
    public void testCreateWardrobe() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWidth(100);
        wardrobe.setHeigth(200);
        wardrobe.setDepth(60);

        when(repositoryMock.saveAndFlush(wardrobe)).thenReturn(wardrobe);

        Wardrobe result = service.create(wardrobe);

        assertEquals(wardrobe, result);
        verify(repositoryMock, times(1)).saveAndFlush(wardrobe);
    }

    @Test
    public void testUpdateWardrobe() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWidth(150);
        wardrobe.setHeigth(250);
        wardrobe.setDepth(70);

        when(repositoryMock.saveAndFlush(wardrobe)).thenReturn(wardrobe);

        Wardrobe result = service.update(wardrobe);

        assertEquals(wardrobe, result);
        verify(repositoryMock, times(1)).saveAndFlush(wardrobe);
    }

    @Test
    public void testDeleteWardrobe() {
        Long id = 1L;

        doNothing().when(repositoryMock).deleteById(id);

        service.delete(id);

        verify(repositoryMock, times(1)).deleteById(id);
    }
}
