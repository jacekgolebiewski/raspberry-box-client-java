package pl.raspberry.box.core.service.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketState;
import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.core.model.request.Request;
import pl.raspberry.box.core.model.response.Response;
import pl.raspberry.box.core.util.ObjectMapperUtil;

import java.io.IOException;
import java.util.function.Consumer;

@Slf4j
public class WebSocketService {

    public static final String LOCALHOST = "ws://localhost:9001";

    private static final int TIMEOUT = 5000;

    private static String host = LOCALHOST;
    private WebSocket webSocket;
    private Consumer<Response> onResponseHandler = (response) -> {};
    private Runnable onConnectHandler = () -> {};
    private Runnable onDisconnectHandler = () -> {};

    public static void configure(String host) {
        WebSocketService.host = host;
    }

    private static WebSocketService instance;

    public static WebSocketService getInstance() {
        if (instance == null) {
            instance = new WebSocketService();
        }
        return instance;
    }

    private WebSocketService() {
    }

    public WebSocketService connect() {
        init();
        try {
            this.webSocket.connect();
            return this;
        } catch (WebSocketException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        try {
            this.webSocket = new WebSocketFactory()
                    .setConnectionTimeout(TIMEOUT)
                    .createSocket(host)
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);
            this.webSocket.addListener(CustomWebSocketAdapter.builder()
                    .onConnect(onConnectHandler)
                    .onDisconnect(onDisconnectHandler)
                    .onResponse(text -> onResponseHandler.accept(convertTextToResponse(text)))
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        this.webSocket.disconnect();
    }

    public WebSocketService onResponse(Consumer<Response> onResponse) {
        this.onResponseHandler = onResponse;
        return this;
    }

    public WebSocketService onConnect(Runnable onConnect) {
        this.onConnectHandler = onConnect;
        return this;
    }

    public WebSocketService onDisconnect(Runnable onDisconnect) {
        this.onDisconnectHandler = onDisconnect;
        return this;
    }

    public void sendRequest(Request request) {
        if (this.webSocket.getState() != WebSocketState.OPEN) {
            this.connect();
        }
        try {
            String requestText = ObjectMapperUtil.getObjectMapper().writeValueAsString(request);
            this.webSocket.sendText(requestText);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Response convertTextToResponse(String text) {
        try {
            return ObjectMapperUtil.getObjectMapper().readValue(text, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
