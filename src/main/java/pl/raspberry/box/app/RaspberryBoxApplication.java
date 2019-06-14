package pl.raspberry.box.app;

import com.google.common.collect.ImmutableMap;
import pl.raspberry.box.client.model.request.led.LedColor;
import pl.raspberry.box.client.model.request.led.LedRequest;
import pl.raspberry.box.client.model.request.led.LedState;
import pl.raspberry.box.client.model.response.Response;
import pl.raspberry.box.client.model.response.button.Button;
import pl.raspberry.box.client.model.response.button.ButtonAction;
import pl.raspberry.box.client.model.response.button.ButtonResponse;
import pl.raspberry.box.client.model.response.message.MessageResponse;
import pl.raspberry.box.client.service.ConsoleInputService;
import pl.raspberry.box.client.service.WebSocketService;
import pl.raspberry.box.client.service.console.ConsoleInputProcessor;
import pl.raspberry.box.client.service.console.PropertyRequestConsoleInputProcessor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class RaspberryBoxApplication {

    private WebSocketService webSocketService;

    private Map<Class<? extends Response>, Consumer<Response>> responseHandlers =
            new ImmutableMap.Builder<Class<? extends Response>, Consumer<Response>>()
                    .put(ButtonResponse.class, (response) -> onButtonAction((ButtonResponse) response))
                    .put(MessageResponse.class, (response) -> System.out.println(">> " + ((MessageResponse)response).getMessage()))
                    .build();

    private <T extends Response> void handleResponse(T response) {
        Optional.ofNullable(responseHandlers.get(response.getClass()))
                .orElse((res) -> System.out.println("Not handled response: " + res))
                .accept(response);
    }

    public void start() {
        this.webSocketService = new WebSocketService(getBoxAddress())
                .onResponse(this::handleResponse)
                .connect();
        ConsoleInputService consoleInputService = new ConsoleInputService();
        consoleInputService.addInputProcessor(new PropertyRequestConsoleInputProcessor(this.webSocketService));
        consoleInputService.addInputProcessor(createApplicationInputProcessor());
        consoleInputService.readLines(this.webSocketService::disconnect);
        this.init();
    }

    public abstract void init();

    abstract public String getBoxAddress();

    abstract public void onConsoleInput(String line);

    public void onButtonAction(ButtonResponse response) {
        if (response.getAction().equals(ButtonAction.PRESSED)) {
            onButtonPressed(response.getButton());
        } else {
            onButtonReleased(response.getButton());
        }
    }

    abstract public void onButtonPressed(Button button);

    abstract public void onButtonReleased(Button button);

    public void switchLed(LedColor color, LedState state) {
        this.webSocketService.sendRequest(new LedRequest(color, state));
    }

    private ConsoleInputProcessor createApplicationInputProcessor() {
        return new ConsoleInputProcessor() {
            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isApplicable(String line) {
                return !line.startsWith("$");
            }

            @Override
            public void process(String line) {
                onConsoleInput(line);
            }
        };
    }

}
