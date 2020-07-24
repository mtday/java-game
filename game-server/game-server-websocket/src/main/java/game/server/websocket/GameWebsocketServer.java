package game.server.websocket;

import game.server.config.ConfigLoader;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.websockets.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class GameWebsocketServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameWebsocketServer.class);

    private static class ReceiveListener extends AbstractReceiveListener {
        @Override
        protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
            LOGGER.info("Received message from peer {} => {}", channel.getPeerAddress(), message.getData());
            WebSockets.sendText("ACK", channel, null);
        }
    }

    public GameWebsocketServer() {
        Properties properties = ConfigLoader.load();

        HttpHandler websocketHandler = Handlers.websocket((exchange, channel) -> {
            channel.getReceiveSetter().set(new ReceiveListener());
            channel.resumeReceives();
        });
        HttpHandler httpHandler = Handlers.path().addPrefixPath("/game", websocketHandler);
        Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(httpHandler)
                .build()
                .start();
    }

    public static void main(String... args) {
        new GameWebsocketServer();
    }
}
