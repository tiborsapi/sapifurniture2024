package ro.sapientia.furniture.service;

import org.junit.jupiter.api.BeforeEach;
import ro.sapientia.furniture.model.RockingChairModel;
import ro.sapientia.furniture.repository.RockingChairRepository;

import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class RockingChairServiceTest {
    private RockingChairRepository repoMock;

    private RockingChairService service;

    @BeforeEach
    public void setUp() {
        repoMock = mock(RockingChairRepository.class);
        service = new RockingChairService(repoMock);
    }

    @Test
    public void testFindRockingChairById() {
        RockingChairModel chair = new RockingChairModel();
        chair.setId(1L);

        when(repoMock.findRockingChairById(1L)).thenReturn(new ArrayList<>(List.of(chair)));

        List<RockingChairModel> result = service.findRockingChairById(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testFindRockingChairByMaterial() {
        RockingChairModel chair = new RockingChairModel();
        chair.setMaterial("Wood");

        when(repoMock.findRockingChairByMaterial("Wood")).thenReturn(new ArrayList<>(List.of(chair)));

        ArrayList<RockingChairModel> result = service.findRockingChairByMaterial("Wood");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Wood", result.get(0).getMaterial());
    }

    @Test
    public void testFindRockingChairByRockingRadius() {
        RockingChairModel chair1 = new RockingChairModel();
        chair1.setRockerRadius(50.0);

        RockingChairModel chair2 = new RockingChairModel();
        chair2.setRockerRadius(70.0);

        when(repoMock.findAllRockingChairModelByRockerRadiusBetween(40.0, 80.0)).thenReturn(new ArrayList<>(List.of(chair1, chair2)));

        ArrayList<RockingChairModel> result = service.findRockingChairByRockingRadius(40.0, 80.0);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindAllRockingChairs() {
        RockingChairModel chair = new RockingChairModel();

        when(repoMock.findAll()).thenReturn(List.of(chair));

        List<RockingChairModel> result = service.findAllRockingCharis();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testSaveRockingChair() {
        RockingChairModel chair = new RockingChairModel();

        when(repoMock.save(chair)).thenReturn(chair);

        RockingChairModel result = service.saveRockingChair(chair);

        assertNotNull(result);
        verify(repoMock, times(1)).save(chair);
    }
}
