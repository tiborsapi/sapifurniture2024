package ro.sapientia.furniture.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ro.sapientia.furniture.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.dto.RawMaterialDTO;
import ro.sapientia.furniture.dto.ComponentListDTO;
import ro.sapientia.furniture.dto.FurnitureBodyDTO.FrontElementDTO;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.FrontElement;
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;
import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.service.RawMaterialTypeService;

@Component
public class FurnitureMapper {

    private final RawMaterialTypeService rawMaterialTypeService;

    public FurnitureMapper(RawMaterialTypeService rawMaterialTypeService) {
        this.rawMaterialTypeService = rawMaterialTypeService;
    }

    public FurnitureBody toEntity(FurnitureBodyDTO dto) {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(dto.getWidth());
        fb.setHeigth(dto.getHeigth());
        fb.setDepth(dto.getDepth());
        fb.setThickness(dto.getThickness());

        if (dto.getFrontElements() != null) {
            List<FrontElement> fes = new ArrayList<>();
            for (FrontElementDTO fedto : dto.getFrontElements()) {
                FrontElement fe = new FrontElement();
                try {
                    fe.setElementType(FrontElement.ElementType.valueOf(fedto.getElementType()));
                } catch (Exception ex) {
                    fe.setElementType(FrontElement.ElementType.UNKNOWN);
                }
                fe.setPosX(fedto.getPosX());
                fe.setPosY(fedto.getPosY());
                fe.setWidth(fedto.getWidth());
                fe.setHeight(fedto.getHeight());
                fe.setDetails(fedto.getDetails());
                if (fedto.getRawMaterialTypeName() != null) {
                    RawMaterialType rmt = rawMaterialTypeService.findOrCreateByName(fedto.getRawMaterialTypeName());
                    fe.setRawMaterialType(rmt);
                }
                fe.setFurnitureBody(fb);
                fes.add(fe);
            }
            fb.setFrontElements(fes);
            fb.refreshMainFrontElement();
        }
        return fb;
    }

    public ComponentListDTO toDto(ComponentList cl) {
        ComponentListDTO dto = new ComponentListDTO();
        dto.setId(cl.getId());
        dto.setCreatedBy(cl.getCreatedBy());
        dto.setCreatedAt(cl.getCreatedAt());
        dto.setUpdatedAt(cl.getUpdatedAt());
        dto.setFurnitureBodyId(cl.getFurnitureBody() != null ? cl.getFurnitureBody().getId() : null);
        if (cl.getRawMaterials() != null) {
            List<RawMaterialDTO> rms = cl.getRawMaterials().stream().map(this::toDto).collect(Collectors.toList());
            dto.setRawMaterials(rms);
        }
        return dto;
    }

    public RawMaterialDTO toDto(RawMaterial rm) {
        RawMaterialDTO dto = new RawMaterialDTO();
        dto.setId(rm.getId());
        dto.setHeight(rm.getHeight());
        dto.setWidth(rm.getWidth());
        dto.setLength(rm.getLength());
        dto.setQuantity(rm.getQuantity());
        dto.setCreatedAt(rm.getCreatedAt());
        dto.setUpdatedAt(rm.getUpdatedAt());
        dto.setRawMaterialTypeName(rm.getRawMaterialType() != null ? rm.getRawMaterialType().getName() : null);
        return dto;
    }
}
