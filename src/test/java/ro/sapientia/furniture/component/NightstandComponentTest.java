package ro.sapientia.furniture.component;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.sapientia.furniture.model.Nightstand;
import ro.sapientia.furniture.model.NightstandColor;
import ro.sapientia.furniture.repository.NightstandRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class NightstandComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NightstandRepository nightstandRepository;

    private static final int defaultWidthValue = 0;
    private static final int defaultHeightValue = 0;
    private static final int defaultDepthValue = 0;
    private static final int defaultNumberOfDrawersValue = 0;
    private static final boolean defaultHasLampValue = false;
    private static final String invalidColorName = "invalidColorName";
    private static final String invalidId = "invalidId";

    @BeforeEach
    public void clearDatabase() {
        this.nightstandRepository.deleteAll();
    }

    @Test
    public void findAllNightstandsTest() throws Exception {
        NightstandColor color1 = NightstandColor.BEIGE;
        NightstandColor color2 = NightstandColor.BLACK;
        NightstandColor color3 = NightstandColor.WHITE;

        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();

        nightstand1.setColor(color1);
        nightstand2.setColor(color2);
        nightstand3.setColor(color3);

        this.nightstandRepository.save(nightstand1);
        this.nightstandRepository.save(nightstand2);
        this.nightstandRepository.save(nightstand3);

        this.mockMvc.perform(get("/nightstands/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(3)))
                    .andExpect(jsonPath("$[0].id", is(nightstand1.getId().intValue())))
                    .andExpect(jsonPath("$[0].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[0].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[0].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[0].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[0].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[0].color", is(color1.toString())))
                    .andExpect(jsonPath("$[1].id", is(nightstand2.getId().intValue())))
                    .andExpect(jsonPath("$[1].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[1].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[1].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[1].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[1].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[1].color", is(color2.toString())))
                    .andExpect(jsonPath("$[2].id", is(nightstand3.getId().intValue())))
                    .andExpect(jsonPath("$[2].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[2].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[2].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[2].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[2].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[2].color", is(color3.toString())));
    }

    @Test
    public void findAllNightstandsExpectEmptyTest() throws Exception {
        this.mockMvc.perform(get("/nightstands/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void findNightstandByIdTest() throws Exception {
        NightstandColor color = NightstandColor.BEIGE;

        Nightstand nightstand = new Nightstand();
        nightstand.setColor(color);

        this.nightstandRepository.save(nightstand);

        this.mockMvc.perform(get("/nightstands/find/id/" + nightstand.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(nightstand.getId().intValue())))
                    .andExpect(jsonPath("$.width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$.height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$.depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$.numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$.hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$.color", is(color.toString())));
    }

    @Test
    public void findNightstandByIdExpectNotFoundTest() throws Exception {
        this.mockMvc.perform(get("/nightstands/find/id/-1"))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void findNightstandByIdExpectBadRequestTest() throws Exception {
        this.mockMvc.perform(get("/nightstands/find/id/" + invalidId))
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void findNightstandsByColorTest() throws Exception {
        NightstandColor color1 = NightstandColor.BLACK;
        NightstandColor color2 = NightstandColor.WHITE;
        NightstandColor color3 = NightstandColor.ANTHRACITE;

        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();
        Nightstand nightstand4 = new Nightstand();
        Nightstand nightstand5 = new Nightstand();

        nightstand1.setColor(color1);
        nightstand2.setColor(color1);
        nightstand3.setColor(color1);
        nightstand4.setColor(color2);
        nightstand5.setColor(color3);

        String findByColorName = color1.toString();

        this.nightstandRepository.save(nightstand1);
        this.nightstandRepository.save(nightstand2);
        this.nightstandRepository.save(nightstand3);
        this.nightstandRepository.save(nightstand4);
        this.nightstandRepository.save(nightstand5);

        this.mockMvc.perform(get("/nightstands/find/color/" + findByColorName))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(3)))
                    .andExpect(jsonPath("$[0].id", is(nightstand1.getId().intValue())))
                    .andExpect(jsonPath("$[0].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[0].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[0].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[0].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[0].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[0].color", is(findByColorName)))
                    .andExpect(jsonPath("$[1].id", is(nightstand2.getId().intValue())))
                    .andExpect(jsonPath("$[1].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[1].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[1].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[1].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[1].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[1].color", is(findByColorName)))
                    .andExpect(jsonPath("$[2].id", is(nightstand3.getId().intValue())))
                    .andExpect(jsonPath("$[2].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[2].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[2].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[2].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[2].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[2].color", is(findByColorName)));
    }

    @Test
    public void findNightstandsByColorExpectEmptyTest1() throws Exception {
        this.mockMvc.perform(get("/nightstands/find/color/gray"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void findNightstandsByColorExpectEmptyTest2() throws Exception {
        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();
        Nightstand nightstand4 = new Nightstand();
        Nightstand nightstand5 = new Nightstand();

        nightstand1.setColor(NightstandColor.BLACK);
        nightstand2.setColor(NightstandColor.BLACK);
        nightstand3.setColor(NightstandColor.BLACK);
        nightstand4.setColor(NightstandColor.WHITE);
        nightstand5.setColor(NightstandColor.ANTHRACITE);

        NightstandColor unusedColor = NightstandColor.GRAY;
        String unusedColorName = unusedColor.toString();

        this.nightstandRepository.save(nightstand1);
        this.nightstandRepository.save(nightstand2);
        this.nightstandRepository.save(nightstand3);
        this.nightstandRepository.save(nightstand4);
        this.nightstandRepository.save(nightstand5);

        this.mockMvc.perform(get("/nightstands/find/color/" + unusedColorName))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void findNightstandsByColorExpectNotFoundTest1() throws Exception {
        this.mockMvc.perform(get("/nightstands/find/color/" + invalidColorName))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void findNightstandsByColorExpectNotFoundTest2() throws Exception {
        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();
        Nightstand nightstand4 = new Nightstand();
        Nightstand nightstand5 = new Nightstand();

        nightstand1.setColor(NightstandColor.BLACK);
        nightstand2.setColor(NightstandColor.BLACK);
        nightstand3.setColor(NightstandColor.BLACK);
        nightstand4.setColor(NightstandColor.WHITE);
        nightstand5.setColor(NightstandColor.ANTHRACITE);

        this.nightstandRepository.save(nightstand1);
        this.nightstandRepository.save(nightstand2);
        this.nightstandRepository.save(nightstand3);
        this.nightstandRepository.save(nightstand4);
        this.nightstandRepository.save(nightstand5);

        this.mockMvc.perform(get("/nightstands/find/color/" + invalidColorName))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void createNightstandTest() throws Exception {
        int width1 = 1;
        int height1 = 2;
        int depth1 = 3;
        int numberOfDrawers1 = 4;
        boolean hasLamp1 = true;
        NightstandColor color1 = NightstandColor.BEIGE;
        Nightstand nightstand1 = new Nightstand(width1, height1, depth1, numberOfDrawers1, hasLamp1, color1);

        NightstandColor color2 = NightstandColor.BLACK;
        Nightstand nightstand2 = new Nightstand();
        nightstand2.setColor(color2);

        ObjectMapper objectMapper = new ObjectMapper();
        String nightStand1Json = objectMapper.writeValueAsString(nightstand1);
        String nightStand2Json = objectMapper.writeValueAsString(nightstand2);

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightStand1Json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.width", is(width1)))
                    .andExpect(jsonPath("$.height", is(height1)))
                    .andExpect(jsonPath("$.depth", is(depth1)))
                    .andExpect(jsonPath("$.numberOfDrawers", is(numberOfDrawers1)))
                    .andExpect(jsonPath("$.hasLamp", is(hasLamp1)))
                    .andExpect(jsonPath("$.color", is(color1.toString())));

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightStand2Json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$.height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$.depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$.numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$.hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$.color", is(color2.toString())));
    }

    @Test
    public void createNightstandExpectBadRequestTest() throws Exception {
        Nightstand nightstand = new Nightstand();

        ObjectMapper objectMapper = new ObjectMapper();
        String nightStandJson = objectMapper.writeValueAsString(nightstand);

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightStandJson))
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNightstandByIdTest() throws Exception {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BLACK);

        this.nightstandRepository.save(nightstand);

        this.mockMvc.perform(delete("/nightstands/delete/" + nightstand.getId()))
                    .andExpect(status().isOk());
    }

    @Test
    public void deleteNightstandByIdExpectNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/nightstands/delete/-1"))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void deleteNightstandByIdExpectBadRequestTest() throws Exception {
        this.mockMvc.perform(delete("/nightstands/delete/" + invalidId))
                    .andExpect(status().isBadRequest());
    }

}
