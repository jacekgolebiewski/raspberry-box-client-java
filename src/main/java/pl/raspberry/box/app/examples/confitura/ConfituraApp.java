package pl.raspberry.box.app.examples.confitura;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.core.model.response.button.Button;
import pl.raspberry.box.core.service.ScreenService;
import pl.raspberry.box.core.service.console.ConsoleInputService;

/**
 * Szablon aplikacji confitura2019
 *
 * 1. W pakiecie examples dostępne są przykładowe aplikacje
 * 2. Aby uruchomić aplikację uruchom Main.java
 * 3. Rozpocznij od uruchomienia aplikacji i implementacji poniższych metod
 */
@Slf4j
public class ConfituraApp extends RaspberryBoxApplication {

    private final ScreenService screenService = new ScreenService();
    private final ConsoleInputService consoleInputService = new ConsoleInputService();

    @Override
    public void onApplicationStarted() {
        log.info("Hello world");
    }

    @Override
    protected void onLoopCycle(int index) {
    }

    @Override
    public void onApplicationStopped() {
    }

    @Override
    protected void onNewDistanceRead(Double distance) {

    }

    @Override
    public void onButtonPressed(Button button) {
    }

    @Override
    public void onButtonReleased(Button button) {

    }

}
