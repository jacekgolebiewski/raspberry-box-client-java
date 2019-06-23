package pl.raspberry.box.app.waveapp;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.client.model.response.button.Button;
import pl.raspberry.box.client.service.console.ConsoleInputService;

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
        System.out.println("STOPPED");
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
