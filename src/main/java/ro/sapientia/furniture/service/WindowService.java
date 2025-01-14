package ro.sapientia.furniture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.Window;
import ro.sapientia.furniture.repository.WindowRepository;

@Service
public class WindowService {

    private final WindowRepository windowRepository;

    public WindowService(final WindowRepository windowRepository) {
        this.windowRepository = windowRepository;
    }

    public List<Window> findAllWindows() {
        return this.windowRepository.findAll();
    }

    public Window findWindowById(final Long id) {
        return this.windowRepository.findWindowById(id);
    }

    public Window create(Window window) {
        return this.windowRepository.saveAndFlush(window);
    }

    public Window update(Window window) {
        return this.windowRepository.saveAndFlush(window);
    }

    public void delete(Long id) {
        this.windowRepository.deleteById(id);
    }
}
