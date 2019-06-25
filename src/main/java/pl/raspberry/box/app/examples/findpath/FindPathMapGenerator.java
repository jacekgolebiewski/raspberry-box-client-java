package pl.raspberry.box.app.examples.findpath;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.core.model.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
public class FindPathMapGenerator {

    private static final Random RANDOM = new Random();
    private static final int SIZE = Matrix.SIZE;

    Matrix generate() {
        return generateForPath(generatePath());
    }

    Matrix generateForPath(List<Point> path) {
        Matrix result = new Matrix();
        for (int i = 0; i < 40; i++) {
            result.fillCell(RANDOM.nextInt(SIZE), RANDOM.nextInt(SIZE), 1);
        }
        path.forEach(point -> result.fillCell(point.getX(), point.getY(), 0));
        return result;
    }

    Matrix generateStrict() {
        List<Point> path = generatePath();

        Matrix result = new Matrix().fillAll();
        path.forEach(point -> result.fillCell(point.getX(), point.getY(), 0));
        return result.flipVertically();
    }

    public List<Point> generatePath() {
        List<Point> path = new ArrayList<>(Arrays.asList(new Point(0, RANDOM.nextInt(SIZE))));
        Point last;
        while ((last = path.get(path.size() - 1)).getX() < SIZE - 2) {
            int length = RANDOM.nextInt(3)+1;
            int direction = RANDOM.nextBoolean() ? 1 : -1;
            int newY = last.getY() + direction * length;
            if (newY >= 0 && newY < SIZE) {
                int newX = last.getX() + 1;
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        Point newPoint = new Point(newX, last.getY() + direction*i);
                        path.add(newPoint);
                    }
                }
            }
        }
        path.add(new Point(last.getX()+1, last.getY()));
        return path;
    }

}
