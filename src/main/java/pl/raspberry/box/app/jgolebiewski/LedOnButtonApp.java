package pl.raspberry.box.app.jgolebiewski;

import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.client.model.request.led.LedColor;
import pl.raspberry.box.client.model.request.led.LedState;
import pl.raspberry.box.client.model.request.screen.MatrixBuilder;
import pl.raspberry.box.client.model.request.screen.Row;
import pl.raspberry.box.client.model.response.button.Button;

import java.util.stream.IntStream;

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
        } else if (line.contains("fill")) {
            String[] splitedLine = line.split("fill");
            int size = Integer.parseInt(splitedLine[splitedLine.length-1].trim());
            if(size < 0 || size > 8) {
                System.out.println("Fill level must be from range 0-8");
                return;
            }

            MatrixBuilder builder = new MatrixBuilder();
            IntStream.range(0, size).forEach(index -> builder.addRow(Row.createFullRow(7-index)));
            setScreenFrame(builder.build());
        } else {
            System.out.println("Command not supported");
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
