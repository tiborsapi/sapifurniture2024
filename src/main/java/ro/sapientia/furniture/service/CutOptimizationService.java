package ro.sapientia.furniture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ro.sapientia.furniture.exception.CutOptimizationException;
import ro.sapientia.furniture.model.dto.CutRequestDTO;
import ro.sapientia.furniture.model.dto.CutResponseDTO;
import ro.sapientia.furniture.model.dto.FurnitureBodyDTO;
import ro.sapientia.furniture.model.dto.PlacedElementDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Service for optimizing furniture element placement on cutting sheets.
 * Uses a First Fit Decreasing Height (FFDH) bin packing algorithm.
 */
@Service
public class CutOptimizationService {

    private static final Logger logger = LoggerFactory.getLogger(CutOptimizationService.class);

    /**
     * Internal class to track occupied areas on the sheet.
     */
    private static class Level {
        int y;          // Y position of this level
        int height;     // Height of the level
        int x;          // Current X position in this level

        Level(int y, int height) {
            this.y = y;
            this.height = height;
            this.x = 0;
        }
    }

    /**
     * Optimize the placement of furniture elements on a cutting sheet.
     *
     * @param request The cutting request containing sheet dimensions and elements
     * @return CutResponse with optimized placements
     * @throws CutOptimizationException if elements cannot fit on the sheet
     */
    public CutResponseDTO optimizeCutting(CutRequestDTO request) {
        logger.info("Starting cut optimization for {} elements on {}x{} sheet",
                request.getElements().size(), request.getSheetWidth(), request.getSheetHeight());

        // Validate input
        validateRequest(request);

        // Sort elements by height (descending) for better packing
        List<FurnitureBodyDTO> sortedElements = new ArrayList<>(request.getElements());
        // DTO has a typo: method is getHeight()
        sortedElements.sort(Comparator.comparingInt(FurnitureBodyDTO::getHeight).reversed());

        // Perform optimization using FFDH algorithm
        List<PlacedElementDTO> placements = performFFDHPacking(
                sortedElements,
                request.getSheetWidth(),
                request.getSheetHeight()
        );

        // Validate that all elements were placed
        if (placements.size() != request.getElements().size()) {
            throw new CutOptimizationException(
                    "Failed to place all elements. " +
                            placements.size() + " of " + request.getElements().size() + " elements were placed."
            );
        }

        validatePlacements(placements, request.getSheetWidth(), request.getSheetHeight());

        logger.info("Cut optimization completed successfully. Placed {} elements", placements.size());

        return new CutResponseDTO(placements);
    }

    /**
     * Validate the cutting request.
     */
    private void validateRequest(CutRequestDTO request) {
        if (request.getElements() == null || request.getElements().isEmpty()) {
            throw new CutOptimizationException("No elements provided for cutting");
        }

        // Check if any element is larger than the sheet in both dimensions (cannot fit either orientation)
        for (FurnitureBodyDTO element : request.getElements()) {
            if (element.getWidth() > request.getSheetWidth() &&
                    element.getHeight() > request.getSheetHeight()) {
                throw new CutOptimizationException(
                        "Element " + element.getId() + " (" + element.getWidth() + "x" + element.getHeight() +
                                ") is too large to fit on the sheet (" + request.getSheetWidth() + "x" + request.getSheetHeight() + ")"
                );
            }
        }
    }

    /**
     * Perform First Fit Decreasing Height (FFDH) bin packing algorithm.
     * This is a shelf-based algorithm that creates horizontal levels.
     */
    private List<PlacedElementDTO> performFFDHPacking(
            List<FurnitureBodyDTO> elements,
            int sheetWidth,
            int sheetHeight) {

        List<PlacedElementDTO> placements = new ArrayList<>();
        List<Level> levels = new ArrayList<>();

        for (FurnitureBodyDTO element : elements) {
            boolean placed = false;
            int elementWidth = element.getWidth();
            int elementHeight = element.getHeight();
            boolean rotated = false;

            // Try to place element in existing levels
            for (Level level : levels) {
                // Check if element fits in this level (original orientation)
                if (elementWidth <= (sheetWidth - level.x) &&
                        elementHeight <= level.height) {

                    placements.add(new PlacedElementDTO(
                            element.getId(),
                            level.x,
                            level.y,
                            elementWidth,
                            elementHeight
                    ));

                    level.x += elementWidth;
                    placed = true;
                    break;
                }

                // Try rotated orientation (90 degrees)
                if (elementHeight <= (sheetWidth - level.x) &&
                        elementWidth <= level.height) {

                    placements.add(new PlacedElementDTO(
                            element.getId(),
                            level.x,
                            level.y,
                            elementHeight,  // swapped
                            elementWidth     // swapped
                    ));

                    level.x += elementHeight;
                    placed = true;
                    rotated = true;
                    break;
                }
            }

            // If not placed, try to create a new level
            if (!placed) {
                int nextY = levels.isEmpty() ? 0 :
                        levels.get(levels.size() - 1).y + levels.get(levels.size() - 1).height;

                // Try original orientation
                if (elementWidth <= sheetWidth &&
                        nextY + elementHeight <= sheetHeight) {

                    Level newLevel = new Level(nextY, elementHeight);
                    levels.add(newLevel);

                    placements.add(new PlacedElementDTO(
                            element.getId(),
                            0,
                            nextY,
                            elementWidth,
                            elementHeight
                    ));

                    newLevel.x = elementWidth;
                    placed = true;
                }
                // Try rotated orientation
                else if (elementHeight <= sheetWidth &&
                        nextY + elementWidth <= sheetHeight) {

                    Level newLevel = new Level(nextY, elementWidth);
                    levels.add(newLevel);

                    placements.add(new PlacedElementDTO(
                            element.getId(),
                            0,
                            nextY,
                            elementHeight,  // swapped
                            elementWidth     // swapped
                    ));

                    newLevel.x = elementHeight;
                    placed = true;
                    rotated = true;
                }
            }

            if (!placed) {
                logger.warn("Failed to place element {} ({}x{})",
                        element.getId(), elementWidth, elementHeight);
                throw new CutOptimizationException(
                        "Cannot fit element " + element.getId() + " (" +
                                elementWidth + "x" + elementHeight + ") on the remaining sheet space"
                );
            }

            if (rotated) {
                logger.debug("Element {} placed with rotation", element.getId());
            }
        }

        return placements;
    }

    /**
     * Validate that placements don't overlap and are within sheet bounds.
     * This is a safety check for the algorithm.
     */
    private void validatePlacements(List<PlacedElementDTO> placements, int sheetWidth, int sheetHeight) {
        for (int i = 0; i < placements.size(); i++) {
            PlacedElementDTO p1 = placements.get(i);

            // Check bounds
            if (p1.getX() < 0 || p1.getY() < 0 ||
                    p1.getX() + p1.getWidth() > sheetWidth ||
                    p1.getY() + p1.getHeight() > sheetHeight) {
                throw new CutOptimizationException(
                        "Element " + p1.getId() + " is out of sheet bounds"
                );
            }

            // Check overlaps
            for (int j = i + 1; j < placements.size(); j++) {
                PlacedElementDTO p2 = placements.get(j);

                boolean overlap = !(p1.getX() + p1.getWidth() <= p2.getX() ||
                        p2.getX() + p2.getWidth() <= p1.getX() ||
                        p1.getY() + p1.getHeight() <= p2.getY() ||
                        p2.getY() + p2.getHeight() <= p1.getY());

                if (overlap) {
                    throw new CutOptimizationException(
                            "Elements " + p1.getId() + " and " + p2.getId() + " overlap"
                    );
                }
            }
        }
    }
}