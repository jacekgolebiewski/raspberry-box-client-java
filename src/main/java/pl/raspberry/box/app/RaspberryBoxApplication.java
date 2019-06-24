package pl.raspberry.box.app;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.core.model.response.Response;
import pl.raspberry.box.core.model.response.button.Button;
import pl.raspberry.box.core.model.response.button.ButtonAction;
import pl.raspberry.box.core.model.response.button.ButtonResponse;
import pl.raspberry.box.core.model.response.distance.DistanceResponse;
import pl.raspberry.box.core.model.response.message.MessageResponse;
import pl.raspberry.box.core.service.websocket.WebSocketService;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Main application abstract class. If you want to create application extend it.
 */
@Slf4j
public abstract class RaspberryBoxApplication {

    private WebSocketService webSocketService;
    private Thread applicationThread;

    @Getter
    private boolean finished = false;

    private Map<Class<? extends Response>, Consumer<Response>> responseHandlers =
            new ImmutableMap.Builder<Class<? extends Response>, Consumer<Response>>()
                    .put(ButtonResponse.class, (response) -> onButtonAction((ButtonResponse) response))
                    .put(MessageResponse.class, (response) -> log.info(">> " + ((MessageResponse)response).getMessage()))
                    .put(DistanceResponse.class, (response) -> onDistanceResponse((DistanceResponse) response))
                    .build();

    private <T extends Response> void handleResponse(T response) {
        Optional.ofNullable(responseHandlers.get(response.getClass()))
                .orElse((res) -> log.warn("Not handled response: " + res))
                .accept(response);
    }

    public void start() {
        applicationThread = new Thread(new ApplicationRunnable(this), "ApplicationThread");
        this.webSocketService = WebSocketService.getInstance()
                .onConnect(applicationThread::start)
                .onDisconnect(() -> {})
                .onResponse(this::handleResponse)
                .connect();
    }

    public void stop() {
        this.finished = true;
        this.webSocketService.disconnect();
    }

    public abstract void onApplicationStarted();

    /**
     * Application loop cycle, after each cycle there is 100ms wait time
     */
    protected abstract void onLoopCycle(int index);

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

    private void onDistanceResponse(DistanceResponse response) {
        log.trace("onDistanceResponse: "+response.getDistance());
        Double distance = response.getDistance();
        if (distance < 5 || distance > 100) {
            return;
        }
        onNewDistanceRead(response.getDistance());
    }

    /**
     * Displays changes in distance within range 5-100cm
     */
    protected abstract void onNewDistanceRead(Double distance);

    abstract public void onButtonPressed(Button button);

    abstract public void onButtonReleased(Button button);

}

