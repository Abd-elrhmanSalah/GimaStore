package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.productionProcess.ProductionAPIRequest;
import com.gima.gimastore.service.LoginService;
import com.gima.gimastore.service.ProductProcessService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.gima.gimastore.constant.ResponseCodes.SUCCESS;

@RestController
@RequestMapping("/productProcess")
@CrossOrigin(origins = "*")
public class ProductProcessController {
    private ProductProcessService productProcessService;

    private static final Logger logger = LoggerFactory.getLogger(ProductProcessController.class);

    public ProductProcessController(ProductProcessService productProcessService) {
        this.productProcessService = productProcessService;
    }

    @GetMapping("/productParts")
    public ResponseEntity<?> getProductParts(@RequestParam Long productId, @RequestParam Integer expectedAmount,
                                             @RequestParam Long storeId) {
        try {

            return new ResponseEntity<>(productProcessService.getProductParts(productId, expectedAmount, storeId), HttpStatus.OK);

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

    @PostMapping("/productionRequest")
    public ResponseEntity<?> addProductRequest(@RequestBody ProductionAPIRequest productionAPIRequest) {
        try {
            productProcessService.addProductionRequest(productionAPIRequest);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تمت إضافة طلب الإنتاج " + SUCCESS.getMessage()), HttpStatus.OK);

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
