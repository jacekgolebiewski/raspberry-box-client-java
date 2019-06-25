package pl.raspberry.box;

import org.slf4j.impl.SimpleLogger;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.app.examples.confitura.ConfituraApp;
import pl.raspberry.box.core.service.websocket.WebSocketService;

import java.util.function.Supplier;

public class Main {

    public static final String LOCALHOST = WebSocketService.LOCALHOST;
    public static final String RASPBERRY_BLUE = "ws://192.168.0.15:9001";
    public static final String RASPBERRY_RED = "ws://192.168.43.126:9001";
    public static final String RASPBERRY_YELLOW = "ws://192.168.10.14:9001";

    /**
     * Tu możesz zmienić uruchamianą aplikację
     */
    static Supplier<RaspberryBoxApplication> CURRENT_APPLICATION = ConfituraApp::new;

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
