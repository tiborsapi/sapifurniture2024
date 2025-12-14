package ro.sapientia.furniture.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.sapientia.furniture.dto.ComponentListDTO;
import ro.sapientia.furniture.dto.RawMaterialDTO;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;
import ro.sapientia.furniture.repository.RawMaterialRepository;

@ExtendWith(MockitoExtension.class)
public class ComponentListServiceTest {

    @Mock
    private ComponentListRepository componentListRepository;

    @Mock
    private FurnitureBodyRepository furnitureBodyRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @Mock
    private RawMaterialTypeService rawMaterialTypeService;

    @Mock
    private RawMaterialService rawMaterialService;

    @InjectMocks
    private ComponentListService componentListService;

    @Test
    public void create_withRawMaterials_persistsAndDelegates() {
        ComponentListDTO dto = new ComponentListDTO();
        dto.setCreatedBy(7L);
        dto.setFurnitureBodyId(15L);

        RawMaterialDTO rmd = new RawMaterialDTO();
        rmd.setHeight(10);
        rmd.setWidth(20);
        rmd.setLength(30);
        rmd.setQuantity(2);
        rmd.setRawMaterialTypeName("Oak");
        dto.setRawMaterials(Arrays.asList(rmd));

        FurnitureBody fb = new FurnitureBody();
        fb.setId(15L);
        when(furnitureBodyRepository.findFurnitureBodyById(15L)).thenReturn(fb);

        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> {
            ComponentList cl = i.getArgument(0);
            cl.setId(100L);
            return cl;
        });

        RawMaterialType rmt = new RawMaterialType("Oak");
        rmt.setId(11L);
        when(rawMaterialTypeService.findOrCreateByName("Oak")).thenReturn(rmt);

        when(rawMaterialService.findOrCreateAndIncreaseQuantity(any(RawMaterial.class))).thenAnswer(i -> {
            RawMaterial rm = i.getArgument(0);
            rm.setId(200L);
            return rm;
        });

        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(i -> i.getArgument(0));

        ComponentList created = componentListService.create(dto);
        assertNotNull(created);
        assertNotNull(created.getId());

