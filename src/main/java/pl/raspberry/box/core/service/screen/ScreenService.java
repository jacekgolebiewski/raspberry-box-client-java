package pl.raspberry.box.core.service.screen;

import pl.raspberry.box.core.model.Matrix;
import pl.raspberry.box.core.model.request.ScreenRequest;
import pl.raspberry.box.core.service.websocket.WebSocketService;
import pl.raspberry.box.core.util.GameUtil;

import java.util.stream.IntStream;

public class ScreenService {

    private WebSocketService webSocketService = WebSocketService.getInstance();
    private CharToMatrixService charToMatrixService = new CharToMatrixService();

    public void setScreenFrame(Matrix matrix) {
        this.webSocketService.sendRequest(new ScreenRequest(matrix.clone().flipVertically().getArray()));
    }

    public void setText(String text) {
        setText(text, 500);
    }

    public void setText(String text, long delay) {
        if (text != null) {
            for(int i=0; i< text.length(); i++) {
                setScreenFrame(charToMatrixService.getMatrix(text.charAt(i)));
                GameUtil.sleep(delay);
            }
        }
    }

    public void clean() {
        this.setScreenFrame(new Matrix());
    }

    public void successScreen() {
        Matrix empty = new Matrix();
        Matrix happyFace = new Matrix()
                .fillCell(1, 2, 1)
                .fillCell(2, 2, 1)
                .fillCell(4, 1, 1)
                .fillCell(5, 2, 1)
                .fillCell(5, 3, 1);
        happyFace.join(happyFace.clone().flipHorizontally());
        IntStream.range(0, 5).forEach(idx -> {
            GameUtil.sleep(200);
            setScreenFrame(happyFace);
            GameUtil.sleep(200);
            setScreenFrame(empty);
        });
    }

    public void failScreen() {
        Matrix empty = new Matrix();
        Matrix sadFace = new Matrix()
                .fillCell(1, 2, 1)
                .fillCell(2, 2, 1)
                .fillCell(6, 1, 1)
                .fillCell(5, 2, 1)
                .fillCell(5, 3, 1);
        sadFace.join(sadFace.clone().flipHorizontally());
        IntStream.range(0, 5).forEach(idx -> {
            GameUtil.sleep(200);
            setScreenFrame(sadFace);
            GameUtil.sleep(200);
            setScreenFrame(empty);
        });
    }

}
