package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.FurnitureColor;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.model.TableType;
import ro.sapientia.furniture.repository.FurnitureTableRepository;

import java.util.List;

@Service
public class FurnitureTableService {

    private final FurnitureTableRepository furnitureTableRepository;

    public FurnitureTableService(final FurnitureTableRepository furnitureTableRepository) {
        this.furnitureTableRepository = furnitureTableRepository;
    }

    public List<FurnitureTable> findAllFurnitureTables(){
        return furnitureTableRepository.findAll();
    }

    public FurnitureTable findFurnitureTableById(Long id){
        return furnitureTableRepository.findFurnitureTableById(id);
    }

    public List<FurnitureTable> findAllFurnitureTablesByType(TableType tableType){
        return furnitureTableRepository.findFurnitureTableByType(tableType);
    }

    public List<FurnitureTable> findAllFurnitureByColor(FurnitureColor color){
        return furnitureTableRepository.findFurnitureTablesByColor(color);
    }

    public FurnitureTable saveFurnitureTable(FurnitureTable furnitureTable){
        return this.furnitureTableRepository.save(furnitureTable);
    }
}
