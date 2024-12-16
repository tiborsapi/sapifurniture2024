package ro.sapientia.furniture.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.sapientia.furniture.dto.TableCreationDTO;
import ro.sapientia.furniture.model.FurnitureColor;
import ro.sapientia.furniture.model.FurnitureTable;
import ro.sapientia.furniture.model.TableType;
import ro.sapientia.furniture.repository.FurnitureTableRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class FurnitureTableComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FurnitureTableRepository furnitureTableRepository;

    @Test
    public void findAllTables() throws Exception {

        var table = new FurnitureTable();
        table.setColor(FurnitureColor.BLACK);

        furnitureTableRepository.save(table);

        this.mockMvc.perform(get("/tables")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].color", is(FurnitureColor.BLACK.toString())));

    }

    @Test
    public void findAllTablesByColor() throws Exception {

        var table1 = new FurnitureTable();
        table1.setColor(FurnitureColor.BLACK);

        var table2 = new FurnitureTable();
        table2.setColor(FurnitureColor.RED);

        var table3 = new FurnitureTable();
        table3.setColor(FurnitureColor.BLACK);

        furnitureTableRepository.save(table1);
        furnitureTableRepository.save(table2);
        furnitureTableRepository.save(table3);

        this.mockMvc.perform(get("/tables/color/red")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].color", is(FurnitureColor.RED.toString())));

    }

    @Test
    public void findAllTablesByColorExpectBadRequest() throws Exception {

        var table1 = new FurnitureTable();
        table1.setColor(FurnitureColor.BLACK);

        var table2 = new FurnitureTable();
        table2.setColor(FurnitureColor.RED);

        var table3 = new FurnitureTable();
        table3.setColor(FurnitureColor.BLACK);

        furnitureTableRepository.save(table1);
        furnitureTableRepository.save(table2);
        furnitureTableRepository.save(table3);

        this.mockMvc.perform(get("/tables/color/RED1")).andExpect(status().isBadRequest());
    }
    @Test
    public void findAllTablesByColorExpectEmpty() throws Exception {

        var table1 = new FurnitureTable();
        table1.setColor(FurnitureColor.BLACK);

        var table2 = new FurnitureTable();
        table2.setColor(FurnitureColor.RED);

        var table3 = new FurnitureTable();
        table2.setColor(FurnitureColor.BLACK);

        furnitureTableRepository.save(table1);
        furnitureTableRepository.save(table2);
        furnitureTableRepository.save(table3);

        this.mockMvc.perform(get("/tables/color/green")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    public void findAllTablesByType() throws Exception {

        var table1 = new FurnitureTable();
        table1.setType(TableType.OFFICE);

        var table2 = new FurnitureTable();
        table2.setType(TableType.DINING);

        var table3 = new FurnitureTable();
        table3.setType(TableType.COFFEE);

        furnitureTableRepository.save(table1);
        furnitureTableRepository.save(table2);
        furnitureTableRepository.save(table3);

        this.mockMvc.perform(get("/tables/type/dining")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type", is(TableType.DINING.toString())));

    }

    @Test
    public void findAllTablesByTypeExpectEmpty() throws Exception {

        var table1 = new FurnitureTable();
        table1.setType(TableType.OFFICE);

        var table2 = new FurnitureTable();
        table2.setType(TableType.DINING);

        var table3 = new FurnitureTable();
        table3.setType(TableType.COFFEE);

        furnitureTableRepository.save(table1);
        furnitureTableRepository.save(table2);
        furnitureTableRepository.save(table3);

        this.mockMvc.perform(get("/tables/type/CONFERENCE")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    public void findAllTablesByTypeExpectBadRequest() throws Exception {

        var table1 = new FurnitureTable();
        table1.setType(TableType.OFFICE);

        var table2 = new FurnitureTable();
        table2.setType(TableType.DINING);

        var table3 = new FurnitureTable();
        table3.setType(TableType.COFFEE);

        furnitureTableRepository.save(table1);
        furnitureTableRepository.save(table2);
        furnitureTableRepository.save(table3);

        this.mockMvc.perform(get("/tables/type/of1ice")).andExpect(status().isBadRequest());

    }

    @Test
    public void testAddFurnitureTable() throws Exception{
        var table = new FurnitureTable();
        table.setType(TableType.OFFICE);
        table.setColor(FurnitureColor.BLACK);
        table.setId(99999L);
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

        this.mockMvc.perform(post("/tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tableDTOJson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id", not(table.getId().intValue())))
                        .andExpect(jsonPath("$.depth", is(table.getDepth())))
                        .andExpect(jsonPath("$.width", is(table.getWidth())))
                        .andExpect(jsonPath("$.height", is(table.getHeight())))
                        .andExpect(jsonPath("$.type", is(table.getType().toString())))
                        .andExpect(jsonPath("$.color", is(table.getColor().toString())));
    }

    @Test
    public void testAddFurnitureTableExpectBadRequest() throws Exception{

        TableCreationDTO tableDTO = new TableCreationDTO();
        tableDTO.setColor("BLACK1");
        tableDTO.setType("ofice");
        tableDTO.setDepth(10);
        tableDTO.setWidth(20);
        tableDTO.setHeight(25);

        ObjectMapper objectMapper = new ObjectMapper();
        String tableDTOJson = objectMapper.writeValueAsString(tableDTO);

        this.mockMvc.perform(post("/tables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tableDTOJson))
                        .andExpect(status().isBadRequest());
    }



}
