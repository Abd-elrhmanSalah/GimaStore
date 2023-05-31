package com.gima.gimastore.service;

import com.gima.gimastore.entity.Supplier;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.SupplierDTO;
import com.gima.gimastore.repository.CommonRepo;
import com.gima.gimastore.repository.SupplierRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.NO_SUPPLIER_ID;
import static com.gima.gimastore.constant.ResponseCodes.REPEATED_SUPPLIERNAME;

@Service
public class SupplierService implements CommonRepo<SupplierDTO> {
    private SupplierRepository supplierRepo;

    public SupplierService(SupplierRepository supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    @Override
    public void add(SupplierDTO supplierDTOParam) {
        validateSupplierName(supplierDTOParam.getSupplierName());
        supplierRepo.save(ObjectMapperUtils.map(supplierDTOParam, Supplier.class));
    }

    @Override
    public void update(SupplierDTO supplierDTOParam) {
        validateExistSupplier(supplierDTOParam.getId());

        validateSupplierNameAndID(supplierDTOParam.getSupplierName(), supplierDTOParam.getId());

        supplierRepo.save(ObjectMapperUtils.map(supplierDTOParam, Supplier.class));

    }

    @Override
    public void delete(Long id) {
        validateExistSupplier(id);
        supplierRepo.deleteById(id);

    }

    @Override
    public SupplierDTO findById(Long id) {
        validateExistSupplier(id);
        return ObjectMapperUtils.map(supplierRepo.findById(id).get(), SupplierDTO.class);
    }

    @Override
    public List<SupplierDTO> findAll() {
        return ObjectMapperUtils.mapAll(supplierRepo.findAll(), SupplierDTO.class);
    }

    private void validateSupplierName(String supplierName) {
        if (supplierRepo.existsBySupplierName(supplierName))
            throw new ApplicationException(new StatusResponse(REPEATED_SUPPLIERNAME.getCode(), REPEATED_SUPPLIERNAME.getKey(), REPEATED_SUPPLIERNAME.getMessage()));

    }

    private void validateSupplierNameAndID(String supplierName, Long supplierId) {
        if (!supplierRepo.findById(supplierId).get().getSupplierName().equals(supplierName))
            validateSupplierName(supplierName);
    }

    private void validateExistSupplier(Long id) {
        Optional<Supplier> partById = supplierRepo.findById(id);
        if (partById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_SUPPLIER_ID.getCode(), NO_SUPPLIER_ID.getKey(), NO_SUPPLIER_ID.getMessage()));

    }
}
