package com.milkcollection.controller;

import com.milkcollection.dto.TankerRequest;
import com.milkcollection.dto.TankerResponse;
import com.milkcollection.service.TankerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tankers")
@RequiredArgsConstructor
public class TankerController {

    private final TankerService tankerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TankerResponse create(@RequestBody TankerRequest request) {
        return tankerService.create(request);
    }

    @GetMapping
    public List<TankerResponse> listAll() {
        return tankerService.listAll();
    }

    @GetMapping("/{id}")
    public TankerResponse getById(@PathVariable Long id) {
        return tankerService.getById(id);
    }
}