package ro.sapientia.furniture.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.sapientia.furniture.model.Drawer;
import ro.sapientia.furniture.repository.DrawerRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrawerServiceTest {

    @Mock
    private DrawerRepository drawerRepository;

    @InjectMocks
    private DrawerService drawerService;

    @Test
    void findOpenDrawers_ShouldReturnOnlyOpenDrawers() {
        // Given
        Drawer openDrawer = Drawer.builder()
                .isOpen(true)
                .build();
        when(drawerRepository.findByIsOpenTrue())
                .thenReturn(Collections.singletonList(openDrawer));

        // When
        List<Drawer> openDrawers = drawerService.findOpenDrawers();

        // Then
        assertThat(openDrawers).isNotEmpty();
        assertThat(openDrawers).allMatch(Drawer::isOpen);
    }

    @Test
    void createDrawer_ShouldReturnSavedDrawer() {
        // Given
        Drawer drawer = Drawer.builder()
                .material("Wood")
                .color("Brown")
                .build();
        when(drawerRepository.save(any(Drawer.class)))
                .thenReturn(drawer);

        // When
        Drawer savedDrawer = drawerService.createDrawer(drawer);

        // Then
        assertThat(savedDrawer).isNotNull();
        assertThat(savedDrawer.getMaterial()).isEqualTo("Wood");
    }
}