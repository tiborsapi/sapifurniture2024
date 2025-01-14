package ro.sapientia.furniture.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.Wardrobe;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class WardrobeRepositoryTest {

    @Autowired
    WardrobeRepository repository;

    @Test
    public void testFindAllReturnsEmptyListInitially() {
        var result = repository.findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testSaveAndFindById() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWidth(100);
        wardrobe.setHeigth(200);
        wardrobe.setDepth(60);
        wardrobe.setNumberOfDoors(3);
        wardrobe.setHasMirror(true);
        wardrobe.setNumberOfShelves(4);

        var savedWardrobe = repository.save(wardrobe);

        var result = repository.findAll();
        assertEquals(1, result.size());

        var foundWardrobe = repository.findWardrobeById(savedWardrobe.getId());

        assertEquals(savedWardrobe, foundWardrobe);
        assertEquals(100, foundWardrobe.getWidth());
        assertEquals(200, foundWardrobe.getHeigth());
        assertEquals(60, foundWardrobe.getDepth());
        assertEquals(3, foundWardrobe.getNumberOfDoors());
        assertTrue(foundWardrobe.isHasMirror());
        assertEquals(4, foundWardrobe.getNumberOfShelves());
    }
}
