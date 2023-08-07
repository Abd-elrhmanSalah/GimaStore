package com.gima.gimastore.controller;

import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.model.NotificationDTO;
import com.gima.gimastore.service.NotificationService;
import com.gima.gimastore.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private SimpMessagingTemplate messagingTemplate;
    private List<String> idList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    public NotificationController(NotificationService notificationService, SimpMessagingTemplate messagingTemplate) {
        this.notificationService = notificationService;
        this.messagingTemplate = messagingTemplate;
    }
    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }


    //    @MessageMapping("/message")
//    @SendTo("/topic/messages")
    public void getMessage(SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        System.out.println("we are here");
    }

    @MessageMapping("/private-notification")
    @SendToUser("topic/private-notification")
    public void getPrivateMessage() {
        logger.error("ana1");
//       ContextHolder.getContext().getAuthentication().getPrincipal();
//        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
//        System.out.println("we are here");
    }

    @MessageMapping("/message/{room}")
    @SendTo("/topic/message/{room}")
    public void handleMessage(@DestinationVariable String room) {
        // Access session attributes
        logger.error("ana2");
//        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
//        messagingTemplate.convertAndSendToUser(sessionAttributes.get("sessionId").toString(),
//                "/topic/private-notification", "Alla");
//        logger.error(sessionAttributes.get("sessionId").toString());

        System.out.println("i'am here");

    }


    @ResponseBody
    @GetMapping("/getNotificationsByUser")
    public void getNotificationsByUser(@RequestParam Long userId) {
        notificationService.notifyFrontend(userId);
        
        boolean b = idList.stream().anyMatch(id ->
        id.equalsIgnoreCase(userId.toString()));
if (!b)
    getIdList().add(userId.toString());

idList.forEach(id -> {

     System.err.println(id);
 });
    }

    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody NotificationDTO notificationDTO) {
        try {


            notificationService.addNotification(notificationDTO);
         
    idList.forEach(id -> {
       notificationService. notifyFrontend(Long.parseLong(id));
        System.err.println(id);
    });


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
