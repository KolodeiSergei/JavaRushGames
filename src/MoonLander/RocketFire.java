package MoonLander;

import com.javarush.engine.cell.Game;

import java.util.List;

public class RocketFire extends GameObjectMoon{
    private List<int[][]> frames;
    private int framesIndex;
    private boolean isVisible;

    public RocketFire(List<int[][]> frameList) {
        super(0, 0, frameList.get(0));
        this.framesIndex=0;
        this.frames=frameList;
        this.isVisible=false;
    }
    private void nextFrame(){
        framesIndex++;
        if(framesIndex>=frames.size()) framesIndex=0;
        matrix= frames.get(framesIndex);
    }

    @Override
    public void draw(Game game) {
        if(!isVisible) return;
        nextFrame();
        super.draw(game);
    }
    public void show(){
        isVisible=true;
    }
    public void hide(){
        isVisible=false;
    }
}
