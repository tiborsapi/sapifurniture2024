package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.FurnitureSwivelChair;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class FurnitureSwivelChairRepositoryTest {

    @Autowired
    FurnitureSwivelChairRepository furnitureSwivelChairRepository;

    @Test
    public void findAllSwivelChairs() {
        FurnitureSwivelChair swivelChair1 = new FurnitureSwivelChair();

        swivelChair1.setSeatWidth(30);
        swivelChair1.setSeatDepth(40);
        swivelChair1.setBackrestHeigth(50);
        swivelChair1.setWeightCapacity(100);
        swivelChair1.setMaterial("plastic");

        this.furnitureSwivelChairRepository.save(swivelChair1);

        FurnitureSwivelChair swivelChair2 = new FurnitureSwivelChair();

        swivelChair2.setSeatWidth(40);
        swivelChair2.setSeatDepth(50);
        swivelChair2.setBackrestHeigth(60);
        swivelChair2.setWeightCapacity(150);
        swivelChair2.setMaterial("metal");

        this.furnitureSwivelChairRepository.save(swivelChair2);

        var result = this.furnitureSwivelChairRepository.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void findAllSwivelChairByWeightCapacity() {
        FurnitureSwivelChair swivelChair1 = new FurnitureSwivelChair();

        swivelChair1.setSeatWidth(30);
        swivelChair1.setSeatDepth(40);
        swivelChair1.setBackrestHeigth(50);
        swivelChair1.setWeightCapacity(100);
        swivelChair1.setMaterial("plastic");

        this.furnitureSwivelChairRepository.save(swivelChair1);

        FurnitureSwivelChair swivelChair2 = new FurnitureSwivelChair();

        swivelChair2.setSeatWidth(40);
        swivelChair2.setSeatDepth(50);
        swivelChair2.setBackrestHeigth(60);
        swivelChair2.setWeightCapacity(150);
        swivelChair2.setMaterial("metal");

        this.furnitureSwivelChairRepository.save(swivelChair2);

        var result = this.furnitureSwivelChairRepository.findFurnitureSwivelChairByWeightCapacity(150);
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

        this.furnitureSwivelChairRepository.save(swivelChair1);

        FurnitureSwivelChair swivelChair2 = new FurnitureSwivelChair();

        swivelChair2.setSeatWidth(40);
        swivelChair2.setSeatDepth(50);
        swivelChair2.setBackrestHeigth(60);
        swivelChair2.setWeightCapacity(150);
        swivelChair2.setMaterial("metal");

        this.furnitureSwivelChairRepository.save(swivelChair2);

        var result = this.furnitureSwivelChairRepository.findFurnitureSwivelChairByMaterial("plastic");
        assertEquals(1, result.size());
    }
}
