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

import java.util.Map;

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

    @GetMapping("/getDistRequests")
    public ResponseEntity<?> getSupplyProcessDistByStoreAndStatus(@RequestParam Map<String, String> params, Pageable pageable) {
        try {
            return new ResponseEntity<>(supplyProcessDistService.getPartsDisByStoreAndStatus(params, pageable), HttpStatus.OK);

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

    @PostMapping("/acceptDistRequest")
    public ResponseEntity<?> acceptRequest(@RequestParam Long requestId, @RequestParam Long userId, @RequestParam String notes) {
        try {
            supplyProcessDistService.acceptRequest(requestId, userId, notes);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تم قبول  " + SUCCESS.getMessage()), HttpStatus.OK);

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

    @PostMapping("/rejectDistRequest")
    public ResponseEntity<?> rejectRequest(@RequestParam Long requestId, @RequestParam Long userId, @RequestParam String notes) {
        try {
            supplyProcessDistService.rejectRequest(requestId, userId, notes);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تم الرفض  " + SUCCESS.getMessage()), HttpStatus.OK);

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
