package ro.sapientia.furniture.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ro.sapientia.furniture.model.Window;
import ro.sapientia.furniture.repository.WindowRepository;

public class WindowServiceTest {

    private WindowRepository repositoryMock;

    private WindowService service;

    @BeforeEach
    public void setUp() {
        // Create a mock instance of WindowRepository
        repositoryMock = mock(WindowRepository.class);
        // Initialize the service with the mocked repository
        service = new WindowService(repositoryMock);
    }

    @Test
    public void testFindAllWindows_emptyList() {
        // Mock the repository to return an empty list
        when(repositoryMock.findAll()).thenReturn(Collections.emptyList());

        // Call the service method
        final List<Window> windows = service.findAllWindows();

        // Assert that the list is empty
        assertEquals(0, windows.size());
    }

    @Test
    public void testFindAllWindows_null() {
        // Mock the repository to return null
        when(repositoryMock.findAll()).thenReturn(null);

        // Call the service method
        final List<Window> windows = service.findAllWindows();

        // Assert that the result is null
        assertNull(windows);
    }
}
