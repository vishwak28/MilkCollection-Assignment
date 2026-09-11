package com.milkcollection.service.impl;

import com.milkcollection.dto.FarmerRequest;
import com.milkcollection.dto.FarmerResponse;
import com.milkcollection.entity.CollectionPoint;
import com.milkcollection.entity.Farmer;
import com.milkcollection.exception.ResourceNotFoundException;
import com.milkcollection.repository.FarmerRepository;
import com.milkcollection.service.CollectionPointService;
import com.milkcollection.service.FarmerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository farmerRepository;
    private final CollectionPointService collectionPointService;

    @Override
    public FarmerResponse create(FarmerRequest request) {
        CollectionPoint point = collectionPointService.getEntityById(request.getCollectionPointId());
        Farmer farmer = Farmer.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .active(true)
                .collectionPoint(point)
                .build();
        return FarmerResponse.from(farmerRepository.save(farmer));
    }

    @Override
    public List<FarmerResponse> listAll() {
        return farmerRepository.findAll().stream().map(FarmerResponse::from).toList();
    }

    @Override
    public List<FarmerResponse> listByCollectionPoint(Long collectionPointId) {
        return farmerRepository.findByCollectionPointId(collectionPointId).stream().map(FarmerResponse::from).toList();
    }

    @Override
    public FarmerResponse getById(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found: " + id));
        return FarmerResponse.from(farmer);
    }
}