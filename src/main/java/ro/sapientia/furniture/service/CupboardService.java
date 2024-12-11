package ro.sapientia.furniture.service;

import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.Cupboard;
import ro.sapientia.furniture.repository.CupboardRepository;

import java.util.List;

@Service
public class CupboardService {
    private final CupboardRepository cupboardRepository;

    public CupboardService(final CupboardRepository cupboardRepository){
        this.cupboardRepository = cupboardRepository;
    }

    public List<Cupboard> findAllCupboard() { return this.cupboardRepository.findAll(); }

    public Cupboard findCupboardById(final Long id){
        return this.cupboardRepository.findCupboardById(id);
    }

    public Cupboard create(Cupboard cupboard){
        return this.cupboardRepository.saveAndFlush(cupboard);
    }

    public Cupboard update(Cupboard cupboard){
        return this.cupboardRepository.saveAndFlush(cupboard);
    }

    public void delete(Long id) { this.cupboardRepository.deleteById(id);}
}
