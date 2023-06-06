package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.entity.supplyProcessPartDist.SupplyProcessPartDist;
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
    public void add(SupplyProcessPartDist supplyProcessPartDist) {

        supplyProcessPartDistRepository.save(supplyProcessPartDist);

        Optional<SupplyProcessPart> supplyProcessPartById = supplyProcessPartsRepository.findById(supplyProcessPartDist.getSupplyProcessPart().getId());

        Part part = null;
        if (!supplyProcessPartById.isEmpty()) {
            part = supplyProcessPartById.get().getPart();
        }
        Optional<StorePart> byStoreAndPart = storePartRepository.findByStoreAndPart(supplyProcessPartDist.getStore(), part);

        if (!byStoreAndPart.isEmpty()) {
            Integer amount = byStoreAndPart.get().getAmount();
            byStoreAndPart.get().setAmount(amount + supplyProcessPartDist.getAmount());
            storePartRepository.save(byStoreAndPart.get());

        } else {
            StorePart storePart = new StorePart();
            storePart.setPart(part);
            storePart.setStore(supplyProcessPartDist.getStore());
            storePart.setAmount(supplyProcessPartDist.getAmount());
            storePartRepository.save(storePart);
        }
        supplyProcessPartById.get().setPartialDist(true);
        int i = supplyProcessPartById.get().getRemainAmount() - supplyProcessPartDist.getAmount();

        supplyProcessPartById.get().setRemainAmount(i);
        supplyProcessPartById.get().setDistAmount(supplyProcessPartById.get().getDistAmount() + supplyProcessPartDist.getAmount());

        supplyProcessPartsRepository.save(supplyProcessPartById.get());
    }

}
