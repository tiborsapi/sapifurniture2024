package ro.sapientia.furniture.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.model.Tvstand;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;
import ro.sapientia.furniture.repository.TvStandRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TvStandComponentTest {


	private Tvstand tvstand;

	@BeforeEach
	public void setUp() {
		tvstand = new Tvstand();
		tvstand.setWidth(500);
		tvstand.setHeigth(500);
		tvstand.setDepth(300);
		tvstand.setDoors(2);
		tvstand.setMaterial("wood");
	}

	@Test
	public void testTvstandWidth() {
		assertEquals(500, tvstand.getWidth());
		tvstand.setWidth(600);
		assertEquals(600, tvstand.getWidth());
	}

	@Test
	public void testTvstandHeight() {
		assertEquals(500, tvstand.getHeigth());
		tvstand.setHeigth(600);
		assertEquals(600, tvstand.getHeigth());
	}

	@Test
	public void testTvstandDepth() {
		assertEquals(300, tvstand.getDepth());
		tvstand.setDepth(400);
		assertEquals(400, tvstand.getDepth());
	}

	@Test
	public void testTvstandDoors() {
		assertEquals(2, tvstand.getDoors());
		tvstand.setDoors(3);
		assertEquals(3, tvstand.getDoors());
	}

	@Test
	public void testTvstandMaterial() {
		assertEquals("wood", tvstand.getMaterial());
		tvstand.setMaterial("metal");
		assertEquals("metal", tvstand.getMaterial());
	}


	@Test
	public void testToString() {
		String expected = "TvStand [id=" + null + ", width=" + 500 + ", heigth=" + 500
				+ ", depth=" + 300 + ", doors=" + 2 + ", material=" + "wood" + "]";
		assertEquals(expected, tvstand.toString());
	}

}
