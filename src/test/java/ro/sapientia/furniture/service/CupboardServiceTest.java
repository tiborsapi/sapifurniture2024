package ro.sapientia.furniture.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ro.sapientia.furniture.model.Cupboard;
import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.CupboardRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static reactor.core.publisher.Mono.when;

public class CupboardServiceTest {
    private CupboardRepository repositoryMock;
    private CupboardService cupboardService;

    @BeforeEach
    public void setUp(){
        repositoryMock = mock(CupboardRepository.class);
        cupboardService = new CupboardService(repositoryMock);
    }

    @Test
    public void testFindAllFurnitureBodies_emptyList() {
        when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
        final List<Cupboard> cupboards =  cupboardService.findAllCupboard();

        assertEquals(0, cupboards.size());
    }

    @Test
    public void testFindAllFurnitureBodies_null() {
        when(repositoryMock.findAll()).thenReturn(null);
        final List<Cupboard> cupboards = cupboardService.findAllCupboard();

        Assertions.assertNull(cupboards);
    }

}
