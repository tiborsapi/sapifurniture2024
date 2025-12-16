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

import ro.sapientia.furniture.model.RawMaterialType;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class RawMaterialTypeRepositoryTest {

    @Autowired
    private RawMaterialTypeRepository repository;

    @Test
    public void findByName_returnsSavedType() {
        RawMaterialType t = new RawMaterialType("Pine");
        repository.save(t);

        Optional<RawMaterialType> found = repository.findByName("Pine");
        assertTrue(found.isPresent());
        assertEquals("Pine", found.get().getName());
    }
}
