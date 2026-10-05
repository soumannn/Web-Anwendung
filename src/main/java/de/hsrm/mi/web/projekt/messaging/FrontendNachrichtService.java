package de.hsrm.mi.web.projekt.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
/**
 * Dieser Service ermöglicht das Senden von FrontendNachrichtEvent-Objekten 
 * über einen STOMP-Messagebroke
 * Backend: Konfiguriert den STOMP-Endpoint /topic/tour über registry.enableSimpleBroker("/topic").
 * Frontend: Abonniert den STOMP-Endpoint /topic/tour, um Echtzeitnachrichten vom Server zu empfangen und darauf zu reagieren.
 */
@Service
public class FrontendNachrichtService {
    private static final Logger logger = LoggerFactory.getLogger(FrontendNachrichtService.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    //Der Service nutzt SimpMessagingTemplate, um die Nachrichten an den STOMP-Endpoint /topic/tour zu senden.
    public void sendEvent(FrontendNachrichtEvent ev) {
        messagingTemplate.convertAndSend("/topic/tour", ev);
        logger.info("Event sent: {} with ID: {} and Operation: {}", ev.getType(), ev.getId(), ev.getOperation());
    }
}
