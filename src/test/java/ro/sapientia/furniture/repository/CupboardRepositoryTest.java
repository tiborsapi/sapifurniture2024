package ro.sapientia.furniture.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ro.sapientia.furniture.model.Cupboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class CupboardRepositoryTest {

    @Autowired
    CupboardRepository repository;

    @Test
    public void  testForFindById() {
        Cupboard cp = new Cupboard();
        cp.setId(99L);
        cp.setHeight(500);
        cp.setWidth(500);
        cp.setNumberOfDrawer(5);
        cp.setNumberOfDrawer(5);
        repository.save(cp);
        var result = repository.findById(99L);
        result.ifPresent(cupboard -> assertEquals(cupboard.getId(), 99L));
    }
}
