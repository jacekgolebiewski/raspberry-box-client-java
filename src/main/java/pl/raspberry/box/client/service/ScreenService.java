package pl.raspberry.box.client.service;

import pl.raspberry.box.client.model.request.screen.ScreenRequest;
import pl.raspberry.box.client.service.websocket.WebSocketService;

import java.util.List;

public class ScreenService {

    private WebSocketService webSocketService = WebSocketService.getInstance();

    public void setScreenFrame(List<List<Integer>> matrix) {
        this.webSocketService.sendRequest(new ScreenRequest(matrix));
    }

}
