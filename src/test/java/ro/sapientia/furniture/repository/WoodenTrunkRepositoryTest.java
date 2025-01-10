package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.WoodenTrunk;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class WoodenTrunkRepositoryTest {

    @Autowired
    WoodenTrunkRepository repository;

    @Test
    public void testFindAllEmpty() {
        // Verify that the repository is initially empty
        var result = repository.findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testSaveAndFindById() {
        // Create a new WoodenTrunk instance
        WoodenTrunk trunk = new WoodenTrunk();
        trunk.setHeight(100);
        trunk.setWidth(50);
        trunk.setDepth(40);
        trunk.setMaterial("oak");
        trunk.setHasLid(true);
        trunk.setCapacity(200);

        // Save the WoodenTrunk to the repository
        var savedTrunk = repository.save(trunk);

        // Verify that the trunk is saved
        var result = repository.findAll();
        assertEquals(1, result.size());

        // Find the WoodenTrunk by ID and validate
        var foundTrunk = repository.findWoodenTrunkById(savedTrunk.getId());
        assertEquals(savedTrunk, foundTrunk);
    }
}
