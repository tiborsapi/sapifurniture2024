package ro.sapientia.furniture.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ro.sapientia.furniture.model.Drawer;
import ro.sapientia.furniture.model.Drawer.DrawerStatus;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DrawerRepositoryTest {

    @Autowired
    private DrawerRepository drawerRepository;

    @Test
    void findByIsOpen_ShouldReturnOpenDrawers() {
        // Given
        Drawer openDrawer = Drawer.builder()
                .material("Wood")
                .color("Brown")
                .height(15.0)
                .width(50.0)
                .depth(45.0)
                .weight(5.0)
                .isOpen(true)
                .maxOpenDistance(40.0)
                .currentOpenDistance(10.0)
                .weightCapacity(25.0)
                .status(DrawerStatus.FUNCTIONAL)
                .description("Test drawer")
                .build();
        drawerRepository.save(openDrawer);

        // When
        List<Drawer> openDrawers = drawerRepository.findByIsOpenTrue();

        // Then
        assertThat(openDrawers).isNotEmpty();
        assertThat(openDrawers).allMatch(Drawer::isOpen);
    }
}
