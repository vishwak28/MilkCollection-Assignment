package com.milkcollection.service;

import com.milkcollection.dto.FarmerRequest;
import com.milkcollection.dto.FarmerResponse;

import java.util.List;

public interface FarmerService {
    public FarmerResponse create(FarmerRequest request);
    public List<FarmerResponse> listAll();
    public List<FarmerResponse> listByCollectionPoint(Long collectionPointId);
    public FarmerResponse getById(Long id);

}
