package ro.sapientia.furniture.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class RawMaterialRepositoryTest {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    @Test
    public void findByRawMaterialTypeAndLengthWidthHeight_returnsSaved() {
        RawMaterialType type = rawMaterialTypeRepository.save(new RawMaterialType("Oak"));

        RawMaterial rm = new RawMaterial(100, 50, 200, type, 2);
        rawMaterialRepository.save(rm);

        Optional<RawMaterial> found = rawMaterialRepository.findByRawMaterialTypeAndLengthAndWidthAndHeight(type, 200, 50, 100);
        assertTrue(found.isPresent());
        assertEquals(2, found.get().getQuantity());
    }
}
