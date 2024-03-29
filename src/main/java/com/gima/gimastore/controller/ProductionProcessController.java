package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.productionProcess.ProductionAPIRequest;
import com.gima.gimastore.model.productionProcess.ProductionReturnRequest;
import com.gima.gimastore.model.productionProcess.StorePartProductionRequest;
import com.gima.gimastore.service.ProductProcessService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.gima.gimastore.constant.ResponseCodes.SUCCESS;

@RestController
@RequestMapping("/productProcess")
@CrossOrigin(origins = "*")
public class ProductionProcessController {
    private ProductProcessService productProcessService;

    private static final Logger logger = LoggerFactory.getLogger(ProductionProcessController.class);

    public ProductionProcessController(ProductProcessService productProcessService) {
        this.productProcessService = productProcessService;
    }

    @GetMapping("/productParts")
    public ResponseEntity<?> getProductParts(@RequestParam Long productId, @RequestParam Integer expectedAmount) {
        try {

            return new ResponseEntity<>(productProcessService.getProductParts(productId, expectedAmount), HttpStatus.OK);

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

    @GetMapping("/getAllProductionRequestByStore")
    public ResponseEntity<?> getAllProductionRequest(@RequestParam Long storeId, Pageable pageable) {
        try {
            ;
            return new ResponseEntity<>(productProcessService.getProductionRequestsByStore(storeId, pageable), HttpStatus.OK);

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

    @GetMapping("/getRequestsByStoreAndRequestId")
    public ResponseEntity<?> getRequestsByStoreAndRequestId(@RequestParam Long storeId, @RequestParam Long requestId) {
        try {

            return new ResponseEntity<>(productProcessService.getRequestsByStoreAndRequestId(storeId, requestId), HttpStatus.OK);

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

    @GetMapping("/getAllProductionRequest")
    public ResponseEntity<?> getAllProductionRequest(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(productProcessService.getAllProductionRequest(params, pageable), HttpStatus.OK);

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

    @GetMapping("/getProductPartsByRequestId")
    public ResponseEntity<?> getProductPartsByRequestId(@RequestParam String requestId) {
        try {

            return new ResponseEntity<>(productProcessService.getProductPartsByRequestId(requestId), HttpStatus.OK);

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

    @PostMapping("/confirmProductionRequestInStore")
    public ResponseEntity<?> confirmProductionRequestInStore(@RequestBody StorePartProductionRequest request) {
        try {
            productProcessService.confirmProductionRequestInStore(request);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تم تأكيد الخروج" + SUCCESS.getMessage()), HttpStatus.OK);

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

    @PostMapping("/confirmProductionRequest")
    public ResponseEntity<?> confirmProductionRequest(@RequestParam String requestId, @RequestParam Integer exactlyAmount) {
        try {
            productProcessService.confirmProductionRequest(requestId, exactlyAmount);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تم تأكيد طلب الإنتاج" + SUCCESS.getMessage()), HttpStatus.OK);

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

    //    @PostMapping("/productionReturn")
    //    public ResponseEntity<?> productionReturn(@RequestBody ProductionReturnRequest productionReturnRequest) {
    //        try {
    //            productProcessService.addProductionRequestReturn(productionReturnRequest);
    //            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تم تأكيد طلب الإنتاج" + SUCCESS.getMessage()), HttpStatus.OK);
    //
    //        } catch (ApplicationException e) {
    //            logger.error(e.getMessage(), e);
    //            e.printStackTrace();
    //            return new ResponseEntity<>(e.getStatus(), HttpStatus.BAD_REQUEST);
    //        } catch (Exception ex) {
    //            logger.error(ex.getMessage(), ex);
    //            ex.printStackTrace();
    //            return new ResponseEntity<>(Utils.internalServerError(ex.getMessage()),
    //                    HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //
    //    }
}
