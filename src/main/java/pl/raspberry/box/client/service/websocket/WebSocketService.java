package pl.raspberry.box.client.service.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketState;
import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.client.model.request.Request;
import pl.raspberry.box.client.model.response.Response;
import pl.raspberry.box.client.util.ObjectMapperUtil;

import java.io.IOException;
import java.util.function.Consumer;

@Slf4j
public class WebSocketService {

    public static final String LOCALHOST = "ws://localhost:9001";

    private static final int TIMEOUT = 5000;

    private static String host = LOCALHOST;
    private WebSocket webSocket;
    private Consumer<Response> onResponseHandler;

    public static void configure(String host) {
        WebSocketService.host = host;
    }

    private static WebSocketService instance;

    public static WebSocketService getInstance() {
        if (instance == null) {
            instance = new WebSocketService(host);
        }
        return instance;
    }

    private WebSocketService(String host) {
        init(host);
    }

    private void init(String host) {
        try {
            this.webSocket = new WebSocketFactory()
                    .setConnectionTimeout(TIMEOUT)
                    .createSocket(host)
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);
            this.webSocket.addListener(CustomWebSocketAdapter.builder()
                    .onDisconnect(this::connect)
                    .onResponse(text -> onResponseHandler.accept(convertTextToResponse(text)))
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public WebSocketService connect() {
        while (true) {
            if(this.webSocket.getState() == WebSocketState.CREATED) {
                try {
                    this.webSocket.connect();
                    return this;
                } catch (WebSocketException e) {
                    log.error(e.getMessage());
                }
            }
            try {
                Thread.sleep(1000);
                this.webSocket = this.webSocket.recreate();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void disconnect() {
        this.webSocket.disconnect();
    }

    public WebSocketService onResponse(Consumer<Response> onResponse) {
        this.onResponseHandler = onResponse;
        return this;
    }

    public void sendRequest(Request request) {
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
