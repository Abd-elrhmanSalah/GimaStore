package com.gima.gimastore.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class SubscribeEventListener  {

//public class SubscribeEventListener implements ApplicationListener {

//    @Override
//    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
//        System.out.println(headerAccessor.getSessionAttributes().get("sessionId").toString());
//    }
//
//
//    @Override
//    public void onApplicationEvent(ApplicationEvent event) {
//        if( event instanceof SessionSubscribeEvent){
//            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
//            System.out.println(headerAccessor.getSessionAttributes().get("sessionId").toString());
//
//        }
//    }
}