package MoonLander;
import com.javarush.engine.cell.*;

import java.util.Arrays;

public class Rocket extends GameObjectMoon{

    private double speedY=0;
    private double speedX=0;
    private double boost=0.05;
    private double slowdawn=boost/10;
    private RocketFire dawnFire;
    private RocketFire leftFire;
    private RocketFire rightFire;

    public Rocket(double x, double y) {
        super(x, y, ShapeMatrix.ROCKET);
        dawnFire = new RocketFire(Arrays.asList(ShapeMatrix.FIRE_DOWN_1, ShapeMatrix.FIRE_DOWN_2, ShapeMatrix.FIRE_DOWN_3));
        leftFire =new RocketFire(Arrays.asList(ShapeMatrix.FIRE_SIDE_1, ShapeMatrix.FIRE_SIDE_2));
        rightFire = new RocketFire(Arrays.asList(ShapeMatrix.FIRE_SIDE_1, ShapeMatrix.FIRE_SIDE_2));
    }
    public void move(boolean isUpPressed, boolean isLeftPressed, boolean isRightPressed){
        if(isUpPressed){
            speedY-=boost;
            y+=speedY;
        }else {
            speedY+=boost;
            y+=speedY;
        }
        if(isLeftPressed){
            speedX-=boost;
            x+=speedX;
        }
        if(isRightPressed){
            speedX+=boost;
            x+=speedX;
        }
        if(!isLeftPressed&&!isRightPressed&&(speedX>-slowdawn)&&(speedX<slowdawn)){
            speedX=0;
        }
        if (!isLeftPressed&&!isRightPressed&&(speedX>slowdawn)) {
            speedX-=slowdawn;
            x+=speedX;
        }
        if (!isLeftPressed&&!isRightPressed&&(speedX<-slowdawn)) {
            speedX+=slowdawn;
            x+=speedX;
        }
        checkBorders();
        switchFire(isUpPressed, isLeftPressed, isRightPressed);
    }
    private void checkBorders(){
        if(x<0) {
            x=0;
            speedX=0;
        }
        if(x+width>MoonLander.WIDTH){
            x=MoonLander.WIDTH-width;
            speedX=0;
        }
        if(y<0){
            y=0;
            speedY=0;
        }
    }
    public boolean isStopped(){
        if(speedY<(10*boost)){
            return true;
        }
        return false;
    }
    public boolean isCollision(GameObjectMoon gameOb){
        int transparent = Color.NONE.ordinal();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int objectX = i + (int) x - (int) gameOb.x;
                int objectY = j + (int) y - (int) gameOb.y;

                if (objectX < 0 || objectX >= gameOb.width || objectY < 0 || objectY >= gameOb.height) {
                    continue;
                }

                if (matrix[j][i] != transparent && gameOb.matrix[objectY][objectX] != transparent) {
                    return true;
                }
            }
        }
        return false;
    }

    public void land(){
        y--;
    }
    public void crash(){
        matrix=ShapeMatrix.ROCKET_CRASH;
    }
    private void switchFire(boolean isUpPressed, boolean isLeftPressed, boolean isRightPressed){
        if(isUpPressed){
            dawnFire.x=x+width/2;
            dawnFire.y=y+height;
            dawnFire.show();
        }else {
            dawnFire.hide();
        }if(isLeftPressed){
            leftFire.x=x+width;
            leftFire.y=y+height;
            leftFire.show();
        }else {
            leftFire.hide();
        }if(isRightPressed){
            rightFire.x=x- ShapeMatrix.FIRE_SIDE_1[0].length;
            rightFire.y=y+height;
            rightFire.show();
        }else {
            rightFire.hide();
        }
    }

    @Override
    public void draw(Game game) {
        super.draw(game);
        dawnFire.draw(game);
        leftFire.draw(game);
        rightFire.draw(game);
    }
}
