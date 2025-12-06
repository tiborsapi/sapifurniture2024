package ro.sapientia.furniture.model.dto;

import java.util.List;

/**
 * Response model for the cutting optimization endpoint.
 * Contains the optimized placement of elements.
 */
public class CutResponseDTO {
    
    private List<PlacedElementDTO> placements;
    
    public CutResponseDTO() {
    }
    
    public CutResponseDTO(List<PlacedElementDTO> placements) {
        this.placements = placements;
    }
    
    public List<PlacedElementDTO> getPlacements() {
        return placements;
    }
    
    public void setPlacements(List<PlacedElementDTO> placements) {
        this.placements = placements;
    }
    
    @Override
    public String toString() {
        return "CutResponse [placements=" + placements + "]";
    }
}
