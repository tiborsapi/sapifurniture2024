package ro.sapientia.furniture.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.model.RawMaterialType;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class ComponentListRepositoryTest {

    @Autowired
    private ComponentListRepository componentListRepository;

    @Autowired
    private FurnitureBodyRepository furnitureBodyRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private RawMaterialTypeRepository rawMaterialTypeRepository;

    @Test
    public void findByFurnitureBodyId_returnsList() {
        FurnitureBody fb = new FurnitureBody();
        fb.setWidth(120);
        fb.setHeigth(80);
        fb = furnitureBodyRepository.save(fb);

        ComponentList cl = new ComponentList(1L);
        cl.setFurnitureBody(fb);
        cl = componentListRepository.save(cl);

        RawMaterialType type = rawMaterialTypeRepository.save(new RawMaterialType("Birch"));
        RawMaterial rm = new RawMaterial(10, 20, 30, type, 5, cl);
        rawMaterialRepository.save(rm);

        List<ComponentList> lists = componentListRepository.findByFurnitureBody_Id(fb.getId());
        assertEquals(1, lists.size());
        assertEquals(cl.getId(), lists.get(0).getId());
    }
}
