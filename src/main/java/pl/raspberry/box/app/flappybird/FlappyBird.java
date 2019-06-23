package pl.raspberry.box.app.flappybird;

import lombok.extern.slf4j.Slf4j;
import pl.raspberry.box.app.RaspberryBoxApplication;
import pl.raspberry.box.client.model.request.screen.Matrix;
import pl.raspberry.box.client.model.response.button.Button;
import pl.raspberry.box.client.service.ScreenService;
import pl.raspberry.box.client.service.console.ConsoleInputService;
import pl.raspberry.box.client.util.GameUtil;

import java.util.List;
import java.util.Random;

@Slf4j
public class FlappyBird extends RaspberryBoxApplication {

    private static final int USER_COLUMN = 2;

    private final ScreenService screenService = new ScreenService();
    private final ConsoleInputService consoleInputService = new ConsoleInputService();
    private final WorldGenerator worldGenerator = new WorldGenerator();

    private Matrix worldMatrix = new Matrix();

    private String userName = "";
    private Long score = 0L;
    private int userPosition = 3;
    private int speedDivider = 5;
    private int distanceBetweenWalls = 5;

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
        renderPlayerCycle(speedDivider);
        if (index < 3) {
            return;
        }
        if (index > 10) {
            stop();
        }
        score = (long) index;
        List<Integer> column = getWall(index, distanceBetweenWalls);
        worldMatrix = worldMatrix.shiftLeft().setColumn(Matrix.SIZE - 1, column);
        renderGame();
        screenService.clean();
    }

    private void renderPlayerCycle(int speedDivider) {
        for (int i = 0; i < speedDivider; i++) {
            renderGame();
            GameUtil.sleep(100);
        }
    }

    private void renderGame() {
        userPosition = getUserPosition();
        screenService.setScreenFrame(worldMatrix.clone().fillCell(userPosition, USER_COLUMN, 1));
    }

    private List<Integer> getWall(int iteration, int distanceBetweenWalls) {
        List<Integer> column;
        if (iteration % distanceBetweenWalls == 0) {
            column = worldGenerator.generateWall();
        } else {
            column = Matrix.getEmptyList();
        }
        return column;
    }

    private int getUserPosition() {
        return new Random().nextInt(Matrix.SIZE);
    }

    @Override
    public void onApplicationStopped() {
        screenService.clean();
        log.info("User: '" + userName + "' score is: " + score);
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
