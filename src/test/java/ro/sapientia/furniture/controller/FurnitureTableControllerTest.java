package ro.sapientia.furniture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.sapientia.furniture.dto.TableCreationDTO;
import ro.sapientia.furniture.model.FurnitureColor;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.model.TableType;
import ro.sapientia.furniture.service.FurnitureTableService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FurnitureTableController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class FurnitureTableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(FurnitureTableService.class)
    private FurnitureTableService furnitureTableService;

    @Test
    public void testFindAllTables() throws Exception{
        var table = new FurnitureTable();
        table.setColor(FurnitureColor.BLACK);

        when(furnitureTableService.findAllFurnitureTables()).thenReturn(List.of(table));

        this.mockMvc.perform(get("/tables")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].color", is(FurnitureColor.BLACK.toString())));
    }

    @Test
    public void testFindAllByColor1() throws Exception{
        var table1 = new FurnitureTable();
        table1.setColor(FurnitureColor.BLACK);

        when(furnitureTableService.findAllFurnitureByColor(FurnitureColor.BLACK)).thenReturn(List.of(table1));

        this.mockMvc.perform(get("/tables/color/black")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].color", is(FurnitureColor.BLACK.toString())));
    }

    @Test
    public void testFindAllByColor2() throws Exception{
        var table1 = new FurnitureTable();
        table1.setColor(FurnitureColor.BLACK);
        var table2 = new FurnitureTable();
        table2.setColor(FurnitureColor.BLACK);

        when(furnitureTableService.findAllFurnitureByColor(FurnitureColor.BLACK)).thenReturn(List.of(table1,table2));

        this.mockMvc.perform(get("/tables/color/BLACK")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[1].color", is(FurnitureColor.BLACK.toString())));
    }

    @Test
    public void testFindAllByColor3() throws Exception{
        this.mockMvc.perform(get("/tables/color/black1")).andExpect(status().isBadRequest());
    }

    @Test
    public void testFindAllByType1() throws Exception{
        var table1 = new FurnitureTable();
        table1.setType(TableType.OFFICE);

        when(furnitureTableService.findAllFurnitureTablesByType(TableType.OFFICE)).thenReturn(List.of(table1));

        this.mockMvc.perform(get("/tables/type/office")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type", is(TableType.OFFICE.toString())));
    }

    @Test
    public void testFindAllByType2() throws Exception{
        var table1 = new FurnitureTable();
        table1.setType(TableType.OFFICE);

        when(furnitureTableService.findAllFurnitureTablesByType(TableType.OFFICE)).thenReturn(List.of(table1));

        this.mockMvc.perform(get("/tables/type/OFFICE")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type", is(TableType.OFFICE.toString())));
    }

    @Test
    public void testFindAllByType3() throws Exception{
        this.mockMvc.perform(get("/tables/type/valami")).andExpect(status().isBadRequest());
    }

    @Test
    public void testAddFurnitureTable() throws Exception{
        var table = new FurnitureTable();
        table.setType(TableType.OFFICE);
        table.setColor(FurnitureColor.BLACK);
        table.setId(1L);
        table.setDepth(10);
        table.setWidth(10);
        table.setHeight(20);

        TableCreationDTO tableDTO = new TableCreationDTO();
        tableDTO.setColor("BLACK");
        tableDTO.setType("office");
        tableDTO.setDepth(table.getDepth());
        tableDTO.setWidth(table.getWidth());
        tableDTO.setHeight(table.getHeight());

        ObjectMapper objectMapper = new ObjectMapper();
        String tableDTOJson = objectMapper.writeValueAsString(tableDTO);

        when(furnitureTableService.saveFurnitureTable(any(FurnitureTable.class))).thenReturn(table);

        this.mockMvc.perform(post("/tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tableDTOJson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.depth", is(table.getDepth())))
                        .andExpect(jsonPath("$.width", is(table.getWidth())))
                        .andExpect(jsonPath("$.height", is(table.getHeight())))
                        .andExpect(jsonPath("$.type", is(table.getType().toString())))
                        .andExpect(jsonPath("$.color", is(table.getColor().toString())));
    }

    @Test
    public void testAddFurnitureTableExpectBadRequest() throws Exception{
        var table = new FurnitureTable();
        table.setType(TableType.OFFICE);
        table.setColor(FurnitureColor.BLACK);
        table.setId(1L);
        table.setDepth(10);
        table.setWidth(10);
        table.setHeight(20);

        when(furnitureTableService.saveFurnitureTable(any(FurnitureTable.class))).thenReturn(table);

        TableCreationDTO tableDTO = new TableCreationDTO();
        tableDTO.setColor("BLACK1");
        tableDTO.setType("ofice");
        tableDTO.setDepth(table.getDepth());
        tableDTO.setWidth(table.getWidth());
        tableDTO.setHeight(table.getHeight());

        ObjectMapper objectMapper = new ObjectMapper();
        String tableDTOJson = objectMapper.writeValueAsString(tableDTO);

        this.mockMvc.perform(post("/tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tableDTOJson))
                        .andExpect(status().isBadRequest());
    }

}
