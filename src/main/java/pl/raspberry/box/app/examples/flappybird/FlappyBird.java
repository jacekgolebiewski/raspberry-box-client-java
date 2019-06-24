package pl.raspberry.box.app.examples.flappybird;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.core.model.request.screen.Matrix;
import pl.raspberry.box.core.model.response.button.Button;
import pl.raspberry.box.core.service.ScreenService;
import pl.raspberry.box.core.service.console.ConsoleInputService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Gra na wzór klasycznego flappy bird wykorzystująca czujnik odległości i matrycę led
 */
@Slf4j
public class FlappyBird extends RaspberryBoxApplication {

    private static final int USER_COLUMN = 2;
    private static final int MIN_DISTANCE = 5;
    private static final int FREE_START_LENGTH = 3;

    private final ScreenService screenService = new ScreenService();
    private final ConsoleInputService consoleInputService = new ConsoleInputService();
    private final WorldGenerator worldGenerator = new WorldGenerator();

    private Matrix worldMatrix = new Matrix();

    private String userName = "";
    private Long score = 0L;
    private int userPosition = 7;
    private int speedDivider = 5;
    private int distanceBetweenWalls = 5;

    private boolean endGame = false;

    @Override
    public void onApplicationStarted() {
        init();
        log.info("What\'s your name?");
        System.out.print(">> ");
        userName = consoleInputService.readLine();
    }

    private void init() {
        worldMatrix = new Matrix();
        screenService.setScreenFrame(worldMatrix);
    }

    @Override
    protected void onLoopCycle(int index) {
        if(endGame) {
            return;
        }
        renderGame();
        updateSpeedDivider(index);
        int time = index/speedDivider;
        updateUserPosition();
        if( index % speedDivider == 0) {
            updateGameState(time);
        }
    }

    private void updateSpeedDivider(int index) {
        if (index < 50) {
            speedDivider = 6;
        } else if(index < 100) {
            speedDivider = 5;
        } else if(index < 300) {
            speedDivider = 4;
        } else {
            speedDivider = 3;
        }
    }

    private void updateUserPosition() {
        userPosition = getUserPosition();
    }

    private void updateGameState(int time) {
        if(worldMatrix.getCellValue(userPosition, USER_COLUMN) == 1) {
            gameFinished();
            return;
        }

        score = (long) time;
        List<Integer> column = getWall(time, distanceBetweenWalls);
        worldMatrix = worldMatrix.shiftLeft().setColumn(Matrix.SIZE - 1, column);
    }

    private void renderGame() {
        screenService.setScreenFrame(worldMatrix.clone().fillCell(userPosition, USER_COLUMN, 1));
    }

    private void gameFinished() {
        endGame = true;
        screenService.failScreen();
        stop();
    }

    private List<Integer> getWall(int time, int distanceBetweenWalls) {
        List<Integer> column;
        if (time > FREE_START_LENGTH && time % distanceBetweenWalls == 0) {
            column = worldGenerator.generateWall();
        } else {
            column = Matrix.getEmptyList();
        }
        return column;
    }

    private int getUserPosition() {
        return userPosition;
    }

    @Override
    public void onApplicationStopped() {
        log.info("User: '" + userName + "' score is: " + score);
        try {
            File resultsFile = new File("results.txt");
            resultsFile.createNewFile();
            Files.write(resultsFile.toPath(), (userName+";"+score+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onNewDistanceRead(Double distance) {
        // position 0-7 depending on distance
        userPosition = 7 - ((Math.round(distance.intValue()) - MIN_DISTANCE) / 3);
        if(userPosition < 0) {
            userPosition = 0;
        }
        if(userPosition > 7) {
            userPosition = 7;
        }
    }

    @Override
    public void onButtonPressed(Button button) {

    }

    @Override
    public void onButtonReleased(Button button) {

    }

}
