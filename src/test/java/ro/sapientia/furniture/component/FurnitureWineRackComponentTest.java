package ro.sapientia.furniture.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.sapientia.furniture.model.FurnitureWineRack;
import ro.sapientia.furniture.repository.FurnitureWineRackRepository;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class FurnitureWineRackComponentTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	FurnitureWineRackRepository repository;
    @Autowired
    private FurnitureWineRackRepository furnitureWineRackRepository;

	@Test
	public void findAllWineRack() throws Exception {
		var wineRack = new FurnitureWineRack();
		wineRack.setCapacity(100);

		furnitureWineRackRepository.save(wineRack);

		this.mockMvc.perform(get("/winerack")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].capacity", is(100)));
	}

	@Test
	public void findAllFurnitureWineRackBetweenTwoCapacity() throws Exception {
		var wineRack1 = new FurnitureWineRack();
		wineRack1.setCapacity(100);

		var wineRack2 = new FurnitureWineRack();
		wineRack2.setCapacity(200);

		var wineRack3 = new FurnitureWineRack();
		wineRack3.setCapacity(300);


		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);
		furnitureWineRackRepository.save(wineRack3);

		this.mockMvc.perform(get("/winerack/capacity/80/350"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].capacity", both(greaterThanOrEqualTo(90)).and(lessThanOrEqualTo(350))));
	}

	@Test
	public void findAllFurnitureWineRackBetweenTwoCapacityExceptBadRequest() throws Exception {
		var wineRack1 = new FurnitureWineRack();
		wineRack1.setCapacity(100);

		var wineRack2 = new FurnitureWineRack();
		wineRack2.setCapacity(200);

		var wineRack3 = new FurnitureWineRack();
		wineRack3.setCapacity(300);


		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);
		furnitureWineRackRepository.save(wineRack3);

		this.mockMvc.perform(get("/winerack/capacity/350/10"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void findAllFurnitureWineRackBetweenTwoCapacityExceptEmptyRequest() throws Exception {
		var wineRack1 = new FurnitureWineRack();
		wineRack1.setCapacity(100);

		var wineRack2 = new FurnitureWineRack();
		wineRack2.setCapacity(200);

		var wineRack3 = new FurnitureWineRack();
		wineRack3.setCapacity(300);

		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);
		furnitureWineRackRepository.save(wineRack3);

		this.mockMvc.perform(get("/winerack/capacity/40/99")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void findAllFurnitureWineRackByFrontView() throws Exception {
		var wineRack1 = new FurnitureWineRack();
		wineRack1.setTransparentFront(true);

		var wineRack2 = new FurnitureWineRack();
		wineRack2.setTransparentFront(true);

		var wineRack3 = new FurnitureWineRack();
		wineRack3.setTransparentFront(false);

		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);
		furnitureWineRackRepository.save(wineRack3);

		this.mockMvc.perform(get("/winerack/transparentFront/true")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].transparentFront", is(true)));
	}

	@Test
	public void findAllFurnitureWineRackByFrontViewExceptBadRequest() throws Exception {
		var wineRack1 = new FurnitureWineRack();
		wineRack1.setTransparentFront(true);

		var wineRack2 = new FurnitureWineRack();
		wineRack2.setTransparentFront(true);

		var wineRack3 = new FurnitureWineRack();
		wineRack3.setTransparentFront(false);

		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);
		furnitureWineRackRepository.save(wineRack3);

		this.mockMvc.perform(get("/winerack/transparentFront/alma")).andExpect(status().isBadRequest());

	}

	@Test
	public void findAllFurnitureWineRackByFrontViewExceptEmptyRequest() throws Exception {
		var wineRack1 = new FurnitureWineRack();
		wineRack1.setTransparentFront(true);

		var wineRack2 = new FurnitureWineRack();
		wineRack2.setTransparentFront(true);

		var wineRack3 = new FurnitureWineRack();
		wineRack3.setTransparentFront(true);

		furnitureWineRackRepository.save(wineRack1);
		furnitureWineRackRepository.save(wineRack2);
		furnitureWineRackRepository.save(wineRack3);

		this.mockMvc.perform(get("/winerack/transparentFront/false")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));
	}

}
