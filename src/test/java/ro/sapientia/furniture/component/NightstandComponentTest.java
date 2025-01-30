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
    public void getAllNightstandsTest() throws Exception {
        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();

        nightstand1.setColor(NightstandColor.BEIGE);
        nightstand2.setColor(NightstandColor.BLACK);
        nightstand3.setColor(NightstandColor.WHITE);

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
                    .andExpect(jsonPath("$[0].color", is(nightstand1.getColor().toString())))
                    .andExpect(jsonPath("$[1].id", is(nightstand2.getId().intValue())))
                    .andExpect(jsonPath("$[1].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[1].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[1].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[1].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[1].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[1].color", is(nightstand2.getColor().toString())))
                    .andExpect(jsonPath("$[2].id", is(nightstand3.getId().intValue())))
                    .andExpect(jsonPath("$[2].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[2].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[2].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[2].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[2].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[2].color", is(nightstand3.getColor().toString())));
    }

    @Test
    public void getAllNightstandsExpectEmptyTest() throws Exception {
        this.mockMvc.perform(get("/nightstands/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void getNightstandByIdTest() throws Exception {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BEIGE);

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
                    .andExpect(jsonPath("$.color", is(nightstand.getColor().toString())));
    }

    @Test
    public void getNightstandByIdExpectNotFoundTest() throws Exception {
        this.mockMvc.perform(get("/nightstands/find/id/-1"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(""));
    }

    @Test
    public void getNightstandByIdExpectBadRequestTest() throws Exception {
        this.mockMvc.perform(get("/nightstands/find/id/" + invalidId))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(""));
    }

    @Test
    public void getNightstandsByColorTest() throws Exception {
        NightstandColor findByColor = NightstandColor.BLACK;
        NightstandColor color2 = NightstandColor.WHITE;

        Nightstand nightstand1 = new Nightstand();
        Nightstand nightstand2 = new Nightstand();
        Nightstand nightstand3 = new Nightstand();
        Nightstand nightstand4 = new Nightstand();
        Nightstand nightstand5 = new Nightstand();

        nightstand1.setColor(findByColor);
        nightstand2.setColor(findByColor);
        nightstand3.setColor(findByColor);
        nightstand4.setColor(color2);
        nightstand5.setColor(color2);

        this.nightstandRepository.save(nightstand1);
        this.nightstandRepository.save(nightstand2);
        this.nightstandRepository.save(nightstand3);
        this.nightstandRepository.save(nightstand4);
        this.nightstandRepository.save(nightstand5);

        this.mockMvc.perform(get("/nightstands/find/color/" + findByColor))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(3)))
                    .andExpect(jsonPath("$[0].id", is(nightstand1.getId().intValue())))
                    .andExpect(jsonPath("$[0].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[0].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[0].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[0].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[0].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[0].color", is(nightstand1.getColor().toString())))
                    .andExpect(jsonPath("$[1].id", is(nightstand2.getId().intValue())))
                    .andExpect(jsonPath("$[1].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[1].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[1].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[1].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[1].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[1].color", is(nightstand2.getColor().toString())))
                    .andExpect(jsonPath("$[2].id", is(nightstand3.getId().intValue())))
                    .andExpect(jsonPath("$[2].width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$[2].height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$[2].depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$[2].numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$[2].hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$[2].color", is(nightstand3.getColor().toString())));
    }

    @Test
    public void getNightstandsByColorExpectEmptyTest1() throws Exception {
        this.mockMvc.perform(get("/nightstands/find/color/gray"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void getNightstandsByColorExpectEmptyTest2() throws Exception {
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

        this.nightstandRepository.save(nightstand1);
        this.nightstandRepository.save(nightstand2);
        this.nightstandRepository.save(nightstand3);
        this.nightstandRepository.save(nightstand4);
        this.nightstandRepository.save(nightstand5);

        this.mockMvc.perform(get("/nightstands/find/color/" + unusedColor))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    public void getNightstandsByColorExpectNotFoundTest1() throws Exception {
        this.mockMvc.perform(get("/nightstands/find/color/" + invalidColorName))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(""));
    }

    @Test
    public void getNightstandsByColorExpectNotFoundTest2() throws Exception {
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
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(""));
    }

    @Test
    public void addNightstandTest() throws Exception {
        Nightstand nightstand1 = new Nightstand(1, 2, 3, 4, true, NightstandColor.BEIGE);

        Nightstand nightstand2 = new Nightstand();
        nightstand2.setColor(NightstandColor.BLACK);

        ObjectMapper objectMapper = new ObjectMapper();
        String nightstand1Json = objectMapper.writeValueAsString(nightstand1);
        String nightstand2Json = objectMapper.writeValueAsString(nightstand2);

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightstand1Json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.width", is(nightstand1.getWidth())))
                    .andExpect(jsonPath("$.height", is(nightstand1.getHeight())))
                    .andExpect(jsonPath("$.depth", is(nightstand1.getDepth())))
                    .andExpect(jsonPath("$.numberOfDrawers", is(nightstand1.getNumberOfDrawers())))
                    .andExpect(jsonPath("$.hasLamp", is(nightstand1.isHasLamp())))
                    .andExpect(jsonPath("$.color", is(nightstand1.getColor().toString())));

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightstand2Json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.width", is(defaultWidthValue)))
                    .andExpect(jsonPath("$.height", is(defaultHeightValue)))
                    .andExpect(jsonPath("$.depth", is(defaultDepthValue)))
                    .andExpect(jsonPath("$.numberOfDrawers", is(defaultNumberOfDrawersValue)))
                    .andExpect(jsonPath("$.hasLamp", is(defaultHasLampValue)))
                    .andExpect(jsonPath("$.color", is(nightstand2.getColor().toString())));
    }

    @Test
    public void addNightstandExpectBadRequestTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String nightstandJson = objectMapper.writeValueAsString(new Nightstand());

        this.mockMvc.perform(post("/nightstands/add").contentType(MediaType.APPLICATION_JSON).content(nightstandJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(""));
    }

    @Test
    public void deleteNightstandByIdTest() throws Exception {
        Nightstand nightstand = new Nightstand();
        nightstand.setColor(NightstandColor.BLACK);

        this.nightstandRepository.save(nightstand);

        this.mockMvc.perform(delete("/nightstands/delete/" + nightstand.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
    }

    @Test
    public void deleteNightstandByIdExpectNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/nightstands/delete/-1"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(""));
    }

    @Test
    public void deleteNightstandByIdExpectBadRequestTest() throws Exception {
        this.mockMvc.perform(delete("/nightstands/delete/" + invalidId))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(""));
    }

}
