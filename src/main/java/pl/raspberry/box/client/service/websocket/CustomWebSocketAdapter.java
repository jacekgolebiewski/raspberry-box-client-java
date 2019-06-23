package pl.raspberry.box.client.service.websocket;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Data
@Builder
public class CustomWebSocketAdapter extends WebSocketAdapter {

    private Runnable onConnect;
    private Runnable onDisconnect;
    private Consumer<String> onResponse;

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        Optional.ofNullable(onConnect).ifPresent(Runnable::run);
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
        Optional.ofNullable(onDisconnect).ifPresent(Runnable::run);
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        super.onTextMessage(websocket, text);
        Optional.ofNullable(onResponse).ifPresent(consumer -> consumer.accept(text));
    }

}
