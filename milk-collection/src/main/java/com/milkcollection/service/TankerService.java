package com.milkcollection.service;

import com.milkcollection.dto.TankerRequest;
import com.milkcollection.dto.TankerResponse;
import com.milkcollection.entity.Tanker;

import java.util.List;

public interface TankerService {
    public TankerResponse create(TankerRequest request);
    public List<TankerResponse> listAll();
    public Tanker getEntityById(Long id);
    public TankerResponse getById(Long id);
}
