package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.WoodenTrunk;
import ro.sapientia.furniture.repository.WoodenTrunkRepository;

public class WoodenTrunkServiceTest {

    private WoodenTrunkRepository repositoryMock;

    private WoodenTrunkService service;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(WoodenTrunkRepository.class);
        service = new WoodenTrunkService(repositoryMock);
    }

    @Test
    public void testFindAllWoodenTrunks_emptyList() {
        // Mock the repository to return an empty list
        when(repositoryMock.findAll()).thenReturn(Collections.emptyList());
        
        // Call the service method
        final List<WoodenTrunk> woodenTrunks = service.findAllWoodenTrunks();
        
        // Verify the result is an empty list
        assertEquals(0, woodenTrunks.size());
    }

    @Test
    public void testFindAllWoodenTrunks_null() {
        // Mock the repository to return null
        when(repositoryMock.findAll()).thenReturn(null);
        
        // Call the service method
        final List<WoodenTrunk> woodenTrunks = service.findAllWoodenTrunks();
        
        // Verify the result is null
        assertNull(woodenTrunks);
    }
}
