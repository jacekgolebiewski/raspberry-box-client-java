package pl.raspberry.box;

import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.app.jgolebiewski.LedOnButtonApp;

public class Main {

    static RaspberryBoxApplication currentApplication = new LedOnButtonApp();

    public static void main(String[] args) {
        currentApplication.start();
    }

}
