package pl.raspberry.box;

import org.slf4j.impl.SimpleLogger;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.app.examples.findpath.solution.CheaterPathFinderApp;
import pl.raspberry.box.core.service.websocket.WebSocketService;

public class Main {

    public static final String LOCALHOST = WebSocketService.LOCALHOST;
    public static final String RASPBERRY_HOST = "ws://192.168.0.15:9001";
    public static final String RASPBERRY_HOST2 = "ws://192.168.43.126:9001";
    public static final String RASPBERRY_HOST3 = "ws://192.168.10.14:9001";

    static RaspberryBoxApplication currentApplication;

    public static void main(String[] args) {
        applyConfiguration();
        startApplication();
    }

    private static void applyConfiguration() {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
        WebSocketService.configure(RASPBERRY_HOST2);
    }

    private static void startApplication() {
        currentApplication = new CheaterPathFinderApp();
        currentApplication.start();
    }

}
