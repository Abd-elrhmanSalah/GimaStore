package com.gima.gimastore.service;

import com.gima.gimastore.model.SupplyProcessPartsDTO;
import com.gima.gimastore.repository.CommonRepo;
import com.gima.gimastore.repository.SupplyProcessPartsRepository;
import com.gima.gimastore.repository.SupplyProcessRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyProcessService implements CommonRepo<SupplyProcessPartsDTO> {

    private SupplyProcessRepository supplyProcessRepo;
    private SupplyProcessPartsRepository supplyProcessPartsRepo;

    public SupplyProcessService(SupplyProcessRepository supplyProcessRepo, SupplyProcessPartsRepository supplyProcessPartsRepo) {
        this.supplyProcessRepo = supplyProcessRepo;
        this.supplyProcessPartsRepo = supplyProcessPartsRepo;
    }

    @Override
    public void add(SupplyProcessPartsDTO dto) {

    }

    @Override
    public void update(SupplyProcessPartsDTO dto) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public SupplyProcessPartsDTO findById(Long id) {
        return null;
    }

    @Override
    public List<SupplyProcessPartsDTO> findAll() {
        return null;
    }
}
