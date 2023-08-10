package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.service.ReportService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
public class ReportController {
    private ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/partReport")
    public ResponseEntity<?> getAllProductionRequest(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(reportService.getPartReport(params, pageable), HttpStatus.OK);

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

    @GetMapping("/settlementReport")
    public ResponseEntity<?> settlementReport(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(reportService.getStorePartSettlementReport(params, pageable), HttpStatus.OK);

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

    @GetMapping("/getSupplyProcessPartsReturns")
    public ResponseEntity<?> getSupplyProcessPartsReturns(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(reportService.getSupplyProcessPartsReturns(params, pageable), HttpStatus.OK);

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

    @GetMapping("/getProductsOut")
    public ResponseEntity<?> getProductsOut(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(reportService.getProductsOut(params, pageable), HttpStatus.OK);

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

    @GetMapping("/getProductBalance")
    public ResponseEntity<?> getProductsBalance(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(reportService.productBalance(params, pageable), HttpStatus.OK);

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

    @GetMapping("/getFullPartDetails")
    public ResponseEntity<?> getFullPartDetails(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(reportService.partFullDetails(params, pageable), HttpStatus.OK);

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

