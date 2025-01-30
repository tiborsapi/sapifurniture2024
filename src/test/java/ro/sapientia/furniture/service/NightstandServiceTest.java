package ro.sapientia.furniture.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ro.sapientia.furniture.model.Nightstand;
import ro.sapientia.furniture.model.NightstandColor;
import ro.sapientia.furniture.repository.NightstandRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

public class NightstandServiceTest {

    private NightstandRepository nightstandRepositoryMock;

    private NightstandService nightstandService;

    @BeforeEach
    public void setUp() {
        this.nightstandRepositoryMock = mock(NightstandRepository.class);
        this.nightstandService = new NightstandService(this.nightstandRepositoryMock);
    }

    @Test
    public void findAllNightstandsTest() {
        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();

        nightstand1.setColor(NightstandColor.BEIGE);
        nightstand2.setColor(NightstandColor.BLACK);
        nightstand3.setColor(NightstandColor.WHITE);

        when(this.nightstandRepositoryMock.findAll()).thenReturn(List.of(nightstand1, nightstand2, nightstand3));
        List<Nightstand> result = this.nightstandService.findAllNightstands();

        assertEquals(3, result.size());
    }

    @Test
    public void findAllNightstandsExpectEmptyTest() {
        when(this.nightstandRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        List<Nightstand> result = this.nightstandService.findAllNightstands();

        assertEquals(0, result.size());
    }

    @Test
    public void findAllNightstandsExpectNullTest() {
        when(this.nightstandRepositoryMock.findAll()).thenReturn(null);
        List<Nightstand> result = this.nightstandService.findAllNightstands();

        assertNull(result);
    }

    @Test
    public void findNightstandByIdTest() {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        when(this.nightstandRepositoryMock.findNightstandById(1L)).thenReturn(nightstand);
        Nightstand result = this.nightstandService.findNightstandById(1L);

        assertEquals(nightstand, result);
    }

    @Test
    public void findNightstandByIdExpectNullTest() {
        when(this.nightstandRepositoryMock.findNightstandById(any(Long.class))).thenReturn(null);
        Nightstand result = this.nightstandService.findNightstandById(1L);

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

        when(this.nightstandRepositoryMock.findNightstandsByColor(color1)).thenReturn(List.of(nightstand1, nightstand2, nightstand3));
        List<Nightstand> result = this.nightstandService.findNightstandsByColor(color1);

        assertEquals(3, result.size());
    }

    @Test
    public void findNightstandsByColorExpectEmptyTest() {
        when(this.nightstandRepositoryMock.findNightstandsByColor(any())).thenReturn(Collections.emptyList());
        List<Nightstand> result = this.nightstandService.findNightstandsByColor(NightstandColor.BLACK);

        assertEquals(0, result.size());
    }

    @Test
    public void findNightstandsByColorExpectNullTest() {
        when(this.nightstandRepositoryMock.findNightstandsByColor(any())).thenReturn(null);
        List<Nightstand> result = this.nightstandService.findNightstandsByColor(NightstandColor.BLACK);

        assertNull(result);
    }

    @Test
    public void createNightstandTest() {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

        when(this.nightstandRepositoryMock.saveAndFlush(nightstand)).thenReturn(nightstand);
        Nightstand result = this.nightstandService.create(nightstand);

        assertEquals(nightstand, result);
    }

    @Test
    public void createNightstandExpectNullTest() {
        when(this.nightstandRepositoryMock.saveAndFlush(any())).thenReturn(null);
        Nightstand result = this.nightstandService.create(new Nightstand());

        assertNull(result);
    }

    @Test
    public void createNightstandExpectDataIntegrityViolationTest() {
        when(this.nightstandRepositoryMock.saveAndFlush(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> this.nightstandService.create(new Nightstand()));
    }

    @Test
    public void deleteNightstandByIdTest() {
        doNothing().when(this.nightstandRepositoryMock).deleteById(any());
        this.nightstandService.delete(1L);

        verify(nightstandRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    public void deleteNightstandByIdExpectNotFoundTest() {
        Long nonExistingId = -1L;
        doThrow(new EmptyResultDataAccessException(1)).when(this.nightstandRepositoryMock).deleteById(nonExistingId);

        assertThrows(EmptyResultDataAccessException.class, () -> this.nightstandService.delete(nonExistingId));
    }

}
