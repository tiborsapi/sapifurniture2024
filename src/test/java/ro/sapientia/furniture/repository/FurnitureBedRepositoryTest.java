package ro.sapientia.furniture.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.WoodType;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class FurnitureBedRepositoryTest {

    @Autowired
    FurnitureBedRepository repository;

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

        this.repository.save(bed1);
        this.repository.save(bed2);

        var result = this.repository.findAll();

        assertEquals(2, result.size());

    }

    @Test
    public void testFindAllBedsNoResult(){
        var result = this.repository.findAll();

        assertEquals(0, result.size());
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testFindBedsByWood(){
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

        this.repository.save(bed1);
        this.repository.save(bed2);

        var result = this.repository.findBedsByWood(WoodType.MAPLE);

        assertEquals(1, result.size());
    }

    @Test
    public void testFindBedsByType(){
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

        this.repository.save(bed1);
        this.repository.save(bed2);

        var result = this.repository.findBedsByType(BedType.FULL);


        assertEquals(1, result.size());
    }
}
