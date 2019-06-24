package pl.raspberry.box.app.examples.distance;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.core.model.response.button.Button;
import pl.raspberry.box.core.service.console.ConsoleInputService;

/**
 * Przykładowa aplikacja wyświetlająca pomiary czujnika odległości
 */
@Slf4j
public class DistanceTestApp extends RaspberryBoxApplication {

    private final ConsoleInputService consoleInputService = new ConsoleInputService();

    @Override
    public void onApplicationStarted() {
        log.info("Started...");
        final RaspberryBoxApplication application = this;
        new Thread(() -> {
            System.out.println("Type anything to finish:");
            consoleInputService.readLine();
            application.stop();
        }, "onApplicationStartedThread").start();
    }

    @Override
    protected void onLoopCycle(int index) {
    }

    @Override
    public void onApplicationStopped() {
    }

    @Override
    protected void onNewDistanceRead(Double distance) {
        System.out.println(distance);
    }

    @Override
    public void onButtonPressed(Button button) {
    }

    @Override
    public void onButtonReleased(Button button) {

    }

}
