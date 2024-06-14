

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class Saper extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;

    final String MINE="\uD83D\uDCA3";

    final String FLAG="\uD83D\uDEA9";

    private int countFlags;

    private boolean isGameStopped;
    private int countClosedTiles=SIDE*SIDE;

    private int score=0;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();

    }

    private void createGame() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[x][y] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.BLUE);

            }
        }
        countMineNeighbors();
        countFlags=countMinesOnField;
        isGameStopped=false;
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
            for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[x][y] == gameObject) {
                    continue;
                }
                result.add(gameField[x][y]);
            }
        }
        return result;
    }

    private void countMineNeighbors(){
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                if(!gameField[x][y].isMine){
                    for(var count:getNeighbors(gameField[x][y])){
                        if(count.isMine){
                            gameField[x][y].countMineNeighbors++;
                        }
                    }


                }
            }
        }
    }
    private void openTile(int x,int y){
        GameObject gameTile = gameField[x][y];
        if(isGameStopped||gameTile.isFlag|| gameTile.isOpen) return;
        gameTile.isOpen=true;
        countClosedTiles--;
        setCellColor(x,y,Color.DARKBLUE);
        if(countClosedTiles==countMinesOnField && !gameTile.isMine) win();
        if(gameTile.isMine){
            setCellValueEx(x,y,Color.RED, MINE);
            gameOver();
        }else if(gameTile.countMineNeighbors==0){

            setCellValue(x,y,"");
            List<GameObject> neighbors = getNeighbors(gameTile);
            for (GameObject neighbor : neighbors) {
                if (!neighbor.isOpen) {
                    openTile(neighbor.x, neighbor.y);
                }
            }
        }else{
            setCellValue(x,y, String.valueOf(gameTile.countMineNeighbors));
        }
        score+=5;
        setScore(score);
    }

    private void markTile(int x,int y){
        GameObject gameTile = gameField[x][y];
        if(isGameStopped|| gameTile.isOpen) return;
        if(countFlags!=0 && !gameTile.isFlag){
            gameTile.isFlag=true;
            countFlags--;
            setCellValue(gameTile.x, gameTile.y, FLAG);
            setCellColor(gameTile.x, gameTile.y,Color.BLUEVIOLET);
        } else if (gameTile.isFlag ) {
            gameTile.isFlag=false;
            countFlags++;
            setCellValue(gameTile.x, gameTile.y, "");
            setCellColor(gameTile.x, gameTile.y,Color.BLUE);
        }
    }

    private void gameOver(){
        isGameStopped=true;
        showMessageDialog(Color.RED,"YOU LOSE", Color.BLACK, 100);
    }
    private void win(){
        isGameStopped=true;
        showMessageDialog(Color.BLACK,"YOU WIN", Color.GOLD, 100);
    }
    private void restart(){
        isGameStopped=false;
        countClosedTiles=SIDE*SIDE;
        countMinesOnField=0;
        createGame();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        if (isGameStopped) {
            restart();
            return;
        }
        openTile(x,y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x,y);
    }
}