package ro.sapientia.furniture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sapientia.furniture.dto.TableCreationDTO;
import ro.sapientia.furniture.model.FurnitureColor;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.model.TableType;
import ro.sapientia.furniture.service.FurnitureTableService;
import ro.sapientia.furniture.util.EnumConverter;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class FurnitureTableController {

    private final FurnitureTableService furnitureTableService;

    public FurnitureTableController(final FurnitureTableService furnitureTableService) {
        this.furnitureTableService = furnitureTableService;
    }

    @GetMapping
    public ResponseEntity<List<FurnitureTable>> findAllTables(){
        var result = furnitureTableService.findAllFurnitureTables();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FurnitureTable> findTableById(final @PathVariable("id") Long id){
        var result = furnitureTableService.findFurnitureTableById(id);

        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<FurnitureTable>> findTableByColor(final @PathVariable("color") String color){

        FurnitureColor realColor;
        try{
            realColor = EnumConverter.tableColorEnumConverter(color);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var result = furnitureTableService.findAllFurnitureByColor(realColor);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<FurnitureTable>> findTableByType(final @PathVariable("type") String type){

        TableType tableType;

        try {
            tableType = EnumConverter.tableTypeEnumConverter(type);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var result = furnitureTableService.findAllFurnitureTablesByType(tableType);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping
    public  ResponseEntity<FurnitureTable> createTable(@RequestBody final TableCreationDTO tableCreationDTO){

        FurnitureTable furnitureTableModel;

        try {
            furnitureTableModel = tableCreationDTO.mapToModel();
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var result = this.furnitureTableService.saveFurnitureTable(furnitureTableModel);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
