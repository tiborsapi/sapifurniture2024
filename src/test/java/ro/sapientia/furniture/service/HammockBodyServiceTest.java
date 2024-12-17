package ro.sapientia.furniture.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.HammockBody;
import ro.sapientia.furniture.repository.HammockBodyRepository;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class HammockBodyServiceTest {

    @Autowired
    HammockBodyRepository hammockBodyRepository;

    @Test
    public void myTest() {
        var result = hammockBodyRepository.findAll();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void myTestForFindFirst() {
        HammockBody hammockBody = new HammockBody();
        hammockBody.setLength(300);
        hammockBody.setWidth(150);
        hammockBody.setWeight(0.54);
        hammockBody.setMaterial("Polyester");
        var savedHammockBody = hammockBodyRepository.save(hammockBody);
        var result = hammockBodyRepository.findAll();
        Assertions.assertEquals(1, result.size());

        var foundObj = hammockBodyRepository.findHammockBodyById(savedHammockBody.getId());

        Assertions.assertEquals(savedHammockBody, foundObj);
    }
}
