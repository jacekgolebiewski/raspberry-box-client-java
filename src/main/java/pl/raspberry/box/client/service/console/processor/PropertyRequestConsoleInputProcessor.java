package pl.raspberry.box.client.service.console.processor;

import pl.raspberry.box.client.model.request.PropertyRequest;
import pl.raspberry.box.client.service.websocket.WebSocketService;

public class PropertyRequestConsoleInputProcessor implements ConsoleInputProcessor {

    private final WebSocketService webSocketService = WebSocketService.getInstance();

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public boolean isApplicable(String line) {
        return line.indexOf("$") == 0 && line.contains(":");
    }

    @Override
    public void process(String line) {
        PropertyRequest request = new PropertyRequest(line.split(":")[0].substring(1).trim(), line.split(":")[1].trim());
        webSocketService.sendRequest(request);
    }

}
