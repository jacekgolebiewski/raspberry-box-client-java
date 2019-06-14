package pl.raspberry.box.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketState;
import pl.raspberry.box.client.model.request.Request;
import pl.raspberry.box.client.model.response.Response;
import pl.raspberry.box.client.util.ObjectMapperUtil;

import java.io.IOException;
import java.util.function.Consumer;

public class WebSocketService {

    private static final int TIMEOUT = 5000;
    private WebSocket webSocket;

    public WebSocketService(String host) {
        try {
            this.webSocket = new WebSocketFactory()
                    .setConnectionTimeout(TIMEOUT)
                    .createSocket(host)
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public WebSocketService connect() {
        while (true) {
            try {
                this.webSocket.connect();
                return this;
            } catch (WebSocketException e) {
                System.out.println(e.getMessage());
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
        this.webSocket.addListener(new WebSocketAdapter() {
            public void onTextMessage(WebSocket websocket, String message) {
                onResponse.accept(convertTextToResponse(message));
            }
        });
        return this;
    }

    public void sendRequest(Request request) {
        if (this.webSocket.getState() == WebSocketState.CLOSED) {
            System.out.println("Disconnected...");
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
