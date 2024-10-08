package com.tejko.yamb.business.interfaces;

import java.security.Principal;

import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.tejko.yamb.domain.models.WebSocketMessage;

public interface WebSocketService {

    public void publicMessage(WebSocketMessage messageRequest, Principal principal);

    public void privateMessage(WebSocketMessage messageRequest, Principal principal);

    public void handleSessionConnected(SessionConnectEvent event);

    public void handleSessionDisconnect(SessionDisconnectEvent event);

    public void handleSessionSubscribeEvent(SessionSubscribeEvent event);
    
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event);
    
}
