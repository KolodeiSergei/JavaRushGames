package MinesWeeper;

public class GameObjectSaper {
    public int x;
    public int y;
    public boolean isMine;
    public int countMineNeighbors;

    public boolean isOpen;
    public boolean isFlag;

    GameObjectSaper(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine=isMine;
    }
}