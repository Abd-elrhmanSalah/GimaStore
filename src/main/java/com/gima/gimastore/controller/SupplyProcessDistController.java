package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.supplyProcess.SupplyProcessPartDistDTO;
import com.gima.gimastore.service.SupplyProcessDistService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.gima.gimastore.constant.ResponseCodes.SUCCESS;

@RestController
@RequestMapping("/supplyProcessDist")
@CrossOrigin(origins = "*")
public class SupplyProcessDistController {
    private SupplyProcessDistService supplyProcessDistService;
    private static final Logger logger = LoggerFactory.getLogger(SupplyProcessDistController.class);

    public SupplyProcessDistController(SupplyProcessDistService supplyProcessDistService) {
        this.supplyProcessDistService = supplyProcessDistService;
    }

    @PostMapping
    public ResponseEntity<?> addSupplyProcessDist(@RequestBody SupplyProcessPartDistDTO supplyProcessPartDistDTO) {
        try {
            supplyProcessDistService.add(supplyProcessPartDistDTO);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تم التوزيع  " + SUCCESS.getMessage()), HttpStatus.OK);

        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return new ResponseEntity<>(Utils.internalServerError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/distByStoreAndStatus")
    public ResponseEntity<?> getSupplyProcessDistByStoreAndStatus(@RequestParam Long storeId, @RequestParam Long statusId, Pageable pageable) {
        try {

            return new ResponseEntity<>(supplyProcessDistService.getPartsDisByStoreAndStatus(storeId, statusId, pageable), HttpStatus.OK);

        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            return new ResponseEntity<>(Utils.internalServerError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
