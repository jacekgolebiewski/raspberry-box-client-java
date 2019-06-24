package pl.raspberry.box.app.examples.findpath.solution;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.examples.findpath.FindPathApp;
import pl.raspberry.box.app.examples.findpath.Point;
import pl.raspberry.box.core.model.request.screen.Matrix;

import java.util.List;

/**
 * Przykładowa implementacja rozwiązania problemu znalezienia ścieżki
 */
@Slf4j
public class CheaterPathFinderApp extends FindPathApp {

    @Override
    public List<Point> getPath(Matrix map) {
        log.debug(this.getOriginPath().toString());
        return this.getOriginPath();
    }

}
