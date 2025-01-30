package ro.sapientia.furniture.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Test
    public void findAllNightstandsTest() {
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
    public void findAllNightstandsExpectEmptyTest() {
        List<Nightstand> result = this.nightstandRepository.findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void findNightstandByIdTest() {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        this.nightstandRepository.save(nightstand);

        Nightstand result = this.nightstandRepository.findNightstandById(nightstand.getId());

        assertEquals(nightstand.getId(), result.getId());
        assertEquals(defaultWidthValue, result.getWidth());
        assertEquals(defaultHeightValue, result.getHeight());
        assertEquals(defaultDepthValue, result.getDepth());
        assertEquals(defaultNumberOfDrawersValue, result.getNumberOfDrawers());
        assertEquals(defaultHasLampValue, result.isHasLamp());
        assertEquals(nightstand.getColor(), result.getColor());
    }

    @Test
    public void findNightstandByIdExpectNullTest() {
        Nightstand result = this.nightstandRepository.findNightstandById(-1L);

        assertNull(result);
    }

    @Test
    public void findNightstandsByColorTest() {
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
    public void findNightstandsByColorExpectEmptyTest() {
        List<Nightstand> result = this.nightstandRepository.findNightstandsByColor(NightstandColor.BEIGE);
        assertEquals(0, result.size());
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
    public void createNightstandExpectDataIntegrityViolationTest() {
        assertThrows(DataIntegrityViolationException.class, () -> this.nightstandRepository.saveAndFlush(new Nightstand()));
    }

    @Test
    public void deleteNightstandByIdTest() {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        this.nightstandRepository.save(nightstand);

        Nightstand result = this.nightstandRepository.findNightstandById(nightstand.getId());
        assertEquals(nightstand.getId(), result.getId());
        assertEquals(defaultWidthValue, result.getWidth());
        assertEquals(defaultHeightValue, result.getHeight());
        assertEquals(defaultDepthValue, result.getDepth());
        assertEquals(defaultNumberOfDrawersValue, result.getNumberOfDrawers());
        assertEquals(defaultHasLampValue, result.isHasLamp());
        assertEquals(nightstand.getColor(), result.getColor());

        this.nightstandRepository.deleteById(nightstand.getId());

        result = this.nightstandRepository.findNightstandById(nightstand.getId());
        assertNull(result);
    }

    @Test
    public void deleteNightstandByIdExpectNotFoundTest() {
        assertThrows(EmptyResultDataAccessException.class, () -> this.nightstandRepository.deleteById(-1L));
    }

}
