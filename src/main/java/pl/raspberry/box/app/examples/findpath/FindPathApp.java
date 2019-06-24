package pl.raspberry.box.app.examples.findpath;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.core.model.request.screen.Matrix;
import pl.raspberry.box.core.model.response.button.Button;
import pl.raspberry.box.core.service.ScreenService;
import pl.raspberry.box.core.service.console.ConsoleInputService;
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
            GameUtil.sleep(1000);
            List<Point> solution = getPath(map);
            if(solution.get(0).getX() != 0) {
                throw new RuntimeException("Algorytm nieprawidłowy, pierwszy punkt ścieżki musi mieć współrzędną X == 0");
            }
            Matrix screen = map.clone();
            solution.forEach(point -> {
                if(screen.getCellValue(point.getX(), point.getY()) == 1) {
                    throw new RuntimeException("Algorytm nieprawidłowy, ścieżka nachodzi na zajęte pole");
                }
                screen.fillCell(point.getX(), point.getY(), 1);
                screenService.setScreenFrame(screen);
                GameUtil.sleep(1000);
            });
            if(solution.get(solution.size()-1).getX() != 7) {
                throw new RuntimeException("Algorytm nieprawidłowy, ostatni punkt ścieżki musi mieć współrzędną X == 7");
            }
            IntStream.range(0, 5).forEach(idx -> {
                GameUtil.sleep(200);
                screenService.setScreenFrame(map);
                GameUtil.sleep(200);
                screenService.setScreenFrame(screen);
            });
        }
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
