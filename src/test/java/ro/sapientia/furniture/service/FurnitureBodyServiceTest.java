package ro.sapientia.furniture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.FrontElement;
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;
import ro.sapientia.furniture.repository.RawMaterialRepository;

@ExtendWith(MockitoExtension.class)
public class FurnitureBodyServiceTest {

    @Mock
    private FurnitureBodyRepository furnitureBodyRepository;

    @Mock
    private ComponentListRepository componentListRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @Mock
    private RawMaterialService rawMaterialService;

    @InjectMocks
    private FurnitureBodyService furnitureBodyService;

    @Test
    public void saveFurnitureBodyAndComponents_savesEverythingAndDelegatesToRawMaterialService() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(100);
        fb.setHeigth(200);
        fb.setDepth(30);
        fb.setThickness(2);

        FrontElement fe = new FrontElement(fb, FrontElement.ElementType.BODY, 0, 0, 100, 200);
        RawMaterialType type = new RawMaterialType("Oak");
        fe.setRawMaterialType(type);
        fb.setMainFrontElement(fe);

        when(furnitureBodyRepository.save(any(FurnitureBody.class))).thenAnswer(i -> i.getArgument(0));

        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> {
            ComponentList cl = i.getArgument(0);
            cl.setId(10L);
            return cl;
        });

        AtomicInteger counter = new AtomicInteger(1);
        when(rawMaterialService.findOrCreateAndIncreaseQuantity(any(RawMaterial.class)))
            .thenAnswer(i -> {
                RawMaterial r = i.getArgument(0);
                r.setId(100L + counter.getAndIncrement());
                return r;
            });

        FurnitureBody result = furnitureBodyService.saveFurnitureBodyAndComponents(fb, 42L);

        assertNotNull(result);

        ComponentList cl = result.getRawMaterials();
        assertNotNull(cl);
        // verify component list saved at least once (initial save + final update)
        verify(componentListRepository, times(2)).save(any(ComponentList.class));

        List<RawMaterial> persisted = cl.getRawMaterials();
        int expectedCalls = fb.getRawMaterials().getRawMaterials().size();
        verify(rawMaterialService, times(expectedCalls)).findOrCreateAndIncreaseQuantity(any(RawMaterial.class));
        assertEquals(expectedCalls, persisted.size());
    }

    @Test
    public void findFurnitureBodyById_returnsEntity() {
        FurnitureBody fb = new FurnitureBody();
        fb.setId(7L);
        fb.setWidth(80);

        when(furnitureBodyRepository.findFurnitureBodyById(7L)).thenReturn(fb);

        FurnitureBody result = furnitureBodyService.findFurnitureBodyById(7L);

        assertNotNull(result);
        assertEquals(7L, result.getId());
        assertEquals(80, result.getWidth());
    }

    @Test
    public void create_callsSaveAndFlushAndReturnsSaved() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(50);

        when(furnitureBodyRepository.saveAndFlush(any(FurnitureBody.class))).thenAnswer(i -> {
            FurnitureBody a = i.getArgument(0);
            a.setId(11L);
            return a;
        });

        FurnitureBody result = furnitureBodyService.create(fb);

        assertNotNull(result);
        assertEquals(11L, result.getId());
        verify(furnitureBodyRepository, times(1)).saveAndFlush(any(FurnitureBody.class));
    }

    @Test
    public void update_callsSaveAndFlushAndReturnsUpdated() {
        FurnitureBody fb = new FurnitureBody();
        fb.setId(21L);
        fb.setWidth(123);

        when(furnitureBodyRepository.saveAndFlush(any(FurnitureBody.class))).thenAnswer(i -> i.getArgument(0));

        FurnitureBody result = furnitureBodyService.update(fb);

        assertNotNull(result);
        assertEquals(21L, result.getId());
        assertEquals(123, result.getWidth());
        verify(furnitureBodyRepository, times(1)).saveAndFlush(any(FurnitureBody.class));
    }

    @Test
    public void delete_callsRepositoryDeleteById() {
        Long id = 33L;

        furnitureBodyService.delete(id);

        verify(furnitureBodyRepository, times(1)).deleteById(id);
    }

    @Test
    public void saveFurnitureBodyAndComponents_initializesNullCandidateMaterials() {
        // Arrange: make repository return a FurnitureBody whose ComponentList has null rawMaterials
        FurnitureBody savedFb = org.mockito.Mockito.mock(FurnitureBody.class);
        ComponentList emptyCl = new ComponentList(); // rawMaterials == null

        when(furnitureBodyRepository.save(any(FurnitureBody.class))).thenReturn(savedFb);
        when(savedFb.getRawMaterials()).thenReturn(emptyCl);

        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> {
            ComponentList cl = i.getArgument(0);
            cl.setId(55L);
            return cl;
        });

        // Act
        FurnitureBody result = furnitureBodyService.saveFurnitureBodyAndComponents(new FurnitureBody(), 99L);

        // Assert
        assertNotNull(result);
        // componentList saved twice (initial save + final update)
        verify(componentListRepository, times(2)).save(any(ComponentList.class));
        // no raw materials to persist -> rawMaterialService should not be called
        verify(rawMaterialService, times(0)).findOrCreateAndIncreaseQuantity(any(RawMaterial.class));

        ComponentList cl = savedFb.getRawMaterials();
        assertNotNull(cl);
        // after the method runs the list should have been initialized to an empty list
        assertNotNull(cl.getRawMaterials());
        assertEquals(0, cl.getRawMaterials().size());
    }
}
