package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.service.SupplyProcessService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.gima.gimastore.constant.ResponseCodes.SUCCESS;

@RestController
@RequestMapping("/supplyProcess")
@CrossOrigin(origins = "*")
public class SupplyProcessController {
    private SupplyProcessService supplyProcessService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public SupplyProcessController(SupplyProcessService supplyProcessService) {
        this.supplyProcessService = supplyProcessService;
    }

    @PostMapping
    public ResponseEntity<?> addSupplier(@RequestPart String stringDto, @RequestPart("picture") MultipartFile file) {
        try {
            supplyProcessService.add(Utils.formattedJsonToSupplyProcessRequestObject(stringDto), file);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تم التوريد  " + SUCCESS.getMessage()), HttpStatus.OK);

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

//    @PatchMapping
//    public ResponseEntity<?> updateSupplier(@Valid @RequestBody SupplierDTO dto) {
//        try {
//            supplierService.update(dto);
//            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تمت تعديل المورد" + SUCCESS.getMessage()), HttpStatus.OK);
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
//    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplyProcess(@PathVariable Long id) {
        try {
            supplyProcessService.deleteSupplyProcessById(id);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تمت حذف الفاتورة" + SUCCESS.getMessage()), HttpStatus.OK);

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

    @GetMapping
    public ResponseEntity<?> getAllSupplyProcess() {
        try {

            return new ResponseEntity<>(supplyProcessService.findAllSupplyProcess(), HttpStatus.OK);

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplyProcessById(@PathVariable Long id) {
        try {

            return new ResponseEntity<>(supplyProcessService.findSupplyProcessById(id), HttpStatus.OK);

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
