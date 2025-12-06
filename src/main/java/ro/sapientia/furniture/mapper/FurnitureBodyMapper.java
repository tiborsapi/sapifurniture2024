package ro.sapientia.furniture.mapper;

import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.model.entities.FurnitureBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for converting between FurnitureBodyDTO and FurnitureBody entity.
 */
public class FurnitureBodyMapper {

    private FurnitureBodyMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts FurnitureBodyDTO to FurnitureBody entity.
     *
     * @param dto the FurnitureBodyDTO to convert
     * @return the converted FurnitureBody entity, or null if dto is null
     */
    public static FurnitureBody toEntity(FurnitureBodyDTO dto) {
        if (dto == null) {
            return null;
        }

        FurnitureBody entity = new FurnitureBody();
        entity.setId(dto.getId());
        entity.setWidth(dto.getWidth());
        entity.setHeight(dto.getHeight());
        entity.setDepth(dto.getDepth());

        return entity;
    }

    /**
     * Converts FurnitureBody entity to FurnitureBodyDTO.
     *
     * @param entity the FurnitureBody entity to convert
     * @return the converted FurnitureBodyDTO, or null if entity is null
     */
    public static FurnitureBodyDTO toDTO(FurnitureBody entity) {
        if (entity == null) {
            return null;
        }

        FurnitureBodyDTO dto = new FurnitureBodyDTO();
        dto.setId(entity.getId());
        dto.setWidth(entity.getWidth());
        dto.setHeight(entity.getHeight());
        dto.setDepth(entity.getDepth());

        return dto;
    }

    /**
     * Converts a list of FurnitureBodyDTO to a list of FurnitureBody entities.
     *
     * @param dtos the list of FurnitureBodyDTOs to convert
     * @return the converted list of FurnitureBody entities
     */
    public static List<FurnitureBody> toEntityList(List<FurnitureBodyDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(FurnitureBodyMapper::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of FurnitureBody entities to a list of FurnitureBodyDTOs.
     *
     * @param entities the list of FurnitureBody entities to convert
     * @return the converted list of FurnitureBodyDTOs
     */
    public static List<FurnitureBodyDTO> toDTOList(List<FurnitureBody> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(FurnitureBodyMapper::toDTO)
                .collect(Collectors.toList());
    }
}

