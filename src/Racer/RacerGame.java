package Racer;

import Racer.road.FinishLine;
import Racer.road.RoadManager;
import com.javarush.engine.cell.*;

public class RacerGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;
    private static final int RACE_GOAL_CARS_COUNT = 40;
    private RoadMarking roadMarking;
    private RoadManager roadManager;
    private PlayerCar player;
    private boolean isGameStopped;
    private FinishLine finishLine;
    private ProgressBar progressBar;
    private int score;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        isGameStopped = false;
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        roadManager = new RoadManager();
        finishLine = new FinishLine();
        progressBar =new ProgressBar(RACE_GOAL_CARS_COUNT);
        setTurnTimer(40);
        score=3500;
        drawScene();
    }

    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x == CENTER_X) {
                    setCellColor(x, y, Color.WHITE);
                } else if (x >= ROADSIDE_WIDTH && x < WIDTH - ROADSIDE_WIDTH) {
                    setCellColor(x, y, Color.DIMGREY);
                } else {
                    setCellColor(x, y, Color.GREEN);
                }
            }
        }
    }

    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        player.draw(this);
        roadManager.draw(this);
        finishLine.draw(this);
        progressBar.draw(this);
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (!(x > WIDTH - 1 || y > HEIGHT - 1 || x < 0 || y < 0)) {
            super.setCellColor(x, y, color);
        }
    }

    private void moveAll() {
        roadMarking.move(player.speed);
        roadManager.move(player.speed);
        finishLine.move(player.speed);
        progressBar.move(roadManager.getPassedCarsCount());
        player.move();

    }

    @Override
    public void onTurn(int step) {
        if (roadManager.checkCrush(player)) {
            gameOver();
            drawScene();
            return;
        }
        roadManager.generateNewRoadObjects(this);
        if (roadManager.getPassedCarsCount() >= RACE_GOAL_CARS_COUNT) {
            finishLine.show();
        }
        if(finishLine.isCrossed(player)){
            win();
            drawScene();
        }
        score-=5;
        setScore(score);
        moveAll();
        drawScene();

    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case RIGHT -> player.setDirection(Direction.RIGHT);
            case LEFT -> player.setDirection(Direction.LEFT);
            case SPACE -> {
                if (isGameStopped) createGame();
            }
            case UP -> player.speed = 2;
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key) {
            case RIGHT, LEFT -> player.setDirection(Direction.NONE);
            case UP -> player.speed = 1;
        }
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "YOU LOSE", Color.AQUA, 50);
        stopTurnTimer();
        player.stop();

    }
    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "YOU WIN", Color.AQUA, 50);
        stopTurnTimer();
    }
}
