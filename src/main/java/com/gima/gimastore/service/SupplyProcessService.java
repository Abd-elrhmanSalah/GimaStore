package com.gima.gimastore.service;

import com.gima.gimastore.model.SupplyProcessRequest;
import com.gima.gimastore.repository.CommonRepo;
import com.gima.gimastore.repository.SupplyProcessPartsRepository;
import com.gima.gimastore.repository.SupplyProcessRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyProcessService implements CommonRepo<SupplyProcessRequest> {

    private SupplyProcessRepository supplyProcessRepo;
    private SupplyProcessPartsRepository supplyProcessPartsRepo;

    public SupplyProcessService(SupplyProcessRepository supplyProcessRepo, SupplyProcessPartsRepository supplyProcessPartsRepo) {
        this.supplyProcessRepo = supplyProcessRepo;
        this.supplyProcessPartsRepo = supplyProcessPartsRepo;
    }


    @Override
    public void add(SupplyProcessRequest request) {



    }

    @Override
    public void update(SupplyProcessRequest request) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public SupplyProcessRequest findById(Long id) {
        return null;
    }

    @Override
    public List<SupplyProcessRequest> findAll() {
        return null;
    }
}
