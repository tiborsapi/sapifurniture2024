package ro.sapientia.furniture.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.FurnitureColor;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.model.TableType;
import ro.sapientia.furniture.repository.FurnitureTableRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FurnitureTableServiceTest {
    private FurnitureTableRepository repositoryMock;

    private FurnitureTableService service;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(FurnitureTableRepository.class);
        service = new FurnitureTableService(repositoryMock);
    }

    @Test
    public void findAllTables(){
        FurnitureTable furnitureTable1 = new FurnitureTable();

        furnitureTable1.setDepth(100);
        furnitureTable1.setHeight(90);
        furnitureTable1.setWidth(100);
        furnitureTable1.setType(TableType.DINING);

        FurnitureTable furnitureTable2 = new FurnitureTable();

        furnitureTable2.setDepth(50);
        furnitureTable2.setHeight(90);
        furnitureTable2.setWidth(80);
        furnitureTable2.setType(TableType.OFFICE);

        when(repositoryMock.findAll()).thenReturn(List.of(furnitureTable1, furnitureTable2));
        var result = this.service.findAllFurnitureTables();

        assert result != null;
        assert result.size() == 2;
    }

    @Test
    public void findAllTablesByType(){
        FurnitureTable furnitureTable1 = new FurnitureTable();

        furnitureTable1.setDepth(100);
        furnitureTable1.setHeight(90);
        furnitureTable1.setWidth(100);
        furnitureTable1.setType(TableType.DINING);

        FurnitureTable furnitureTable2 = new FurnitureTable();

        furnitureTable2.setDepth(50);
        furnitureTable2.setHeight(90);
        furnitureTable2.setWidth(80);
        furnitureTable2.setType(TableType.OFFICE);

        var mockList = new ArrayList<FurnitureTable>();
        mockList.add(furnitureTable1);

        when(repositoryMock.findFurnitureTableByType(TableType.DINING)).thenReturn(mockList);
        var result = this.service.findAllFurnitureTablesByType(TableType.DINING);

        assert result != null;
        assert result.size() == 1;
    }

    @Test
    public void findAllTablesByColor(){
        FurnitureTable furnitureTable1 = new FurnitureTable();

        furnitureTable1.setDepth(100);
        furnitureTable1.setHeight(90);
        furnitureTable1.setWidth(100);
        furnitureTable1.setColor(FurnitureColor.GREEN);

        FurnitureTable furnitureTable2 = new FurnitureTable();

        furnitureTable2.setDepth(50);
        furnitureTable2.setHeight(90);
        furnitureTable2.setWidth(80);
        furnitureTable2.setColor(FurnitureColor.RED);

        var mockList = new ArrayList<FurnitureTable>();
        mockList.add(furnitureTable2);

        when(repositoryMock.findFurnitureTablesByColor(FurnitureColor.RED)).thenReturn(mockList);
        var result = this.service.findAllFurnitureByColor(FurnitureColor.RED);

        assert result != null;
        assert result.size() == 1;
    }

    @Test
    public void findByIdExpectNull(){
        when(repositoryMock.findFurnitureTableById(any(Long.class))).thenReturn(null);
        var result = service.findFurnitureTableById(1L);

        assert result == null;
    }

}
