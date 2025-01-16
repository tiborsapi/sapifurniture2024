package ro.sapientia.furniture.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.RockingChairModel;

import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class RockingChairRepositoryTest {
    @Autowired
    private RockingChairRepository rockingChairRepository;

    @Test
    public void findAllRockingChairs() {
        RockingChairModel rockingChairModel = new RockingChairModel();

        rockingChairModel.setDepth(100);
        rockingChairModel.setHeight(200);
        rockingChairModel.setRockerRadius(60);
        rockingChairModel.setMaterial("Metal");
        rockingChairModel.setWidth(650);
        rockingChairModel.setSeatHeight(70);

        RockingChairModel rockingChairModel2 = new RockingChairModel();

        rockingChairModel.setDepth(110);
        rockingChairModel.setHeight(500);
        rockingChairModel.setRockerRadius(90);
        rockingChairModel.setMaterial("Wood");
        rockingChairModel.setWidth(250);
        rockingChairModel.setSeatHeight(40);

        this.rockingChairRepository.save(rockingChairModel);
        this.rockingChairRepository.save(rockingChairModel2);

        var result = this.rockingChairRepository.findAll();

        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    public void findRockingChairByMaterial() {
        RockingChairModel rockingChairModel = new RockingChairModel();
        rockingChairModel.setDepth(120);
        rockingChairModel.setHeight(300);
        rockingChairModel.setRockerRadius(75.0);
        rockingChairModel.setMaterial("Plastic");
        rockingChairModel.setWidth(500);
        rockingChairModel.setSeatHeight(45);

        this.rockingChairRepository.save(rockingChairModel);

        List<RockingChairModel> result = this.rockingChairRepository.findRockingChairByMaterial("Plastic");

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Plastic", result.get(0).getMaterial());
    }

    @Test
    public void findRockingChairByRockerRadiusBetween() {
        RockingChairModel rockingChairModel1 = new RockingChairModel();
        rockingChairModel1.setDepth(150);
        rockingChairModel1.setHeight(350);
        rockingChairModel1.setRockerRadius(50.0);
        rockingChairModel1.setMaterial("Metal");
        rockingChairModel1.setWidth(600);
        rockingChairModel1.setSeatHeight(55);

        RockingChairModel rockingChairModel2 = new RockingChairModel();
        rockingChairModel2.setDepth(160);
        rockingChairModel2.setHeight(400);
        rockingChairModel2.setRockerRadius(80.0);
        rockingChairModel2.setMaterial("Wood");
        rockingChairModel2.setWidth(300);
        rockingChairModel2.setSeatHeight(60);

        this.rockingChairRepository.save(rockingChairModel1);
        this.rockingChairRepository.save(rockingChairModel2);

        List<RockingChairModel> result = this.rockingChairRepository.findAllRockingChairModelByRockerRadiusBetween(45.0, 85.0);

        Assertions.assertEquals(2, result.size());
    }
}
