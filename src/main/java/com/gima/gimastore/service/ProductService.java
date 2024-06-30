package com.gima.gimastore.service;

import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductPart;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartRequest;
import com.gima.gimastore.model.productionProcess.ProductDTO;
import com.gima.gimastore.model.productionProcess.ProductPartDTO;
import com.gima.gimastore.repository.ProductPartRepository;
import com.gima.gimastore.repository.ProductRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static com.gima.gimastore.constant.ResponseCodes.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final ProductPartRepository productPartRepo;

    public void addProduct(ProductDTO productParamDTO, MultipartFile file) throws IOException {
        validateProductName(productParamDTO.getProductName());

        Product savedProduct = productRepo.save(ObjectMapperUtils.map(productParamDTO, Product.class));

        if (!file.isEmpty())
            savedProduct.setPicture(ImageUtil.compressImage(file.getBytes()));

        productRepo.save(savedProduct);
        addProductParts(productParamDTO, savedProduct);
    }

    @Transactional
    public void update(ProductDTO productParamDTO, MultipartFile file) throws IOException {

        Optional<Product> productById = validateExistProduct(productParamDTO.getId());
        validateProductAndID(productParamDTO.getProductName(), productParamDTO.getId());

        if (productById.get().getPicture() != null)
            productParamDTO.setPicture(productById.get().getPicture());

        Product savedProduct = productRepo.save(ObjectMapperUtils.map(productParamDTO, Product.class));
        if (!file.isEmpty())
            savedProduct.setPicture(ImageUtil.compressImage(file.getBytes()));

        productRepo.save(savedProduct);
        productPartRepo.deleteAllByProduct(savedProduct);
        addProductParts(productParamDTO, savedProduct);
    }

    private void addProductParts(ProductDTO productParamDTO, Product savedProduct) {
        productParamDTO.getParts().forEach(partRequest -> {
            ProductPartDTO productPartDTO = ObjectMapperUtils.map(partRequest, ProductPartDTO.class);
            productPartDTO.setProduct(savedProduct);

            productPartRepo.save(ObjectMapperUtils.map(productPartDTO, ProductPart.class));
        });
    }

    public void delete(Long id) {
        Optional<Product> productById = validateExistProduct(id);
        if (productById.get().getLocked())
            productById.get().setLocked(false);
        else productById.get().setLocked(true);
        productRepo.save(productById.get());
    }


    public ProductDTO findById(Long id) throws DataFormatException, IOException {
        Optional<Product> productById = productRepo.findById(id);
        if (productById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PRODUCT_ID.getCode(), NO_PRODUCT_ID.getKey(), NO_PRODUCT_ID.getMessage()));

//        if (productById.get().getLocked())
//            throw new ApplicationException(new StatusResponse(LOCK_UNLOCK_ERROR.getCode(), LOCK_UNLOCK_ERROR.getKey(), LOCK_UNLOCK_ERROR.getMessage()));

        ProductDTO productDTO = ObjectMapperUtils.map(productById.get(), ProductDTO.class);
        if (!Objects.isNull(productById.get().getPicture()))
            productDTO.setPicture(ImageUtil.decompressImage(productById.get().getPicture()));

        List<ProductPart> allByProduct = productPartRepo.findAllByProduct(productById.get());
        allByProduct.forEach(byProduct -> {
            PartRequest partRequest = new PartRequest();
            if(byProduct.getPart().getPicture()!=null)
            byProduct.getPart().setPicture(ImageUtil.decompressImage(byProduct.getPart().getPicture()));
            partRequest.setPart(byProduct.getPart());
            partRequest.setAmount(byProduct.getAmount());
            productDTO.getParts().add(partRequest);
        });

        return productDTO;
    }


    public Page<Product> findAll(Pageable pageable) {

        Page<Product> all = productRepo.findAll(pageable);
        all.getContent().stream().forEach(part -> {

            if (!Objects.isNull(part.getPicture())) {
                part.setPicture(ImageUtil.decompressImage(part.getPicture()));
            }

        });
        return all;

    }

    private void validateProductName(String productName) {
        if (productRepo.existsByProductName(productName))
            throw new ApplicationException(new StatusResponse(REPEATED_PRODUCT_NAME.getCode(), REPEATED_PRODUCT_NAME.getKey(),
                    REPEATED_PRODUCT_NAME.getMessage()));

    }

    private void validateProductAndID(String partName, Long partId) {
        if (!productRepo.findById(partId).get().getProductName().equals(partName))
            validateProductName(partName);
    }

    private Optional<Product> validateExistProduct(Long id) {
        Optional<Product> productById = productRepo.findById(id);
        if (productById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PRODUCT_ID.getCode(), NO_PRODUCT_ID.getKey(),
                    NO_PRODUCT_ID.getMessage()));
        return productById;
    }
}
