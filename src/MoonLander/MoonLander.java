package MoonLander;

import com.javarush.engine.cell.*;

public class MoonLander extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    private Rocket rocket;
    private GameObjectMoon landscape;

    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private GameObjectMoon platform;

    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        isLeftPressed = false;
        isRightPressed = false;
        isUpPressed = false;
        platform = new GameObjectMoon(23, MoonLander.HEIGHT - 1, ShapeMatrix.PLATFORM);
        isGameStopped = false;
        showGrid(false);
        createGameObjects();
        drawScene();
        setTurnTimer(50);
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellColor(i, j, Color.BLACK);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }

    private void createGameObjects() {
        rocket = new Rocket(WIDTH / 2, 0);
        landscape = new GameObjectMoon(0, 25, ShapeMatrix.LANDSCAPE);
    }

    @Override
    public void onTurn(int step) {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        drawScene();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x > WIDTH - 1 || x < 0 || y < 0 || y > HEIGHT - 1) return;
        super.setCellColor(x, y, color);
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case UP -> isUpPressed = true;

            case LEFT -> {
                isLeftPressed = true;
                isRightPressed = false;
            }
            case RIGHT -> {
                isRightPressed = true;
                isLeftPressed = false;
            }
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key) {
            case UP -> isUpPressed = false;

            case LEFT -> isLeftPressed = false;

            case RIGHT -> isRightPressed = false;
            case SPACE -> {
                if (isGameStopped) {
                    createGame();
                }
            }
        }
    }

    private void check() {
        if (rocket.isCollision(platform) && rocket.isStopped()) {
            win();
        } else if (rocket.isCollision(landscape)) {
            gameOver();
        }
    }

    private void win() {
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.VIOLET, "YOU WIN", Color.AQUA, 100);
        stopTurnTimer();
    }

    private void gameOver() {
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.CORNSILK, "YOU LOSE", Color.CHARTREUSE, 100);
        stopTurnTimer();
    }

}
