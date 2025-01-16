package ro.sapientia.furniture.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.WoodType;
import ro.sapientia.furniture.repository.FurnitureBedRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FurnitureBedServiceTest {
    private FurnitureBedRepository repositoryMock;

    private FurnitureBedService service;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(FurnitureBedRepository.class);
        service = new FurnitureBedService(repositoryMock);
    }

    @Test
    public void testFindAllBeds(){
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setHeight(50);
        bed1.setLength(200);
        bed1.setWidth(100);
        bed1.setType(BedType.FULL);
        bed1.setWood(WoodType.OAK);

        FurnitureBed bed2 = new FurnitureBed();
        bed2.setHeight(180);
        bed2.setLength(220);
        bed2.setWidth(110);
        bed2.setType(BedType.QUEEN);
        bed2.setWood(WoodType.MAPLE);

        when(repositoryMock.findAll()).thenReturn(List.of(bed1, bed2));

        var result = this.repositoryMock.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindBedsByWood() {
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setHeight(50);
        bed1.setLength(200);
        bed1.setWidth(100);
        bed1.setType(BedType.FULL);
        bed1.setWood(WoodType.OAK);

        var mockBedList = new ArrayList<FurnitureBed>();
        mockBedList.add(bed1);

        when(repositoryMock.findBedsByWood(WoodType.OAK)).thenReturn(mockBedList);

        var result = this.repositoryMock.findBedsByWood(WoodType.OAK);
        assertEquals(1, result.size());
    }

    @Test
    public void testFindBedsByType() {
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setHeight(50);
        bed1.setLength(200);
        bed1.setWidth(100);
        bed1.setType(BedType.FULL);
        bed1.setWood(WoodType.OAK);

        var mockBedList = new ArrayList<FurnitureBed>();
        mockBedList.add(bed1);

        when(repositoryMock.findBedsByType(BedType.FULL)).thenReturn(mockBedList);

        var result = this.repositoryMock.findBedsByType(BedType.FULL);
        assertEquals(1, result.size());
    }
}
