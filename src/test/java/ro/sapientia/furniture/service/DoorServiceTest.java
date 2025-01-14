package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.Door;
import ro.sapientia.furniture.repository.DoorRepository;

public class DoorServiceTest {
	
	//service-ben az a lenyeg hogy tobb repositoryt is tudunk hasznalni de egyetlen egy service-t
	private DoorRepository repositoryMock;

	private DoorService service;

	
	@BeforeEach // minden egyes teszt elott elokesziti a szukseges objektumokat 
	public void setUp() {
		repositoryMock = mock(DoorRepository.class); // egy mock (szimulalt) objektumot hoz letre a DoorRepository class bol
		service = new DoorService(repositoryMock); //letrehozok egy peldanyt
	}
	
	//Ez azt vizsgalja ha nincs adat az adatbazisban (vagyis a repository ures listat ad vissza)
	//akkor a DoorService is ures listat fog visszaadni -> ami az elvart viselkedes
	@Test
	public void testFindAllDoors_emptyList() {
		//lekerem az osszeset s megnezem hogy amit visszaad az ures lista-e 
		//when-el tudom megadni milyen muvelet eseten mi lesz az elvart eredenyt varok vissza
		when(repositoryMock.findAll()).thenReturn(Collections.emptyList()); //mokoljuk a repositoty-t
		//letrehozom az ures listat
		final List<Door> furnitureBodies =  service.findAllDoors();
		//megnezem hogy a merete az tenyleg 0
		assertEquals(0, furnitureBodies.size());
	}
	
	//megfeleloen kezeli-e azt az esetet, amikor a repository null eteket ad vissza
	@Test
	public void testFindAllDoors_null() {
		when(repositoryMock.findAll()).thenReturn(null);
		//lekerem a repositorybol az osszes ajtokat
		final List<Door> doors =  service.findAllDoors();
		//itt ellenorizzuk hogy null erteket ad-e vissza amit lekertunk 
		assertNull(doors);
	}
	

	
}
