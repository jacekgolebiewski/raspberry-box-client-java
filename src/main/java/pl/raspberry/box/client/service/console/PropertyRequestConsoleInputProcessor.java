package pl.raspberry.box.client.service.console;

import lombok.RequiredArgsConstructor;
import pl.raspberry.box.client.model.request.PropertyRequest;
import pl.raspberry.box.client.service.WebSocketService;

@RequiredArgsConstructor
public class PropertyRequestConsoleInputProcessor implements ConsoleInputProcessor {

    private final WebSocketService webSocketService;

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
