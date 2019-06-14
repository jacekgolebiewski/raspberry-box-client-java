package pl.raspberry.box.app.jgolebiewski;

import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.client.model.request.led.LedColor;
import pl.raspberry.box.client.model.request.led.LedState;
import pl.raspberry.box.client.model.response.button.Button;

public class LedOnButtonApp extends RaspberryBoxApplication {

    private static final String HOST = "ws://localhost:9001";

    @Override
    public void init() {
        System.out.println("Game started!");
    }

    @Override
    public String getBoxAddress() {
        return HOST;
    }

    @Override
    public void onConsoleInput(String line) {
        if (line.contains("red")) {
            switchLed(LedColor.RED, LedState.ON);
        }
    }

    @Override
    public void onButtonPressed(Button button) {
        if (button == Button.A) {
            System.out.println("START!");
            switchLed(LedColor.GREEN, LedState.ON);
        } else {
            System.out.println("STOP!");
            switchLed(LedColor.GREEN, LedState.OFF);
        }
    }

    @Override
    public void onButtonReleased(Button button) {

    }

}
