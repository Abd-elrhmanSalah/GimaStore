package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.service.PartService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.gima.gimastore.constant.ResponseCodes.SUCCESS;

@RestController
@RequestMapping("/part")
@CrossOrigin(origins = "*")
public class PartController {

    private PartService partService;

    private static final Logger logger = LoggerFactory.getLogger(PartController.class);

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @PostMapping
    public ResponseEntity<?> addPart(@RequestPart String stringDto, @RequestPart("picture") MultipartFile file) {
        try {

            partService.add(Utils.formattedJsonToPartDTOObject(stringDto), file);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تمت إضافة الجزء" + SUCCESS.getMessage()), HttpStatus.OK);

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

    @PatchMapping
    public ResponseEntity<?> updatePart(@RequestPart String stringDto, @RequestPart("picture") MultipartFile file) {
        try {


            partService.update(Utils.formattedJsonToPartDTOObject(stringDto), file);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تمت تعديل الجزء" + SUCCESS.getMessage()), HttpStatus.OK);

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePart(@PathVariable Long id) {
        try {
            partService.delete(id);
            return new ResponseEntity<>(new StatusResponse(SUCCESS.getCode(), SUCCESS.getKey(), "تمت حذف الجزء" + SUCCESS.getMessage()), HttpStatus.OK);

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
    public ResponseEntity<?> getAllParts() {
        try {

            return new ResponseEntity<>(partService.findAll(), HttpStatus.OK);

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
    public ResponseEntity<?> getPartById(@PathVariable Long id) {
        try {

            return new ResponseEntity<>(partService.findById(id), HttpStatus.OK);

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
