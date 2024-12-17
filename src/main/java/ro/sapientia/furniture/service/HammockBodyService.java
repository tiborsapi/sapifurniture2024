package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.HammockBody;
import ro.sapientia.furniture.repository.HammockBodyRepository;
import java.util.List;

@Service
public class HammockBodyService {
    private final HammockBodyRepository hammockBodyRepository;

    public HammockBodyService(final HammockBodyRepository hammockBodyRepository) {
        this.hammockBodyRepository = hammockBodyRepository;
    }

    public List<HammockBody> findAllHammockBodies() {
        return this.hammockBodyRepository.findAll();
    }

    public HammockBody findHammockBodyById(final Long id) {
        return this.hammockBodyRepository.findHammockBodyById(id);
    }

    public HammockBody create(HammockBody hammockBody) {
        return this.hammockBodyRepository.saveAndFlush(hammockBody);
    }

    public HammockBody update(HammockBody hammockBody) {
        return this.hammockBodyRepository.saveAndFlush(hammockBody);
    }

    public void delete(Long id) {
        try {
            this.hammockBodyRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
