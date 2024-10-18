package Racer;
import com.javarush.engine.cell.*;
public class RacerGame extends Game{
    public static final int WIDTH=64;
    public static final int HEIGHT=64;
    public static final int CENTER_X=WIDTH/2;
    public static final int ROADSIDE_WIDTH=14;
    private RoadMarking roadMarking;
    private PlayerCar player;
    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }
    private void createGame(){
        roadMarking=new RoadMarking();
        player=new PlayerCar();
        setTurnTimer(40);
        drawScene();
    }
    private void drawField(){
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

    private void drawScene(){
        drawField();
        roadMarking.draw(this);
        player.draw(this);
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if(x>WIDTH-1||y>HEIGHT-1||x<0||y<0){
            return;}
        else{
            super.setCellColor(x, y, color);
        }
    }
    private void moveAll(){
        roadMarking.move(player.speed);
        player.move();
    }

    @Override
    public void onTurn(int step) {
        moveAll();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key){
            case RIGHT -> player.setDirection(Direction.RIGHT);
            case LEFT -> player.setDirection(Direction.LEFT);
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key){
            case RIGHT -> player.setDirection(Direction.NONE);
            case LEFT -> player.setDirection(Direction.NONE);
        }
    }
}
