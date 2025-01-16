package ro.sapientia.furniture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sapientia.furniture.model.RockingChairModel;
import ro.sapientia.furniture.repository.RockingChairRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RockingChairService {
    final RockingChairRepository rockingChairRepository;

    public RockingChairService(final RockingChairRepository rockingChairRepository) {
        this.rockingChairRepository = rockingChairRepository;
    }

    public List<RockingChairModel> findRockingChairById(Long id) {
        return this.rockingChairRepository.findRockingChairById(id);
    }

    public ArrayList<RockingChairModel> findRockingChairByMaterial(String material) {
        return this.rockingChairRepository.findRockingChairByMaterial(material);
    }

    public ArrayList<RockingChairModel> findRockingChairByRockingRadius(double minAngle,double maxAngle) {
        return this.rockingChairRepository.findAllRockingChairModelByRockerRadiusBetween(minAngle, maxAngle);
    }

    public List<RockingChairModel> findAllRockingCharis() {
        return this.rockingChairRepository.findAll();
    }

    public RockingChairModel saveRockingChair(RockingChairModel rockingChairModel) {
        return this.rockingChairRepository.save(rockingChairModel);
    }


}
