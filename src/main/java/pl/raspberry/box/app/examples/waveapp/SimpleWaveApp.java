package pl.raspberry.box.app.examples.waveapp;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.core.model.Matrix;
import pl.raspberry.box.core.model.response.button.Button;
import pl.raspberry.box.core.service.console.ConsoleInputService;
import pl.raspberry.box.core.service.screen.ScreenService;

import java.util.Collections;
import java.util.List;

/**
 * Przykładowa aplikacja wyświetlająca na matrycy led falę, którą można modyfikować przyciskami A i B
 */
@Slf4j
public class SimpleWaveApp extends RaspberryBoxApplication {

    private final int MAX_LEVEL = 8;

    private final ScreenService screenService = new ScreenService();
    private final ConsoleInputService consoleInputService = new ConsoleInputService();

    private Matrix screen = new Matrix();
    private int level = 1;

    @Override
    public void onApplicationStarted() {
        final RaspberryBoxApplication application = this;
        new Thread(() -> {
            System.out.println("Type anything to finish:");
            consoleInputService.readLine();
            application.stop();
        }, "onApplicationStartedThread").start();
    }

    @Override
    protected void onLoopCycle(int index) {
        if (index % 5 == 0) {
            screen.shiftLeft().setLastColumn(getNLevelColumn(level));
            log.trace(screen.toString());
            screenService.setScreenFrame(screen);
        }
    }

    private List<Integer> getNLevelColumn(int n) {
        return Collections.nCopies(n, 1);
    }

    @Override
    public void onApplicationStopped() {
        System.out.println("STOPPED");
    }

    @Override
    protected void onNewDistanceRead(Double distance) {

    }

    @Override
    public void onButtonPressed(Button button) {
        if (button == Button.A) {
            log.debug("Button A pressed");
            level++;
        } else {
            log.debug("Button B pressed");
            level--;
        }
        if (level < 0) {
            level = 0;
        }
        if (level > MAX_LEVEL) {
            level = MAX_LEVEL;
        }
    }

    @Override
    public void onButtonReleased(Button button) {

    }

}
