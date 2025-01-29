package ro.sapientia.furniture.repository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;

import ro.sapientia.furniture.model.Nightstand;
import ro.sapientia.furniture.model.NightstandColor;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class NightstandRepositoryTest {

    @Autowired
    private NightstandRepository nightstandRepository;

    private static final int defaultWidthValue = 0;
    private static final int defaultHeightValue = 0;
    private static final int defaultDepthValue = 0;
    private static final int defaultNumberOfDrawersValue = 0;
    private static final boolean defaultHasLampValue = false;

    @BeforeEach
    public void clearDatabase() {
        this.nightstandRepository.deleteAll();
    }

    @Test
    public void findAllNightstandsTest1() {
        List<Nightstand> result = this.nightstandRepository.findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void findAllNightstandsTest2() {
        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();

        nightstand1.setColor(NightstandColor.BEIGE);
        nightstand2.setColor(NightstandColor.BLACK);
        nightstand3.setColor(NightstandColor.WHITE);

        this.nightstandRepository.save(nightstand1);
        this.nightstandRepository.save(nightstand2);
        this.nightstandRepository.save(nightstand3);

        List<Nightstand> result = this.nightstandRepository.findAll();
        assertEquals(3, result.size());
    }

    @Test
    public void findNightstandByIdTest1() {
        Optional<Nightstand> result = this.nightstandRepository.findById(-1L);
        assertThat(result).isNotPresent();
    }

    @Test
    public void findNightstandByIdTest2() {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        this.nightstandRepository.save(nightstand);

        Optional<Nightstand> findResult = this.nightstandRepository.findById(nightstand.getId());
        assertThat(findResult).isPresent();

        Nightstand getResult = findResult.get();
        assertEquals(nightstand.getId(), getResult.getId());
        assertEquals(defaultWidthValue, getResult.getWidth());
        assertEquals(defaultHeightValue, getResult.getHeight());
        assertEquals(defaultDepthValue, getResult.getDepth());
        assertEquals(defaultNumberOfDrawersValue, getResult.getNumberOfDrawers());
        assertEquals(defaultHasLampValue, getResult.isHasLamp());
        assertEquals(nightstand.getColor(), getResult.getColor());
    }

    @Test
    public void findNightstandsByColorTest1() {
        List<Nightstand> result = this.nightstandRepository.findNightstandsByColor(NightstandColor.BEIGE);
        assertEquals(0, result.size());
    }

    @Test
    public void findNightstandsByColorTest2() {
        NightstandColor color1 = NightstandColor.BLACK;
        NightstandColor color2 = NightstandColor.WHITE;

        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();
        Nightstand nightstand4 = new Nightstand();
        Nightstand nightstand5 = new Nightstand();

        nightstand1.setColor(color1);
        nightstand2.setColor(color1);
        nightstand3.setColor(color1);
        nightstand4.setColor(color2);
        nightstand5.setColor(color2);

        this.nightstandRepository.save(nightstand1);
        this.nightstandRepository.save(nightstand2);
        this.nightstandRepository.save(nightstand3);
        this.nightstandRepository.save(nightstand4);
        this.nightstandRepository.save(nightstand5);

        List<Nightstand> result = this.nightstandRepository.findNightstandsByColor(color1);
        assertEquals(3, result.size());
    }

    @Test
    public void createNightstandTest() {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        Nightstand result = this.nightstandRepository.saveAndFlush(nightstand);
        assertEquals(nightstand.getId(), result.getId());
        assertEquals(defaultWidthValue, result.getWidth());
        assertEquals(defaultHeightValue, result.getHeight());
        assertEquals(defaultDepthValue, result.getDepth());
        assertEquals(defaultNumberOfDrawersValue, result.getNumberOfDrawers());
        assertEquals(defaultHasLampValue, result.isHasLamp());
        assertEquals(nightstand.getColor(), result.getColor());
    }

    @Test
    public void deleteNightstandByIdTest1() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            this.nightstandRepository.deleteById(-1L);
        });
    }

    @Test
    public void deleteNightstandByIdTest2() {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        this.nightstandRepository.save(nightstand);
        Optional<Nightstand> findResult = this.nightstandRepository.findById(nightstand.getId());
        assertThat(findResult).isPresent();

        this.nightstandRepository.deleteById(nightstand.getId());

        findResult = this.nightstandRepository.findById(nightstand.getId());
        assertThat(findResult).isNotPresent();
    }

}
