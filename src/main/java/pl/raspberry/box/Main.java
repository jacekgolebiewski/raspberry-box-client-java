package pl.raspberry.box;

import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.app.jgolebiewski.LedOnButtonApp;
import pl.raspberry.box.client.service.websocket.WebSocketService;

public class Main {

    public static final String RASPBERRY_HOST = "ws://192.168.0.15:9001";

    static RaspberryBoxApplication currentApplication;

    public static void main(String[] args) {
        applyConfiguration();
        startApplication();
    }

    private static void applyConfiguration() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
        WebSocketService.configure(WebSocketService.LOCALHOST);
    }

    private static void startApplication() {
        try {
            currentApplication = new LedOnButtonApp();
            currentApplication.start();
        } finally {
            currentApplication.stop();
        }
    }

}
