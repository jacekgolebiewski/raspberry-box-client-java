package pl.raspberry.box.app.jgolebiewski;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.client.model.request.screen.MatrixLine;
import pl.raspberry.box.client.model.request.screen.Row;
import pl.raspberry.box.client.model.request.screen.builder.MatrixBuilder;
import pl.raspberry.box.client.model.response.button.Button;
import pl.raspberry.box.client.service.ScreenService;
import pl.raspberry.box.client.service.console.ConsoleInputService;

import java.util.stream.IntStream;

@Slf4j
public class LedOnButtonApp extends RaspberryBoxApplication {

    private final ScreenService screenService = new ScreenService();
    private final ConsoleInputService consoleInputService = new ConsoleInputService();

    private Integer score = 0;

    @Override
    public void onApplicationStarted() {
        log.debug("Game started!");
        while(true) {
            String line = consoleInputService.readLine();
            if (line.equals("exit")) {
                break;
            }
            onConsoleInput(line);
        }
    }

    @Override
    public void onApplicationStopped() {
        log.debug("Score: " + score);
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
            String[] splitedLine = line.split("fill");
            int level = Integer.parseInt(splitedLine[splitedLine.length - 1].trim());
            fillScreen(level);
        } else if (line.contains("animation")) {
            for (int i = 0; i < 3; i++) {
                MatrixLine.forEachIndex(level -> {
                    fillScreen(level);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else {
            log.debug("Command not supported");
        }
    }

    private void fillScreen(int level) {
        if (level < 0 || level > MatrixLine.SIZE - 1) {
            log.warn("Fill level must be from range 0-8");
            return;
        }

        MatrixBuilder builder = new MatrixBuilder();
        IntStream.range(0, level+1).forEach(index -> builder.addRow(Row.createFullRow(7 - index)));
        screenService.setScreenFrame(builder.build());
    }

}
