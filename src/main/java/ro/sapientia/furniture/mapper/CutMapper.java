package ro.sapientia.furniture.mapper;

import ro.sapientia.furniture.model.entities.CutRequest;
import ro.sapientia.furniture.model.dto.CutRequestDTO;
import ro.sapientia.furniture.model.entities.CutResponse;
import ro.sapientia.furniture.model.dto.CutResponseDTO;
import ro.sapientia.furniture.model.entities.PlacedElement;
import ro.sapientia.furniture.model.dto.PlacedElementDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for converting between cutting optimization DTOs and domain models.
 */
public class CutMapper {

    private CutMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts CutRequestDTO to CutRequest domain model.
     *
     * @param dto the CutRequestDTO to convert
     * @return the converted CutRequest model, or null if dto is null
     */
    public static CutRequest toModel(CutRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        CutRequest model = new CutRequest();
        model.setSheetWidth(dto.getSheetWidth());
        model.setSheetHeight(dto.getSheetHeight());
        model.setElements(dto.getElements());

        return model;
    }

    /**
     * Converts CutRequest domain model to CutRequestDTO.
     *
     * @param model the CutRequest to convert
     * @return the converted CutRequestDTO, or null if model is null
     */
    public static CutRequestDTO toDTO(CutRequest model) {
        if (model == null) {
            return null;
        }

        CutRequestDTO dto = new CutRequestDTO();
        dto.setSheetWidth(model.getSheetWidth());
        dto.setSheetHeight(model.getSheetHeight());
        dto.setElements(model.getElements());

        return dto;
    }

    /**
     * Converts CutResponseDTO to CutResponse domain model.
     *
     * @param dto the CutResponseDTO to convert
     * @return the converted CutResponse model, or null if dto is null
     */
    public static CutResponse toModel(CutResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        CutResponse model = new CutResponse();

        if (dto.getPlacements() != null) {
            List<PlacedElement> placements = dto.getPlacements().stream()
                    .map(PlacedElementMapper::toModel)
                    .collect(Collectors.toList());
            model.setPlacements(placements);
        }

        return model;
    }

    /**
     * Converts CutResponse domain model to CutResponseDTO.
     *
     * @param model the CutResponse to convert
     * @return the converted CutResponseDTO, or null if model is null
     */
    public static CutResponseDTO toDTO(CutResponse model) {
        if (model == null) {
            return null;
        }

        CutResponseDTO dto = new CutResponseDTO();

        if (model.getPlacements() != null) {
            List<PlacedElementDTO> placements = model.getPlacements().stream()
                    .map(PlacedElementMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setPlacements(placements);
        }

        return dto;
    }

    /**
     * Converts a list of CutRequestDTO to a list of CutRequest models.
     *
     * @param dtos the list of CutRequestDTOs to convert
     * @return the converted list of CutRequest models
     */
    public static List<CutRequest> toModelList(List<CutRequestDTO> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(CutMapper::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of CutRequest models to a list of CutRequestDTOs.
     *
     * @param models the list of CutRequest models to convert
     * @return the converted list of CutRequestDTOs
     */
    public static List<CutRequestDTO> toDTOList(List<CutRequest> models) {
        if (models == null) {
            return new ArrayList<>();
        }

        return models.stream()
                .map(CutMapper::toDTO)
                .collect(Collectors.toList());
    }
}

