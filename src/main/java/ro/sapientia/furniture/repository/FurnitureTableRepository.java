package ro.sapientia.furniture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sapientia.furniture.model.FurnitureColor;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.model.TableType;

import java.util.ArrayList;

public interface FurnitureTableRepository extends JpaRepository<FurnitureTable, Long> {

    FurnitureTable findFurnitureTableById(Long id);

    ArrayList<FurnitureTable> findFurnitureTableByType(TableType type);

    ArrayList<FurnitureTable> findFurnitureTablesByColor(FurnitureColor color);

}
