package com.milkcollection.service;

import com.milkcollection.dto.CollectionPointRequest;
import com.milkcollection.dto.CollectionPointResponse;
import com.milkcollection.entity.CollectionPoint;

import java.util.List;

public interface CollectionPointService {
    public CollectionPointResponse create(CollectionPointRequest request);
    public List<CollectionPointResponse> listAll();
    public List<CollectionPointResponse> listByVillage(Long villageId);
    public CollectionPoint getEntityById(Long id);
    public CollectionPointResponse getById(Long id);
}
