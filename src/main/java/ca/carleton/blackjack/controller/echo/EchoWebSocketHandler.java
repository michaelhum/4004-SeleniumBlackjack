package ca.carleton.blackjack.controller.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Web socket for simple echos.
 * <p/>
 * Created by Mike on 10/6/2015.
 */
@Component
public class EchoWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EchoWebSocketHandler.class);

    @Autowired
    private EchoService echoService;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        LOG.info("Opened new session for {}.", session.getId());
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
    }

    @Override
    public void handleTransportError(final WebSocketSession session, final Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
    }

}