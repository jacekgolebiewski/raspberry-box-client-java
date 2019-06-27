package pl.raspberry.box;

import org.slf4j.impl.SimpleLogger;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.app.examples.flappybird.FlappyBird;
import pl.raspberry.box.core.service.websocket.WebSocketService;

import java.util.function.Supplier;

public class Main {

    public static final String LOCALHOST = WebSocketService.LOCALHOST;
    public static final String RASPBERRY_GREEN = "ws://192.168.43.3:9001";
    public static final String RASPBERRY_YELLOW = "ws://192.168.43.216:9001";
    public static final String RASPBERRY_BLUE = "ws://192.168.43.126:9001";

    /**
     * Tu możesz zmienić uruchamianą aplikację
     */
    static Supplier<RaspberryBoxApplication> CURRENT_APPLICATION = FlappyBird::new;

    public static void main(String[] args) {
        applyConfiguration();
        startApplication();
    }

    private static void applyConfiguration() {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
        WebSocketService.configure(RASPBERRY_BLUE);
    }

    private static void startApplication() {
        CURRENT_APPLICATION.get().start();
    }

}
