package com.gima.gimastore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;
@Slf4j
public class SessionInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Your session management logic here
        // Store session attributes in a user session map or Spring Session
        if (request instanceof ServletServerHttpRequest) {
       //     ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
       //     HttpSession session = servletRequest.getServletRequest().getSession();
        //    attributes.put("sessionId", session.getId());
       //     log.error("==========="+session.getId());
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        // Clean up or perform additional operations after handshake

        super.afterHandshake(request, response, wsHandler, ex);
    }
}