package Snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;


public class Snake {
    private List<GameObjectSnake> snakeParts = new ArrayList<>();
    private static final String HEAD_SNAKE="\uD83D\uDC7E";
    private static final String BODY_SNAKE="\u26AB";
    public boolean isAlive=true;
    private Direction direction= Direction.LEFT;


    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DAWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }
        if(direction==Direction.LEFT&&this.direction==Direction.RIGHT){
            return;
        } else if (direction==Direction.RIGHT&&this.direction==Direction.LEFT) {
            return;
        }
        else if (direction==Direction.UP&&this.direction==Direction.DAWN) {
            return;
        }
        else if (direction==Direction.DAWN&&this.direction==Direction.UP) {
            return;
        }
        this.direction = direction;
    }

    public Snake(int x, int y) {
        GameObjectSnake snake1= new GameObjectSnake(x,y);
        GameObjectSnake snake2= new GameObjectSnake(x+1,y);
        GameObjectSnake snake3= new GameObjectSnake(x+2,y);
        snakeParts.add(snake1);
        snakeParts.add(snake2);
        snakeParts.add(snake3);
    }
    public void draw(Game game){
        Color snakeColor ;
        if(isAlive){
            snakeColor = Color.GREENYELLOW;
        }else{
            snakeColor = Color.GREENYELLOW;
        }
        game.setCellValueEx(snakeParts.get(0).x,snakeParts.get(0).y,Color.NONE,HEAD_SNAKE,snakeColor,75);
        for (int i =1;i<snakeParts.size();i++){
            game.setCellValueEx(snakeParts.get(i).x,snakeParts.get(i).y,Color.NONE,BODY_SNAKE,snakeColor,75);
        }
    }
    public void move(Apple apple){
        GameObjectSnake newHead=createNewHead();

        if(newHead.x<0||newHead.x>SnakeGame.WIDTH-1||newHead.y<0
                ||newHead.y>SnakeGame.HEIGHT-1||checkCollision(newHead)){
            isAlive=false;
            return;
        }

        snakeParts.add(0,createNewHead());
        if(apple.x==snakeParts.get(0).x&&apple.y==snakeParts.get(0).y){
            apple.isAlive=false;
        }else {
            removeTail();
        }

    }
    public GameObjectSnake createNewHead(){
        if(direction==Direction.LEFT){
            return new GameObjectSnake(snakeParts.get(0).x-1,snakeParts.get(0).y);
        }else if(direction==Direction.RIGHT){
            return new GameObjectSnake(snakeParts.get(0).x+1,snakeParts.get(0).y);
        }else if(direction==Direction.UP){
            return new GameObjectSnake(snakeParts.get(0).x,snakeParts.get(0).y-1);
        }else /*if(direction==Direction.DAWN)*/{
            return new GameObjectSnake(snakeParts.get(0).x,snakeParts.get(0).y+1);
        }//else return;
    }
    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }
    public boolean checkCollision(GameObjectSnake game){
        for(int i=0;i<snakeParts.size();i++) {
            if ((game.x == snakeParts.get(i).x) && (game.y == snakeParts.get(i).y)) {
                return true;
            }
        }
        return false;
    }
    public int getLength(){
        return snakeParts.size();
    }
}
