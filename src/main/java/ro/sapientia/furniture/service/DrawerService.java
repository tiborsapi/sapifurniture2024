package ro.sapientia.furniture.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sapientia.furniture.model.Drawer;
import ro.sapientia.furniture.repository.DrawerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrawerService {
    private final DrawerRepository drawerRepository;

    @Transactional
    public Drawer createDrawer(Drawer drawer) {
        return drawerRepository.save(drawer);
    }

    @Transactional
    public Drawer updateDrawer(Long id, Drawer drawerDetails) {
        Drawer drawer = drawerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Drawer not found"));

        // Update properties
        drawer.setMaterial(drawerDetails.getMaterial());
        drawer.setColor(drawerDetails.getColor());
        drawer.setHeight(drawerDetails.getHeight());
        drawer.setWidth(drawerDetails.getWidth());
        drawer.setDepth(drawerDetails.getDepth());
        drawer.setWeight(drawerDetails.getWeight());
        drawer.setWeightCapacity(drawerDetails.getWeightCapacity());
        drawer.setMaxOpenDistance(drawerDetails.getMaxOpenDistance());
        drawer.setDescription(drawerDetails.getDescription());
        drawer.setStatus(drawerDetails.getStatus());

        return drawerRepository.save(drawer);
    }

    @Transactional
    public void openDrawer(Long id) {
        Drawer drawer = drawerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Drawer not found"));
        drawer.open();
        drawerRepository.save(drawer);
    }

    @Transactional
    public void closeDrawer(Long id) {
        Drawer drawer = drawerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Drawer not found"));
        drawer.close();
        drawerRepository.save(drawer);
    }

    @Transactional
    public void slideDrawer(Long id, Double distance) {
        Drawer drawer = drawerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Drawer not found"));
        drawer.slidePartially(distance);
        drawerRepository.save(drawer);
    }

    public List<Drawer> findByMaterial(String material) {
        return drawerRepository.findByMaterial(material);
    }

    public List<Drawer> findOpenDrawers() {
        return drawerRepository.findByIsOpenTrue();
    }

    public List<Drawer> findByMinimumCapacity(Double minCapacity) {
        return drawerRepository.findByMinimumWeightCapacity(minCapacity);
    }

    public List<Drawer> findByDimensions(Double width, Double height, Double depth) {
        return drawerRepository.findByDimensions(width, height, depth);
    }
}