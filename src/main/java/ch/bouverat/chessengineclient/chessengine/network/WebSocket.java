package ch.bouverat.chessengineclient.chessengine.network;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocket extends WebSocketClient {
    public WebSocket (URI endpointUrl) {
        super(endpointUrl);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("connected");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Server : " + message);
    }

    @Override
    public void onError(Exception e) {
        System.out.println(e);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed");
        System.out.println(reason);
    }
}
