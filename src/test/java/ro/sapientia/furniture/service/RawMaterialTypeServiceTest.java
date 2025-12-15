package ro.sapientia.furniture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.RawMaterialTypeRepository;

@ExtendWith(MockitoExtension.class)
public class RawMaterialTypeServiceTest {

    @Mock
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    @InjectMocks
    private RawMaterialTypeService rawMaterialTypeService;

    @Test
    public void whenNameNullOrBlank_thenReturnNull() {
        assertNull(rawMaterialTypeService.findOrCreateByName(null));
        assertNull(rawMaterialTypeService.findOrCreateByName("   "));
    }

    @Test
    public void whenExists_thenReturnExisting() {
        RawMaterialType existing = new RawMaterialType("Oak");
        existing.setId(5L);
        when(rawMaterialTypeRepository.findByName("Oak")).thenReturn(Optional.of(existing));

        RawMaterialType result = rawMaterialTypeService.findOrCreateByName("Oak");
        assertNotNull(result);
        assertEquals(5L, result.getId().longValue());
        verify(rawMaterialTypeRepository, times(1)).findByName("Oak");
    }

    @Test
    public void whenNotExists_thenCreateAndSave() {
        when(rawMaterialTypeRepository.findByName("Pine")).thenReturn(Optional.empty());
        when(rawMaterialTypeRepository.save(any(RawMaterialType.class))).thenAnswer(i -> {
            RawMaterialType r = i.getArgument(0);
            r.setId(9L);
            return r;
        });

        RawMaterialType created = rawMaterialTypeService.findOrCreateByName("Pine");
        assertNotNull(created);
        assertEquals(9L, created.getId().longValue());
        verify(rawMaterialTypeRepository, times(1)).findByName("Pine");
        verify(rawMaterialTypeRepository, times(1)).save(any(RawMaterialType.class));
        // createdAt/updatedAt should be set by service when missing
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());
    }
}
