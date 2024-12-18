package ro.sapientia.furniture.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.FurnitureColor;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.model.TableType;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class FurnitureTableRepositoryTest {

    @Autowired
    private FurnitureTableRepository furnitureTableRepository;

    @Test
    public void findAllByTypeTest(){
        FurnitureTable furnitureTable1 = new FurnitureTable();

        furnitureTable1.setDepth(100);
        furnitureTable1.setHeight(90);
        furnitureTable1.setWidth(100);
        furnitureTable1.setType(TableType.DINING);

        FurnitureTable furnitureTable2 = new FurnitureTable();

        furnitureTable2.setDepth(100);
        furnitureTable2.setHeight(90);
        furnitureTable2.setWidth(100);
        furnitureTable2.setType(TableType.OFFICE);

        this.furnitureTableRepository.save(furnitureTable1);
        this.furnitureTableRepository.save(furnitureTable2);

        var result = this.furnitureTableRepository.findFurnitureTableByType(TableType.DINING);

        assertEquals(result.size(), 1);
    }

    @Test
    public void findAllTables(){
        FurnitureTable furnitureTable1 = new FurnitureTable();

        furnitureTable1.setDepth(100);
        furnitureTable1.setHeight(90);
        furnitureTable1.setWidth(100);
        furnitureTable1.setType(TableType.DINING);

        FurnitureTable furnitureTable2 = new FurnitureTable();

        furnitureTable2.setDepth(100);
        furnitureTable2.setHeight(90);
        furnitureTable2.setWidth(100);
        furnitureTable2.setType(TableType.OFFICE);

        this.furnitureTableRepository.save(furnitureTable1);
        this.furnitureTableRepository.save(furnitureTable2);

        var result = this.furnitureTableRepository.findAll();

        assertEquals(result.size(), 2);
    }

    @Test
    public void findAllByColor1(){
        FurnitureTable furnitureTable1 = new FurnitureTable();

        furnitureTable1.setDepth(100);
        furnitureTable1.setHeight(90);
        furnitureTable1.setWidth(100);
        furnitureTable1.setColor(FurnitureColor.BLACK);
        furnitureTable1.setType(TableType.DINING);

        FurnitureTable furnitureTable2 = new FurnitureTable();

        furnitureTable2.setDepth(100);
        furnitureTable2.setHeight(90);
        furnitureTable2.setWidth(100);
        furnitureTable2.setColor(FurnitureColor.RED);
        furnitureTable2.setType(TableType.OFFICE);

        this.furnitureTableRepository.save(furnitureTable1);
        this.furnitureTableRepository.save(furnitureTable2);

        var result = this.furnitureTableRepository.findFurnitureTablesByColor(FurnitureColor.BLACK);

        assertEquals(result.size(), 1);
    }

    @Test
    public void findAllByColor2(){
        FurnitureTable furnitureTable1 = new FurnitureTable();

        furnitureTable1.setDepth(100);
        furnitureTable1.setHeight(90);
        furnitureTable1.setWidth(100);
        furnitureTable1.setColor(FurnitureColor.RED);
        furnitureTable1.setType(TableType.DINING);

        FurnitureTable furnitureTable2 = new FurnitureTable();

        furnitureTable2.setDepth(100);
        furnitureTable2.setHeight(90);
        furnitureTable2.setWidth(100);
        furnitureTable2.setColor(FurnitureColor.RED);
        furnitureTable2.setType(TableType.OFFICE);

        this.furnitureTableRepository.save(furnitureTable1);
        this.furnitureTableRepository.save(furnitureTable2);

        var result = this.furnitureTableRepository.findFurnitureTablesByColor(FurnitureColor.RED);

        assertEquals(result.size(), 2);
    }
}
