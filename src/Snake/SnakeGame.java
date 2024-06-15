package Snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private final static int GOAL=28;

    private int score;

    @Override
    public void initialize() {

        setScreenSize(WIDTH, HEIGHT);
        creatGame();

    }
    private void creatGame(){
        snake=new Snake(2,2);
        turnDelay=300;
        createNewApple();
        isGameStopped=false;
        score=0;
        drawScene();
        setTurnTimer(turnDelay);
    }
    private void drawScene(){
        for(int i=0;i<WIDTH;i++){
            for(int j=0;j<HEIGHT;j++){
                setCellValueEx(i,j,Color.DARKGREEN,"");
            }
        }
        snake.draw(this);
        apple.draw(this);

    }

    @Override
    public void onTurn(int step) {
        super.onTurn(step);
        snake.move(apple);
        if(!apple.isAlive) {
                createNewApple();
                score+=5;
                setScore(score);
                turnDelay-=10;
                setTurnTimer(turnDelay);
        }
        if(!snake.isAlive) gameOver();
        if(GOAL<snake.getLength()) win();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key){
            case UP -> snake.setDirection(Direction.UP);
            case DOWN -> snake.setDirection(Direction.DAWN);
            case LEFT -> snake.setDirection(Direction.LEFT);
            case RIGHT -> snake.setDirection(Direction.RIGHT);
            case SPACE -> {
                if(isGameStopped) creatGame();
            }
        }
    }
    private void createNewApple(){
        apple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
        while (snake.checkCollision(apple)){
            apple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
        }
    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.ALICEBLUE,"YOU LOSE", Color.RED, 100);
    }
    private void win(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.BLANCHEDALMOND,"YOU WIN", Color.GOLD, 100);
    }

}