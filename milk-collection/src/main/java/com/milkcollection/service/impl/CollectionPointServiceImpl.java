package com.milkcollection.service.impl;

import com.milkcollection.dto.CollectionPointRequest;
import com.milkcollection.dto.CollectionPointResponse;
import com.milkcollection.entity.CollectionPoint;
import com.milkcollection.entity.Village;
import com.milkcollection.exception.ResourceNotFoundException;
import com.milkcollection.repository.CollectionPointRepository;
import com.milkcollection.service.CollectionPointService;
import com.milkcollection.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionPointServiceImpl implements CollectionPointService {

    private final CollectionPointRepository collectionPointRepository;
    private final VillageService villageService;

    @Override
    public CollectionPointResponse create(CollectionPointRequest request) {
        Village village = villageService.getEntityById(request.getVillageId());
        CollectionPoint point = CollectionPoint.builder()
                .name(request.getName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .active(true)
                .village(village)
                .build();
        return CollectionPointResponse.from(collectionPointRepository.save(point));
    }

    @Override
    public List<CollectionPointResponse> listAll() {
        return collectionPointRepository.findAll().stream().map(CollectionPointResponse::from).toList();
    }

    @Override
    public List<CollectionPointResponse> listByVillage(Long villageId) {
        return collectionPointRepository.findByVillageId(villageId).stream().map(CollectionPointResponse::from).toList();
    }

    @Override
    public CollectionPoint getEntityById(Long id) {
        return collectionPointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection point not found: " + id));
    }

    @Override
    public CollectionPointResponse getById(Long id) {
        return CollectionPointResponse.from(getEntityById(id));
    }
}