package ro.sapientia.furniture;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import ro.sapientia.furniture.model.WoodenTrunk;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:eetest.properties")
class WoodenTrunkApplicationComponentTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    private void addOneWoodenTrunk() {
        WoodenTrunk trunk = new WoodenTrunk();
        trunk.setHeight(100);
        trunk.setWidth(50);
        trunk.setDepth(40);
        trunk.setMaterial("Oak");
        trunk.setHasLid(true);
        trunk.setCapacity(200);
        entityManager.persist(trunk);
        entityManager.flush();
    }

    @Test
    void woodenTrunkAll_oneElement() throws Exception {
        addOneWoodenTrunk();
        mvc.perform(get("/wooden-trunk/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].height", is(100)))
                .andExpect(jsonPath("$[0].width", is(50)))
                .andExpect(jsonPath("$[0].depth", is(40)))
                .andExpect(jsonPath("$[0].material", is("Oak")))
                .andExpect(jsonPath("$[0].hasLid", is(true)))
                .andExpect(jsonPath("$[0].capacity", is(200)));
    }
}
