package com.milkcollection.service.impl;

import com.milkcollection.dto.TankerRequest;
import com.milkcollection.dto.TankerResponse;
import com.milkcollection.entity.Tanker;
import com.milkcollection.exception.ResourceNotFoundException;
import com.milkcollection.repository.TankerRepository;
import com.milkcollection.service.TankerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TankerServiceImpl implements TankerService {

    private final TankerRepository tankerRepository;

    @Override
    public TankerResponse create(TankerRequest request) {
        if (tankerRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new IllegalArgumentException("Tanker already registered: " + request.getRegistrationNumber());
        }
        Tanker tanker = Tanker.builder()
                .registrationNumber(request.getRegistrationNumber())
                .capacityLitres(request.getCapacityLitres())
                .active(true)
                .build();
        return TankerResponse.from(tankerRepository.save(tanker));
    }

    @Override
    public List<TankerResponse> listAll() {
        return tankerRepository.findAll().stream().map(TankerResponse::from).toList();
    }

    @Override
    public Tanker getEntityById(Long id) {
        return tankerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tanker not found: " + id));
    }

    @Override
    public TankerResponse getById(Long id) {
        return TankerResponse.from(getEntityById(id));
    }
}