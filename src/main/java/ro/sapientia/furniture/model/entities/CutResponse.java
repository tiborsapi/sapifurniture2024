package ro.sapientia.furniture.model.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain model for cutting optimization response.
 * Contains the optimized placement of elements.
 */
public class CutResponse {

    private List<PlacedElement> placements;

    public CutResponse() {
        this.placements = new ArrayList<>();
    }

    public CutResponse(List<PlacedElement> placements) {
        this.placements = placements != null ? placements : new ArrayList<>();
    }

    public List<PlacedElement> getPlacements() {
        return placements;
    }

    public void setPlacements(List<PlacedElement> placements) {
        this.placements = placements;
    }

    @Override
    public String toString() {
        return "CutResponse [placements=" + placements + "]";
    }
}

