package ro.sapientia.furniture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.RawMaterialRepository;

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
}
