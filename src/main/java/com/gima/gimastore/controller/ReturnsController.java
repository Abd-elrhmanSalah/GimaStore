package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.model.ReturnsProcess.HarmedPartRequest;
import com.gima.gimastore.model.ReturnsProcess.ReturnPartRequest;
import com.gima.gimastore.service.ReturnsService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/returns")
@CrossOrigin(origins = "*")
public class ReturnsController {
    private ReturnsService returnPartService;

    private static final Logger logger = LoggerFactory.getLogger(ReturnsController.class);

    public ReturnsController(ReturnsService returnPartService) {
        this.returnPartService = returnPartService;
    }

    @PostMapping("/addReturnedParts")
    public ResponseEntity<?> getProductParts(@RequestBody List<ReturnPartRequest> request) {
        try {
            returnPartService.addPartToReturn(request);
            return new ResponseEntity<>("تم تسجيل الإرجاعات ", HttpStatus.OK);

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

    @PostMapping("/addHarmedParts")
    public ResponseEntity<?> addHarmedParts(@RequestBody List<HarmedPartRequest> request) {
        try {
            returnPartService.addHarmedPart(request);
            return new ResponseEntity<>("تم تسجيل الهوالك ", HttpStatus.OK);

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

    @GetMapping("/getHarmedParts")
    public ResponseEntity<?> getHarmedParts(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(returnPartService.getDetailedHarmedParts(params, pageable), HttpStatus.OK);

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

    @GetMapping("/getReturnedParts")
    public ResponseEntity<?> getReturnedParts(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(returnPartService.getDetailedReturnedPart(params, pageable), HttpStatus.OK);

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

    @GetMapping("/getTotalReturnedParts")
    public ResponseEntity<?> getTotalReturnedParts(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(returnPartService.getTotalReturnedPart(params, pageable), HttpStatus.OK);

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
    @GetMapping("/getTotalHarmedParts")
    public ResponseEntity<?> getTotalHarmedParts(@RequestParam Map<String, String> params, Pageable pageable) {
        try {

            return new ResponseEntity<>(returnPartService.getTotalHarmedParts(params, pageable), HttpStatus.OK);

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
