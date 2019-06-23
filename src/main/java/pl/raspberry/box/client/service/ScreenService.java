package pl.raspberry.box.client.service;

import pl.raspberry.box.client.model.request.screen.Matrix;
import pl.raspberry.box.client.model.request.screen.ScreenRequest;
import pl.raspberry.box.client.service.websocket.WebSocketService;

public class ScreenService {

    private WebSocketService webSocketService = WebSocketService.getInstance();

    public void setScreenFrame(Matrix matrix) {
        this.webSocketService.sendRequest(new ScreenRequest(matrix.getArray()));
    }

    public void clean() {
        this.setScreenFrame(new Matrix());
    }

}
