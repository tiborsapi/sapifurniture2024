package ro.sapientia.furniture.mapper;

import ro.sapientia.furniture.model.entities.PlacedElement;
import ro.sapientia.furniture.model.dto.PlacedElementDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for converting between PlacedElementDTO and PlacedElement domain model.
 */
public class PlacedElementMapper {

    private PlacedElementMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts PlacedElementDTO to PlacedElement domain model.
     *
     * @param dto the PlacedElementDTO to convert
     * @return the converted PlacedElement model, or null if dto is null
     */
    public static PlacedElement toModel(PlacedElementDTO dto) {
        if (dto == null) {
            return null;
        }

        PlacedElement model = new PlacedElement();
        model.setId(dto.getId());
        model.setX(dto.getX());
        model.setY(dto.getY());
        model.setWidth(dto.getWidth());
        model.setHeight(dto.getHeight());

        return model;
    }

    /**
     * Converts PlacedElement domain model to PlacedElementDTO.
     *
     * @param model the PlacedElement to convert
     * @return the converted PlacedElementDTO, or null if model is null
     */
    public static PlacedElementDTO toDTO(PlacedElement model) {
        if (model == null) {
            return null;
        }

        PlacedElementDTO dto = new PlacedElementDTO();
        dto.setId(model.getId());
        dto.setX(model.getX());
        dto.setY(model.getY());
        dto.setWidth(model.getWidth());
        dto.setHeight(model.getHeight());

        return dto;
    }

    /**
     * Converts a list of PlacedElementDTO to a list of PlacedElement models.
     *
     * @param dtos the list of PlacedElementDTOs to convert
     * @return the converted list of PlacedElement models
     */
    public static List<PlacedElement> toModelList(List<PlacedElementDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(PlacedElementMapper::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of PlacedElement models to a list of PlacedElementDTOs.
     *
     * @param models the list of PlacedElement models to convert
     * @return the converted list of PlacedElementDTOs
     */
    public static List<PlacedElementDTO> toDTOList(List<PlacedElement> models) {
        if (models == null) {
            return new ArrayList<>();
        }

        return models.stream()
                .map(PlacedElementMapper::toDTO)
                .collect(Collectors.toList());
    }
}

