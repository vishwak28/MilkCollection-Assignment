package com.milkcollection.service.impl;

import com.milkcollection.dto.VillageRequest;
import com.milkcollection.dto.VillageResponse;
import com.milkcollection.entity.Village;
import com.milkcollection.exception.ResourceNotFoundException;
import com.milkcollection.repository.VillageRepository;
import com.milkcollection.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VillageServiceImpl implements VillageService {

    private final VillageRepository villageRepository;

    @Override
    public VillageResponse create(VillageRequest request) {
        Village village = Village.builder()
                .name(request.getName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
        return VillageResponse.from(villageRepository.save(village));
    }

    @Override
    public List<VillageResponse> listAll() {
        return villageRepository.findAll().stream().map(VillageResponse::from).toList();
    }

    @Override
    public Village getEntityById(Long id) {
        return villageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Village not found: " + id));
    }

    @Override
    public VillageResponse getById(Long id) {
        return VillageResponse.from(getEntityById(id));
    }
}