package ro.sapientia.furniture.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.RawMaterialRepository;

import static org.junit.jupiter.api.Assertions.*;

class RawMaterialServiceTest {

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    private RawMaterialService rawMaterialService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        rawMaterialService = new RawMaterialService(rawMaterialRepository);
    }

    @Test
    void whenExistingRawMaterial_thenIncreaseQuantityAndUpdate() {
        RawMaterial existing = new RawMaterial();
        existing.setId(1L);
        existing.setRawMaterialType(new RawMaterialType("WOOD"));
        existing.setLength(100);
        existing.setWidth(50);
        existing.setHeight(10);
        existing.setQuantity(5);
        existing.setUpdatedAt(LocalDateTime.now().minusDays(1));

        RawMaterial candidate = new RawMaterial();
        candidate.setRawMaterialType(new RawMaterialType("WOOD"));
        candidate.setLength(100);
        candidate.setWidth(50);
        candidate.setHeight(10);
        candidate.setQuantity(3);

        when(rawMaterialRepository.findByRawMaterialTypeAndLengthAndWidthAndHeight(
                candidate.getRawMaterialType(), candidate.getLength(), candidate.getWidth(), candidate.getHeight()))
            .thenReturn(Optional.of(existing));
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(inv -> inv.getArgument(0));

        RawMaterial result = rawMaterialService.findOrCreateAndIncreaseQuantity(candidate);

        assertNotNull(result);
        assertEquals(8, result.getQuantity()); // 5 + 3
        assertTrue(result.getUpdatedAt().isAfter(existing.getUpdatedAt()));

        // verify save called with the updated entity
        ArgumentCaptor<RawMaterial> captor = ArgumentCaptor.forClass(RawMaterial.class);
        verify(rawMaterialRepository).save(captor.capture());
        assertEquals(8, captor.getValue().getQuantity());
    }

    @Test
    void whenNotExisting_thenCreateAndSetTimestamps() {
        RawMaterial candidate = new RawMaterial();
        candidate.setRawMaterialType(new RawMaterialType("METAL"));
        candidate.setLength(200);
        candidate.setWidth(100);
        candidate.setHeight(20);
        candidate.setQuantity(7);
        candidate.setCreatedAt(null);
        candidate.setUpdatedAt(null);

        when(rawMaterialRepository.findByRawMaterialTypeAndLengthAndWidthAndHeight(
                candidate.getRawMaterialType(), candidate.getLength(), candidate.getWidth(), candidate.getHeight()))
            .thenReturn(Optional.empty());
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(inv -> {
            RawMaterial rm = inv.getArgument(0);
            rm.setId(2L);
            return rm;
        });

        RawMaterial result = rawMaterialService.findOrCreateAndIncreaseQuantity(candidate);

        assertNotNull(result);
        assertEquals(7, result.getQuantity());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertEquals(2L, result.getId());
        verify(rawMaterialRepository).save(any(RawMaterial.class));
    }
}