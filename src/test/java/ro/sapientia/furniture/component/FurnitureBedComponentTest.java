package ro.sapientia.furniture.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.BedType;
import ro.sapientia.furniture.model.FurnitureBed;
import ro.sapientia.furniture.model.WoodType;
import ro.sapientia.furniture.repository.FurnitureBedRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class FurnitureBedComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FurnitureBedRepository repository;

    @Test
    public void testFindAllBeds() throws Exception{
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setHeight(50);
        bed1.setLength(200);
        bed1.setWidth(100);
        bed1.setType(BedType.FULL);
        bed1.setWood(WoodType.OAK);

        var savedBed = repository.save(bed1);

        this.mockMvc.perform(get("/beds/all")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(50)));
    }

    @Test
    public void testFindAllBedsByType() throws Exception{
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setHeight(50);
        bed1.setLength(200);
        bed1.setWidth(100);
        bed1.setType(BedType.FULL);
        bed1.setWood(WoodType.OAK);

        var savedBed = repository.save(bed1);

        this.mockMvc.perform(get("/beds/type/full")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type", is(BedType.FULL.toString())));
    }

    @Test
    public void testFindAllBedsByWood() throws Exception{
        FurnitureBed bed1 = new FurnitureBed();
        bed1.setHeight(50);
        bed1.setLength(200);
        bed1.setWidth(100);
        bed1.setType(BedType.FULL);
        bed1.setWood(WoodType.OAK);

        var savedBed = repository.save(bed1);

        this.mockMvc.perform(get("/beds/wood/oak")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].wood", is(WoodType.OAK.toString())));
    }

}
