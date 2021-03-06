package pl.raspberry.box.app.examples.flappybird;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.core.model.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
class WorldGenerator {

    private final int SPACE_WIDTH = 3;
    private final Random RANDOM = new Random();

    List<Integer> generateWall() {
        int wallStart = RANDOM.nextInt(Matrix.SIZE - SPACE_WIDTH + 1);
        List<Integer> wall = new ArrayList<>();
        wall.addAll(Collections.nCopies(wallStart, 1));
        wall.addAll(Collections.nCopies(SPACE_WIDTH, 0));
        wall.addAll(Collections.nCopies(Matrix.SIZE - wallStart - SPACE_WIDTH, 1));
        return wall;
    }


}
