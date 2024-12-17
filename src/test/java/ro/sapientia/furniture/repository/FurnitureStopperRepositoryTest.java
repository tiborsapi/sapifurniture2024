
package ro.sapientia.furniture.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.FurnitureStopper;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class FurnitureStopperRepositoryTest {

    @Autowired
    private FurnitureBodyRepository bodyRepository;

    @Autowired
    private FurnitureStopperRepository repository;

    @Test
    public void testFindFurnitureStopperById() {
        FurnitureStopper stopper = new FurnitureStopper();
        FurnitureStopper finalStopper = repository.save(stopper);

        Optional<FurnitureStopper> found = repository.findFurnitureStopperById(finalStopper.getId());

        assertTrue(found.isPresent());
        assertThat(found.get().getId()).isEqualTo(finalStopper.getId());
    }

    @Test
    public void testFindByFurnitureBodyId() {
        FurnitureBody body = bodyRepository.save(new FurnitureBody());

        FurnitureStopper stopper1 = new FurnitureStopper();
        stopper1.setFurnitureBody(body);
        repository.save(stopper1);

        FurnitureStopper stopper2 = new FurnitureStopper();
        stopper2.setFurnitureBody(body);
        repository.save(stopper2);

        List<FurnitureStopper> found = repository.findByFurnitureBodyId(body.getId());
        assertThat(found).hasSize(2);
    }

    @Test
    public void testFindByFurnitureBodyIdNoResults() {
        List<FurnitureStopper> found = repository.findByFurnitureBodyId(99L);
        assertThat(found).isEmpty();
    }
}
