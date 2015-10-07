package ca.carleton.blackjack.controller;

import ca.carleton.blackjack.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Web socket for simple echos.
 * <p/>
 * Created by Mike on 10/6/2015.
 */
@Component
public class EchoWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EchoWebSocketHandler.class);

    private static final int MAX_PLAYERS = 4;

    private Map<WebSocketSession, Player> players;

    private int count = 1;

    @Autowired
    private EchoService echoService;

    @PostConstruct
    public void init() {
        players = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        LOG.info("Opened new session for {}.", session.getId());
        players.putIfAbsent(session, new Player());
        LOG.info("Added player for session {}.", session.getId());

        // Tell others of a connection
        players.keySet().stream()
                .filter(val -> !session.getId().equals(val.getId()))
                .forEach(val -> {
                    try {
                        val.sendMessage(new TextMessage(String.format("Client %s has connected!", session.getId())));
                    } catch (final IOException exception) {
                        LOG.error("Unable to send message.", exception);
                    }
                });
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        LOG.info("Closed session for {} with status {}.", session, status);
    }

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message)
            throws Exception {
        final String echoMessage = this.echoService.getMessage(message.getPayload());
        LOG.info(echoMessage);
        session.sendMessage(new TextMessage(echoMessage + " I have been opened + " + this.count++ + " times."));
    }

    @Override
    public void handleTransportError(final WebSocketSession session, final Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }

}
