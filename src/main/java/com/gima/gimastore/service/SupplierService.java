package com.gima.gimastore.service;

import com.gima.gimastore.entity.Supplier;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.SupplierDTO;
import com.gima.gimastore.repository.SupplierRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.NO_SUPPLIER_ID;
import static com.gima.gimastore.constant.ResponseCodes.REPEATED_SUPPLIERNAME;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepo;
    
    public void add(SupplierDTO supplierDTOParam) {
        validateSupplierName(supplierDTOParam.getSupplierName());
        Supplier map = ObjectMapperUtils.map(supplierDTOParam, Supplier.class);
        supplierRepo.save(map);
    }


    public void update(SupplierDTO supplierDTOParam) {
        validateExistSupplier(supplierDTOParam.getId());

        validateSupplierNameAndID(supplierDTOParam.getSupplierName(), supplierDTOParam.getId());

        supplierRepo.save(ObjectMapperUtils.map(supplierDTOParam, Supplier.class));

    }


    public void delete(Long id) {
        Optional<Supplier> partById = validateExistSupplier(id);
        partById.get().setLocked(true);
        supplierRepo.save(partById.get());

    }


    public SupplierDTO findById(Long id) {
        validateExistSupplier(id);
        return ObjectMapperUtils.map(supplierRepo.findById(id).get(), SupplierDTO.class);
    }


    public Page<Supplier> findAll(Pageable pageable) {

        return supplierRepo.findAll(pageable);
    }

    private void validateSupplierName(String supplierName) {
        if (supplierRepo.existsBySupplierName(supplierName))
            throw new ApplicationException(new StatusResponse(REPEATED_SUPPLIERNAME.getCode(), REPEATED_SUPPLIERNAME.getKey(), REPEATED_SUPPLIERNAME.getMessage()));

    }

    private void validateSupplierNameAndID(String supplierName, Long supplierId) {
        if (!supplierRepo.findById(supplierId).get().getSupplierName().equals(supplierName))
            validateSupplierName(supplierName);
    }

    private Optional<Supplier> validateExistSupplier(Long id) {
        Optional<Supplier> partById = supplierRepo.findById(id);
        if (partById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_SUPPLIER_ID.getCode(), NO_SUPPLIER_ID.getKey(), NO_SUPPLIER_ID.getMessage()));

        return partById;
    }
}
