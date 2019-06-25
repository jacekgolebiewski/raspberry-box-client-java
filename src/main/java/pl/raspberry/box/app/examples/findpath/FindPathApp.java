package pl.raspberry.box.app.examples.findpath;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.core.model.Matrix;
import pl.raspberry.box.core.model.response.button.Button;
import pl.raspberry.box.core.service.console.ConsoleInputService;
import pl.raspberry.box.core.service.screen.ScreenService;
import pl.raspberry.box.core.util.GameUtil;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Aplikacja rozwiązania problemu znalezienia ścieżki
 */
@Slf4j
public abstract class FindPathApp extends RaspberryBoxApplication {

    private final ScreenService screenService = new ScreenService();
    private final ConsoleInputService consoleInputService = new ConsoleInputService();
    private final FindPathMapGenerator findPathMapGenerator = new FindPathMapGenerator();

    private int score = 0;

    /**
     * This variable is used for map generation, should not be used in solution.
     */
    @Getter
    @Deprecated
    private List<Point> originPath;

    public abstract List<Point> getPath(Matrix map);

    @Override
    public void onApplicationStarted() {
    }

    @Override
    protected void onLoopCycle(int index) {
        originPath = findPathMapGenerator.generatePath();
        if(index % 70 == 0) {
            Matrix map = findPathMapGenerator.generateForPath(originPath);
            screenService.setScreenFrame(map);
            List<Point> solution = getPath(map);
            if(solution.get(0).getX() != 0) {
                throw new RuntimeException("Algorytm nieprawidłowy, pierwszy punkt ścieżki musi mieć współrzędną X == 0");
            }
            Matrix screen = map.clone();
            solution.forEach(point -> {
                if(screen.getCellValue(point.getX(), point.getY()) == 1) {
                    screenService.failScreen();
                    throw new RuntimeException("Algorytm nieprawidłowy, ścieżka nachodzi na zajęte pole");
                }
                screen.fillCell(point.getX(), point.getY(), 1);
                showPath(map, screen, 2);
                GameUtil.sleep(200);
            });
            if(solution.get(solution.size()-1).getX() != 7) {
                screenService.failScreen();
                throw new RuntimeException("Algorytm nieprawidłowy, ostatni punkt ścieżki musi mieć współrzędną X == 7");
            }
            showPath(map, screen, 5);
            score++;
            if(score >= 5) {
                screenService.successScreen();
                stop();
            }
        }
    }

    private void showPath(Matrix map, Matrix screen, int times) {
        IntStream.range(0, times).forEach(idx -> {
            GameUtil.sleep(200);
            screenService.setScreenFrame(map);
            GameUtil.sleep(200);
            screenService.setScreenFrame(screen);
        });
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
