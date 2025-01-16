package ro.sapientia.furniture.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sapientia.furniture.model.FurnitureWineRack;
import ro.sapientia.furniture.repository.FurnitureWineRackRepository;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class FurnitureWineRackServiceTest {

	private FurnitureWineRackRepository repositoryMock;

	private FurnitureWineRackService service;

	@BeforeEach
	public void setUp() {
		repositoryMock = mock(FurnitureWineRackRepository.class);
		service = new FurnitureWineRackService(repositoryMock);
	}

	@Test
	public void findAllWineRacks() {
		FurnitureWineRack wineRack1 = new FurnitureWineRack();
		wineRack1.setWidth(60);
		wineRack1.setHeigth(100);
		wineRack1.setCapacity(75);
		wineRack1.setTransparentFront(true);

		FurnitureWineRack wineRack2 = new FurnitureWineRack();
		wineRack2.setWidth(80);
		wineRack2.setHeigth(120);
		wineRack2.setCapacity(85);
		wineRack2.setTransparentFront(true);

		when(repositoryMock.findAll()).thenReturn(List.of(wineRack1, wineRack2));
		var resultData = this.service.findAllFurnitureWineRack();

		assert resultData != null;
		assert resultData.size() == 2;
	}

	@Test
	public void findAllFurnitureWineRacksByCapacityBetween() {
		FurnitureWineRack wineRack1 = new FurnitureWineRack();
		wineRack1.setWidth(60);
		wineRack1.setHeigth(100);
		wineRack1.setCapacity(75);
		wineRack1.setTransparentFront(true);

		FurnitureWineRack wineRack2 = new FurnitureWineRack();
		wineRack2.setWidth(80);
		wineRack2.setHeigth(120);
		wineRack2.setCapacity(85);
		wineRack2.setTransparentFront(true);

		var list = new ArrayList<FurnitureWineRack>();
		list.add(wineRack1);

		when(repositoryMock.findAllFurnitureWineRackByCapacityBetween(35,80)).thenReturn(list);
		var resultData = this.service.findAllFurnitureWineRacksByCapacityBetween(35,80);

		assert resultData != null;
		assert resultData.size() == 1;
	}

	@Test
	public void findAllFurnitureWineRacksByCurrentLoadNumberBetween() {
		FurnitureWineRack wineRack1 = new FurnitureWineRack();
		wineRack1.setWidth(60);
		wineRack1.setHeigth(100);
		wineRack1.setCurrentLoad(75);
		wineRack1.setTransparentFront(true);

		FurnitureWineRack wineRack2 = new FurnitureWineRack();
		wineRack2.setWidth(80);
		wineRack2.setHeigth(120);
		wineRack2.setCurrentLoad(85);
		wineRack2.setTransparentFront(true);

		var list = new ArrayList<FurnitureWineRack>();
		list.add(wineRack1);

		when(repositoryMock.findAllFurnitureWineRackByCurrentLoadBetween(35,80)).thenReturn(list);
		var resultData = this.service.findAllFurnitureWineRacksByCurrentLoadNumberBetween(35,80);

		assert resultData != null;
		assert resultData.size() == 1;
	}

	@Test
	public void findAllFurnitureWineRacksByFrontView() {
		FurnitureWineRack wineRack1 = new FurnitureWineRack();
		wineRack1.setWidth(60);
		wineRack1.setHeigth(100);
		wineRack1.setCurrentLoad(75);
		wineRack1.setTransparentFront(true);

		FurnitureWineRack wineRack2 = new FurnitureWineRack();
		wineRack2.setWidth(80);
		wineRack2.setHeigth(120);
		wineRack2.setCurrentLoad(85);
		wineRack2.setTransparentFront(true);

		var list = new ArrayList<FurnitureWineRack>();
		list.add(wineRack1);
		list.add(wineRack2);

		when(repositoryMock.findAllFurnitureWineRackByTransparentFront(true)).thenReturn(list);
		var resultData = this.service.findAllFurnitureWineRacksByFrontView(true);

		assert resultData != null;
		assert resultData.size() == 2;
	}

	@Test
	public void findFurnitureWineRackByIdExceptNullResponse(){
		when(repositoryMock.findFurnitureWineRacksById(any(Long.class))).thenReturn(null);
		var resultData = this.service.findFurnitureWineRackById(1L);

		assert resultData == null;
	}

}
