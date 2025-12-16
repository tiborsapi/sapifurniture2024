package ro.sapientia.furniture.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import ro.sapientia.furniture.model.ComponentList;
import ro.sapientia.furniture.model.RawMaterial;
import ro.sapientia.furniture.repository.ComponentListRepository;
import ro.sapientia.furniture.repository.RawMaterialRepository;
import ro.sapientia.furniture.service.RawMaterialService;

import org.springframework.stereotype.Service;

import ro.sapientia.furniture.model.FurnitureBody;
import ro.sapientia.furniture.repository.FurnitureBodyRepository;

@Service
public class FurnitureBodyService {
	
	private final FurnitureBodyRepository furnitureBodyRepository;
	private final ComponentListRepository componentListRepository;
	private final RawMaterialService rawMaterialService;
	private final RawMaterialRepository rawMaterialRepository;
	
	@Autowired
	public FurnitureBodyService(final FurnitureBodyRepository furnitureBodyRepository,
								final ComponentListRepository componentListRepository,
								final RawMaterialRepository rawMaterialRepository,
								final RawMaterialService rawMaterialService) {
		this.furnitureBodyRepository = furnitureBodyRepository;
		this.componentListRepository = componentListRepository;
		this.rawMaterialRepository = rawMaterialRepository;
		this.rawMaterialService = rawMaterialService;
	}

	// Backwards-compatible constructor used by some tests — other deps may be null.
	public FurnitureBodyService(final FurnitureBodyRepository furnitureBodyRepository) {
		this.furnitureBodyRepository = furnitureBodyRepository;
		this.componentListRepository = null;
		this.rawMaterialRepository = null;
		this.rawMaterialService = null;
	}
	
	public List<FurnitureBody> findAllFurnitureBodies() {
		return this.furnitureBodyRepository.findAll();
	}

	public FurnitureBody findFurnitureBodyById(final Long id) {
		return this.furnitureBodyRepository.findFurnitureBodyById(id);
	}

	public FurnitureBody create(FurnitureBody furnitureBody) {
		return this.furnitureBodyRepository.saveAndFlush(furnitureBody);
	}

	public FurnitureBody update(FurnitureBody furnitureBody) {
		return this.furnitureBodyRepository.saveAndFlush(furnitureBody);
	}

	public void delete(Long id) {
		this.furnitureBodyRepository.deleteById(id);
	}

	@Transactional
	public FurnitureBody saveFurnitureBodyAndComponents(FurnitureBody fb, Long createdByUserId) {
		// 1) Save furniture body (frontElements cascade = ALL -> frontElements saved)
		FurnitureBody savedFb = furnitureBodyRepository.save(fb);

		// 2) Build component list from model method
		ComponentList cl = savedFb.getRawMaterials();
		cl.setCreatedBy(createdByUserId);

		// 3) Set componentList reference on all raw materials
		List<RawMaterial> candidateMaterials = cl.getRawMaterials();
		if (candidateMaterials == null) {
			candidateMaterials = new ArrayList<>();
			cl.setRawMaterials(candidateMaterials);
		} else {
			for (RawMaterial rm : candidateMaterials) {
				rm.setComponentList(cl);
			}
		}

		// 4) Save component list first so it has an ID
		ComponentList savedCl = componentListRepository.save(cl);

		// 5) Save raw materials using dedup service
		List<RawMaterial> persistedMaterials = new ArrayList<>();
		for (RawMaterial rm : candidateMaterials) {
			rm.setComponentList(savedCl);
			RawMaterial persisted = rawMaterialService.findOrCreateAndIncreaseQuantity(rm);
			persistedMaterials.add(persisted);
		}

		// 6) Update component list with persisted raw materials
		savedCl.setRawMaterials(persistedMaterials);
		componentListRepository.save(savedCl);

		return savedFb;
	}

}
