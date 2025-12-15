package ro.sapientia.furniture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
public class RawMaterialServiceTest {

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private RawMaterialService rawMaterialService;

    @Test
    public void whenExisting_thenIncreaseQuantityAndSave() {
        RawMaterialType type = new RawMaterialType("Oak");
        RawMaterial existing = new RawMaterial(100, 50, 200, type, 5);
        existing.setId(1L);

        when(rawMaterialRepository.findByRawMaterialTypeAndLengthAndWidthAndHeight(type, 200, 50, 100))
            .thenReturn(Optional.of(existing));
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(i -> i.getArgument(0));

        RawMaterial candidate = new RawMaterial(100, 50, 200, type, 2);
        RawMaterial result = rawMaterialService.findOrCreateAndIncreaseQuantity(candidate);

        assertNotNull(result);
        assertEquals(7, result.getQuantity());
        verify(rawMaterialRepository, times(1)).save(result);
    }

    @Test
    public void whenNotExisting_thenCreateAndSave() {
        RawMaterialType type = new RawMaterialType("Pine");
        when(rawMaterialRepository.findByRawMaterialTypeAndLengthAndWidthAndHeight(type, 200, 50, 100))
            .thenReturn(Optional.empty());

        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(i -> {
            RawMaterial r = i.getArgument(0);
            r.setId(23L);
            return r;
        });

        RawMaterial candidate = new RawMaterial(100, 50, 200, type, 3);

        RawMaterial result = rawMaterialService.findOrCreateAndIncreaseQuantity(candidate);

        assertNotNull(result);
        assertEquals(3, result.getQuantity());
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
    }
  
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
        LocalDateTime originalUpdatedAt = LocalDateTime.now().minusDays(1);
        existing.setUpdatedAt(originalUpdatedAt);

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
        assertTrue(result.getUpdatedAt().isAfter(originalUpdatedAt));

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
