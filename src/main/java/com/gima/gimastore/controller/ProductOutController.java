package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.productionProcess.ProductOutRequest;
import com.gima.gimastore.service.PartService;
import com.gima.gimastore.service.ProductOutService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.gima.gimastore.constant.ResponseCodes.SUCCESS;

@RestController
@RequestMapping("/productOut")
@CrossOrigin(origins = "*")
public class ProductOutController {
    private ProductOutService productOutService;

    private static final Logger logger = LoggerFactory.getLogger(ProductOutController.class);

    public ProductOutController(ProductOutService productOutService) {
        this.productOutService = productOutService;
    }

    @PostMapping("/addProductOut")
    public ResponseEntity<?> addPart(@RequestBody ProductOutRequest request) {
        try {

            productOutService.addProductOut(request);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تمت خروج المنتجات" + SUCCESS.getMessage()), HttpStatus.OK);

        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return new ResponseEntity<>(Utils.internalServerError(ex.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllProductOut")
    public ResponseEntity<?> getAllProductOut(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(productOutService.getProductsOut(params, pageable), HttpStatus.OK);

        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return new ResponseEntity<>(Utils.internalServerError(ex.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

