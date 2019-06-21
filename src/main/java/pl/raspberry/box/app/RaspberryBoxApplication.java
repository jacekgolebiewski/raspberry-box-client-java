package pl.raspberry.box.app;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.client.model.response.Response;
import pl.raspberry.box.client.model.response.button.Button;
import pl.raspberry.box.client.model.response.button.ButtonAction;
import pl.raspberry.box.client.model.response.button.ButtonResponse;
import pl.raspberry.box.client.model.response.message.MessageResponse;
import pl.raspberry.box.client.service.websocket.WebSocketService;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public abstract class RaspberryBoxApplication {

    private WebSocketService webSocketService;

    private Map<Class<? extends Response>, Consumer<Response>> responseHandlers =
            new ImmutableMap.Builder<Class<? extends Response>, Consumer<Response>>()
                    .put(ButtonResponse.class, (response) -> onButtonAction((ButtonResponse) response))
                    .put(MessageResponse.class, (response) -> log.info(">> " + ((MessageResponse)response).getMessage()))
                    .build();

    private <T extends Response> void handleResponse(T response) {
        Optional.ofNullable(responseHandlers.get(response.getClass()))
                .orElse((res) -> log.warn("Not handled response: " + res))
                .accept(response);
    }

    public void start() {
        this.webSocketService = WebSocketService.getInstance()
                .onResponse(this::handleResponse)
                .connect();
        this.onApplicationStarted();
    }

    public void stop() {
        this.webSocketService.disconnect();
        this.onApplicationStopped();
    }

    public abstract void onApplicationStarted();

    public abstract void onApplicationStopped();

    private void onButtonAction(ButtonResponse response) {
        try{
            if (response.getAction().equals(ButtonAction.PRESSED)) {
                onButtonPressed(response.getButton());
            } else {
                onButtonReleased(response.getButton());
            }
        } catch (Exception e) {
            log.error("onButtonPressed/onButtonReleased thrown exception:");
            throw new RuntimeException(e);
        }

    }

    abstract public void onButtonPressed(Button button);

    abstract public void onButtonReleased(Button button);

}
