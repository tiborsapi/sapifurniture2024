package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.FurnitureSwivelChair;
import ro.sapientia.furniture.repository.FurnitureSwivelChairRepository;

public class FurnitureSwivelChairServiceTest {

    private FurnitureSwivelChairRepository repositoryMock;

    private FurnitureSwivelChairService service;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(FurnitureSwivelChairRepository.class);
        service = new FurnitureSwivelChairService(repositoryMock);
    }

    @Test
    public void findAllSwivelChairs() {
        FurnitureSwivelChair swivelChair1 = new FurnitureSwivelChair();

        swivelChair1.setSeatWidth(30);
        swivelChair1.setSeatDepth(40);
        swivelChair1.setBackrestHeigth(50);
        swivelChair1.setWeightCapacity(100);
        swivelChair1.setMaterial("plastic");

        FurnitureSwivelChair swivelChair2 = new FurnitureSwivelChair();

        swivelChair2.setSeatWidth(40);
        swivelChair2.setSeatDepth(50);
        swivelChair2.setBackrestHeigth(60);
        swivelChair2.setWeightCapacity(150);
        swivelChair2.setMaterial("metal");

        when(repositoryMock.findAll()).thenReturn(List.of(swivelChair1, swivelChair2));
        var result = this.service.findAllFurnitureSwivelChairs();

        assert result != null;
        assertEquals(result.size(), 2);
    }

    @Test
    public void findAllFurnitureSwivelChairs_NULL() {
        when(repositoryMock.findAll()).thenReturn(null);
        var result = this.service.findAllFurnitureSwivelChairs();

        assertNull(result);
    }

    @Test
    public void findAllSwivelChairByWeightCapacity() {
        FurnitureSwivelChair swivelChair1 = new FurnitureSwivelChair();

        swivelChair1.setSeatWidth(30);
        swivelChair1.setSeatDepth(40);
        swivelChair1.setBackrestHeigth(50);
        swivelChair1.setWeightCapacity(100);
        swivelChair1.setMaterial("plastic");

        FurnitureSwivelChair swivelChair2 = new FurnitureSwivelChair();

        swivelChair2.setSeatWidth(40);
        swivelChair2.setSeatDepth(50);
        swivelChair2.setBackrestHeigth(60);
        swivelChair2.setWeightCapacity(150);
        swivelChair2.setMaterial("metal");

        var listMock = new ArrayList<FurnitureSwivelChair>();
        listMock.add(swivelChair2);

        when(repositoryMock.findFurnitureSwivelChairByWeightCapacity(150)).thenReturn(listMock);
        var result = this.service.findAllFurnitureSwivelChairByWeightCapacity(150);

        assert result != null;
        assertEquals(1, result.size());
    }

    @Test
    public void findAllSwivelChairByMaterial() {
        FurnitureSwivelChair swivelChair1 = new FurnitureSwivelChair();

        swivelChair1.setSeatWidth(30);
        swivelChair1.setSeatDepth(40);
        swivelChair1.setBackrestHeigth(50);
        swivelChair1.setWeightCapacity(100);
        swivelChair1.setMaterial("plastic");

        FurnitureSwivelChair swivelChair2 = new FurnitureSwivelChair();

        swivelChair2.setSeatWidth(40);
        swivelChair2.setSeatDepth(50);
        swivelChair2.setBackrestHeigth(60);
        swivelChair2.setWeightCapacity(150);
        swivelChair2.setMaterial("metal");

        var listMock = new ArrayList<FurnitureSwivelChair>();
        listMock.add(swivelChair1);

        when(repositoryMock.findFurnitureSwivelChairByMaterial("plastic")).thenReturn(listMock);
        var result = this.service.findAllFurnitureSwivelChairByMaterial("plastic");

        assert result != null;
        assertEquals(1, result.size());
    }
}
