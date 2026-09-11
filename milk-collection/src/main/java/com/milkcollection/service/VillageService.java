package com.milkcollection.service;

import com.milkcollection.dto.VillageRequest;
import com.milkcollection.dto.VillageResponse;
import com.milkcollection.entity.Village;

import java.util.List;

public interface VillageService {

    public VillageResponse create(VillageRequest request);
    public List<VillageResponse> listAll();
    public Village getEntityById(Long id);
    public VillageResponse getById(Long id);
}
