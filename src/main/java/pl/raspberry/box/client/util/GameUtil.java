package pl.raspberry.box.client.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GameUtil {

    public static void sleep(long timeInMs) {
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
