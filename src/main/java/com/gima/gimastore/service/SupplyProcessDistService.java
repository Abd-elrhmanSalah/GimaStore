package com.gima.gimastore.service;

import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.entity.supplyProcessPartDist.SupplyProcessPartDist;
import com.gima.gimastore.model.supplyProcess.SupplyProcessPartDistDTO;
import com.gima.gimastore.repository.StorePartRepository;
import com.gima.gimastore.repository.SupplyProcessPartDistRepository;
import com.gima.gimastore.repository.SupplyProcessPartsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SupplyProcessDistService {
    private SupplyProcessPartDistRepository supplyProcessPartDistRepository;
    private StorePartRepository storePartRepository;
    private SupplyProcessPartsRepository supplyProcessPartsRepository;

    public SupplyProcessDistService(SupplyProcessPartDistRepository supplyProcessPartDistRepository, StorePartRepository storePartRepository, SupplyProcessPartsRepository supplyProcessPartsRepository) {
        this.supplyProcessPartDistRepository = supplyProcessPartDistRepository;
        this.storePartRepository = storePartRepository;
        this.supplyProcessPartsRepository = supplyProcessPartsRepository;
    }

    @Transactional
    public void add(SupplyProcessPartDistDTO supplyProcessPartDistDTO) {


        Optional<SupplyProcessPart> supplyProcessPartById = supplyProcessPartsRepository.findById(supplyProcessPartDistDTO.getSupplyProcessPart().getId());

        supplyProcessPartDistDTO.getStoreRequests().forEach(dto -> {
            SupplyProcessPartDist supplyProcessPartDist = new SupplyProcessPartDist();
            supplyProcessPartDist.setSupplyProcessPart(supplyProcessPartDistDTO.getSupplyProcessPart());

            supplyProcessPartDist.setStore(dto.getStore());
            supplyProcessPartDist.setAmount(dto.getAmount());
            supplyProcessPartDistRepository.save(supplyProcessPartDist);

            supplyProcessPartById.get().setRemainAmount(supplyProcessPartById.get().getRemainAmount() - dto.getAmount());
            supplyProcessPartById.get().setDistAmount(supplyProcessPartById.get().getDistAmount() + dto.getAmount());
            if (supplyProcessPartById.get().getRemainAmount() == 0)
                supplyProcessPartById.get().setFullDist(true);

            supplyProcessPartsRepository.save(supplyProcessPartById.get());
        });

        if (supplyProcessPartById.get().getPartialDist() == false)
            supplyProcessPartById.get().setPartialDist(true);

//        Part part = null;
//        if (!supplyProcessPartById.isEmpty()) {
//            part = supplyProcessPartById.get().getPart();
//        }

//        Optional<StorePart> byStoreAndPart = storePartRepository.findByStoreAndPart(supplyProcessPartDist.getStore(), part);
//
//        if (!byStoreAndPart.isEmpty()) {
//            Integer amount = byStoreAndPart.get().getAmount();
//            byStoreAndPart.get().setAmount(amount + supplyProcessPartDist.getAmount());
//            storePartRepository.save(byStoreAndPart.get());
//
//        } else {
//            StorePart storePart = new StorePart();
//            storePart.setPart(part);
//            storePart.setStore(supplyProcessPartDist.getStore());
//            storePart.setAmount(supplyProcessPartDist.getAmount());
//            storePartRepository.save(storePart);
//        }

    }

}
