package pl.raspberry.box.app.jgolebiewski;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.client.model.request.screen.Matrix;
import pl.raspberry.box.client.model.response.button.Button;
import pl.raspberry.box.client.service.ScreenService;
import pl.raspberry.box.client.service.console.ConsoleInputService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class LedOnButtonApp extends RaspberryBoxApplication {

    private final ScreenService screenService = new ScreenService();
    private final ConsoleInputService consoleInputService = new ConsoleInputService();

    private Integer score = 0;

    @Override
    public void onApplicationStarted() {
        log.debug("Game started!");
    }

    @Override
    protected void onLoopCycle(int index) {
        String line = consoleInputService.readLine();
        if (line.equals("exit")) {
            stop();
        }
        onConsoleInput(line);
    }

    @Override
    public void onApplicationStopped() {
        log.debug("Score: " + score);
    }

    @Override
    protected void onNewDistanceRead(Double distance) {

    }

    @Override
    public void onButtonPressed(Button button) {
        if (button == Button.A) {
            log.debug("A PRESSED!");
        } else {
            log.debug("B PRESSED!");
        }
    }

    @Override
    public void onButtonReleased(Button button) {

    }

    private void onConsoleInput(String line) {
        if (line.contains("fill")) {
            fill(line);
        } else if (line.contains("animation")) {
            animation();
        } else if (line.contains("clean")) {
            screenService.setScreenFrame(new Matrix());
        } else {
            log.debug("Command not supported");
        }
    }

    private void fill(String line) {
        String[] splitedLine = line.split("fill");
        int level = Integer.parseInt(splitedLine[splitedLine.length - 1].trim());
        fillScreen(level);
    }

    private void animation() {
        for (int i = 0; i < 3; i++) {
            Matrix.forEachIndex(level -> {
                fillScreen(level);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        screenService.setScreenFrame(new Matrix());
    }

    private void fillScreen(int level) {
        List<Integer> rows = IntStream.range(0, level+1).boxed().collect(Collectors.toList());
        Matrix matrix = new Matrix().fillRows(rows);//.flipVertically();
        screenService.setScreenFrame(matrix);
    }

}