        verify(rawMaterialTypeService, times(1)).findOrCreateByName("Oak");
        verify(rawMaterialService, times(1)).findOrCreateAndIncreaseQuantity(any(RawMaterial.class));
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
        verify(componentListRepository, times(2)).save(any(ComponentList.class));
    }

    @Test
    public void delete_existingWithRawMaterials_dissociatesAndDeletes() {
        ComponentList cl = new ComponentList();
        cl.setId(5L);
        RawMaterial rm = new RawMaterial(1,1,1,null,1);
        rm.setId(77L);
        rm.setComponentList(cl);
        cl.setRawMaterials(new ArrayList<RawMaterial>(){{ add(rm); }});

        when(componentListRepository.findById(5L)).thenReturn(Optional.of(cl));

        boolean res = componentListService.delete(5L);
        assertTrue(res);

        // raw material should have been dissociated and saved, and component list deleted
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
        verify(componentListRepository, times(1)).delete(any(ComponentList.class));
    }

    @Test
    public void delete_missing_returnsFalse() {
        when(componentListRepository.findById(99L)).thenReturn(Optional.empty());
        boolean res = componentListService.delete(99L);
        assertFalse(res);
    }

    @Test
    public void create_withoutRawMaterials_persistsEmptyList() {
        ComponentListDTO dto = new ComponentListDTO();
        dto.setCreatedBy(3L);

        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> {
            ComponentList cl = i.getArgument(0);
            cl.setId(55L);
            return cl;
        });

        ComponentList created = componentListService.create(dto);

        assertNotNull(created);
        assertEquals(55L, created.getId().longValue());
        verify(rawMaterialService, times(0)).findOrCreateAndIncreaseQuantity(any(RawMaterial.class));
        verify(rawMaterialRepository, times(0)).save(any(RawMaterial.class));
        verify(componentListRepository, times(2)).save(any(ComponentList.class));
    }

    @Test
    public void update_existing_updatesAndReturnsOptional() {
        ComponentList existing = new ComponentList();
        existing.setId(44L);
        RawMaterial old = new RawMaterial(1,1,1,null,1);
        old.setId(77L);
        old.setComponentList(existing);
        existing.setRawMaterials(new ArrayList<RawMaterial>(){{ add(old); }});

        when(componentListRepository.findById(44L)).thenReturn(Optional.of(existing));
        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> i.getArgument(0));
        when(rawMaterialService.findOrCreateAndIncreaseQuantity(any(RawMaterial.class))).thenAnswer(i -> {
            RawMaterial rm = i.getArgument(0);
            rm.setId(88L);
            return rm;
        });
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(i -> i.getArgument(0));

        ComponentListDTO dto = new ComponentListDTO();
        RawMaterialDTO rmd = new RawMaterialDTO();
        rmd.setHeight(2);
        rmd.setWidth(2);
        rmd.setLength(2);
        rmd.setQuantity(5);
        dto.setRawMaterials(Arrays.asList(rmd));

        Optional<ComponentList> res = componentListService.update(44L, dto);
        assertTrue(res.isPresent());
        verify(rawMaterialRepository, times(2)).save(any(RawMaterial.class)); // dissociated old + saved new
        verify(rawMaterialService, times(1)).findOrCreateAndIncreaseQuantity(any(RawMaterial.class));
    }

    @Test
    public void update_existing_onlyDissociatesOldMaterials_whenDtoHasNoNewMaterials() {
        ComponentList existing = new ComponentList();
        existing.setId(50L);
        RawMaterial old = new RawMaterial(1,1,1,null,1);
        old.setId(77L);
        old.setComponentList(existing);
        existing.setRawMaterials(new ArrayList<RawMaterial>(){{ add(old); }});

        when(componentListRepository.findById(50L)).thenReturn(Optional.of(existing));
        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> i.getArgument(0));
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(i -> i.getArgument(0));

        ComponentListDTO dto = new ComponentListDTO(); // no rawMaterials set

        Optional<ComponentList> res = componentListService.update(50L, dto);

        assertTrue(res.isPresent());
        // only dissociation save should be invoked, rawMaterialService not called
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
        verify(rawMaterialService, times(0)).findOrCreateAndIncreaseQuantity(any(RawMaterial.class));
        verify(componentListRepository, times(1)).save(any(ComponentList.class));
    }

    @Test
    public void update_existing_addsNewMaterials_whenNoOldMaterialsPresent() {
        ComponentList existing = new ComponentList();
        existing.setId(60L);
        existing.setRawMaterials(null);

        when(componentListRepository.findById(60L)).thenReturn(Optional.of(existing));
        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> i.getArgument(0));

        RawMaterialDTO rmd = new RawMaterialDTO();
        rmd.setHeight(5);
        rmd.setWidth(6);
        rmd.setLength(7);
        rmd.setQuantity(3);
        rmd.setRawMaterialTypeName("Pine");

        ComponentListDTO dto = new ComponentListDTO();
        dto.setRawMaterials(Arrays.asList(rmd));

        RawMaterialType rmt = new RawMaterialType("Pine");
        when(rawMaterialTypeService.findOrCreateByName("Pine")).thenReturn(rmt);

        when(rawMaterialService.findOrCreateAndIncreaseQuantity(any(RawMaterial.class))).thenAnswer(i -> {
            RawMaterial rm = i.getArgument(0);
            rm.setId(300L);
            return rm;
        });

        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(i -> i.getArgument(0));

        Optional<ComponentList> res = componentListService.update(60L, dto);
        assertTrue(res.isPresent());

        // should have saved the new raw material once
        verify(rawMaterialService, times(1)).findOrCreateAndIncreaseQuantity(any(RawMaterial.class));
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
        verify(componentListRepository, times(1)).save(any(ComponentList.class));
    }

    @Test
    public void update_missing_returnsEmptyOptional() {
        when(componentListRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<ComponentList> res = componentListService.update(999L, new ComponentListDTO());
        assertFalse(res.isPresent());
    }

    @Test
    public void create_rawMaterialWithoutTypeName_handlesNullType() {
        ComponentListDTO dto = new ComponentListDTO();

        RawMaterialDTO rmd = new RawMaterialDTO();
        rmd.setHeight(1);
        rmd.setWidth(2);
        rmd.setLength(3);
        rmd.setQuantity(1);
        // rawMaterialTypeName left null intentionally
        dto.setRawMaterials(Arrays.asList(rmd));

        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> {
            ComponentList cl = i.getArgument(0);
            cl.setId(500L);
            return cl;
        });

        when(rawMaterialService.findOrCreateAndIncreaseQuantity(any(RawMaterial.class))).thenAnswer(i -> {
            RawMaterial rm = i.getArgument(0);
            rm.setId(600L);
            return rm;
        });

        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(i -> i.getArgument(0));

        ComponentList created = componentListService.create(dto);
        assertNotNull(created);
        verify(rawMaterialService, times(1)).findOrCreateAndIncreaseQuantity(any(RawMaterial.class));
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
    }

    @Test
    public void update_existing_setsFurnitureBody_whenDtoProvidesId() {
        ComponentList existing = new ComponentList();
        existing.setId(70L);

        when(componentListRepository.findById(70L)).thenReturn(Optional.of(existing));
        when(componentListRepository.save(any(ComponentList.class))).thenAnswer(i -> i.getArgument(0));

        ComponentListDTO dto = new ComponentListDTO();
        dto.setFurnitureBodyId(11L);

        FurnitureBody fb = new FurnitureBody();
        fb.setId(11L);
        when(furnitureBodyRepository.findFurnitureBodyById(11L)).thenReturn(fb);

        Optional<ComponentList> res = componentListService.update(70L, dto);
        assertTrue(res.isPresent());
        assertEquals(11L, res.get().getFurnitureBody().getId().longValue());
    }

    @Test
    public void delete_existingWithoutRawMaterials_deletesOnly() {
        ComponentList cl = new ComponentList();
        cl.setId(88L);
        cl.setRawMaterials(null);

        when(componentListRepository.findById(88L)).thenReturn(Optional.of(cl));

        boolean res = componentListService.delete(88L);
        assertTrue(res);

        verify(rawMaterialRepository, times(0)).save(any(RawMaterial.class));
        verify(componentListRepository, times(1)).delete(any(ComponentList.class));
    }
}
