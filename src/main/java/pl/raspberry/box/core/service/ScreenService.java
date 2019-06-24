package pl.raspberry.box.core.service;

import pl.raspberry.box.core.model.request.screen.Matrix;
import pl.raspberry.box.core.model.request.screen.ScreenRequest;
import pl.raspberry.box.core.service.websocket.WebSocketService;

public class ScreenService {

    private WebSocketService webSocketService = WebSocketService.getInstance();

    public void setScreenFrame(Matrix matrix) {
        this.webSocketService.sendRequest(new ScreenRequest(matrix.clone().flipVertically().getArray()));
    }

    public void clean() {
        this.setScreenFrame(new Matrix());
    }

}
