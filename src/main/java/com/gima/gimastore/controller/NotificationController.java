package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.model.NotificationDTO;
import com.gima.gimastore.service.NotificationService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notification")
@CrossOrigin(origins = "*")
public class NotificationController {
    private NotificationService notificationService;

    private List<String> idList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    @MessageMapping("/message/{room}")
    @SendTo("/topic/message/{room}")
    public void handleMessage(@DestinationVariable String room) {
    }

    @ResponseBody
    @GetMapping("/notifyByUser")
    public void getNotificationsByUser(@RequestParam Long userId, Pageable pageable) {
        notificationService.notifyFrontend(userId, pageable);

        boolean userIdExist = idList.stream().anyMatch(id ->
                id.equalsIgnoreCase(userId.toString()));
        if (!userIdExist)
            getIdList().add(userId.toString());
    }

    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody NotificationDTO notificationDTO, Pageable p) {
        try {

            notificationService.addNotification(notificationDTO);
            refreshTunles(p);
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

    @GetMapping("/getAllNotifications")
    public ResponseEntity<?> getAllnotifications(@RequestParam Long userId, Pageable p) {
        try {

            return new ResponseEntity<>(notificationService.getAllNotification(userId, p), HttpStatus.OK);

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
//    @GetMapping("/testList")
//    public ResponseEntity<?> testList(@RequestParam Long userId, Pageable p) {
//        try {
//
//            return new ResponseEntity<>(notificationService.testList(userId, p), HttpStatus.OK);
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

    @PatchMapping("/updateNotification")
    public ResponseEntity<?> updateNotification(@RequestParam Long notificationId, @RequestParam Long userId, Pageable pageable) {
        try {

            notificationService.updateNotificationToRead(notificationId, userId);

            refreshTunles(pageable);
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

    public void refreshTunles(Pageable p) {
        idList.forEach(id -> notificationService.notifyFrontend(Long.parseLong(id), p));
    }
}
