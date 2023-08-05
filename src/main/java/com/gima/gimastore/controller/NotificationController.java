package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.model.NotificationDTO;
import com.gima.gimastore.service.NotificationService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "*")
public class NotificationController {
    private NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public void getMessage() {

    }

    @ResponseBody
    @GetMapping("/getNotificationsByUser")
    public void getNotificationsByUser(@RequestParam Long userId) {
        notificationService.notifyFrontend(userId);
    }

    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody NotificationDTO notificationDTO) {
        try {

            notificationService.addNotification(notificationDTO);

            return new ResponseEntity<>("done", HttpStatus.OK);

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
