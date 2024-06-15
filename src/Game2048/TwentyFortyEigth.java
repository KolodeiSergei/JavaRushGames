package Game2048;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

public class TwentyFortyEigth extends Game {
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score = 0;


    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }

    private void createGame() {
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();

    }

    private void drawScene() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                setCellColoredNumber(x, y, gameField[y][x]);

            }
        }

    }

    private void createNewNumber() {
        boolean isCreated = false;
        if (getMaxTileValue() == 2048) win();
        if (!canUseMove()) gameOver();

        do {
            int x = getRandomNumber(SIDE);
            int y = getRandomNumber(SIDE);
            if (gameField[x][y] == 0) {
                gameField[x][y] = getRandomNumber(10) == 1 ? 4 : 2;
                isCreated = true;
            }
        } while (!isCreated);
    }

    private Color getColorByValue(int value) {
        switch (value) {
            case 0 -> {
                return Color.WHITE;
            }
            case 2 -> {
                return Color.BISQUE;
            }
            case 4 -> {
                return Color.PURPLE;
            }
            case 8 -> {
                return Color.GREEN;
            }
            case 16 -> {
                return Color.GRAY;
            }
            case 32 -> {
                return Color.RED;
            }
            case 64 -> {
                return Color.ROSYBROWN;
            }
            case 128 -> {
                return Color.LAVENDER;
            }
            case 256 -> {
                return Color.SALMON;
            }
            case 512 -> {
                return Color.SANDYBROWN;
            }
            case 1024 -> {
                return Color.SILVER;
            }
            case 2048 -> {
                return Color.GOLD;
            }
            default -> {
                return Color.BLACK;
            }
        }
    }

    private void setCellColoredNumber(int x, int y, int value) {
        if (value == 0) {
            setCellValueEx(x, y, getColorByValue(value), "");
        } else {
            setCellValueEx(x, y, getColorByValue(value), String.valueOf(value));
        }
    }

    private boolean compressRow(int[] row) {
        int insertPosition = 0;
        boolean result = false;
        for (int x = 0; x < SIDE; x++) {
            if (row[x] > 0) {
                if (x != insertPosition) {
                    row[insertPosition] = row[x];
                    row[x] = 0;
                    result = true;
                }
                insertPosition++;
            }
        }
        return result;
    }

    private boolean mergeRow(int[] row) {

        boolean isChange = false;
        for (int i = 0; i < row.length - 1; i++) {
            if (row[i] != 0 && row[i] == row[i + 1]) {
                row[i] += row[i + 1];
                row[i + 1] = 0;
                isChange = true;
                score += row[i];
                setScore(score);
            }
        }
        return isChange;
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT -> {
                if (!isGameStopped) moveLeft();
            }
            case RIGHT -> {
                if (!isGameStopped) moveRight();
            }
            case UP -> {
                if (!isGameStopped) moveUp();
            }
            case DOWN -> {
                if (!isGameStopped) moveDown();
            }
            case SPACE -> {
                isGameStopped = false;
                score = 0;
                createGame();
            }
        }
        drawScene();
    }

    private void moveLeft() {
        boolean isNewNumberNeeded = false;
        for (int[] row : gameField) {
            boolean wasCompressed = compressRow(row);
            boolean wasMerged = mergeRow(row);
            if (wasMerged) {
                compressRow(row);
            }
            if (wasCompressed || wasMerged) {
                isNewNumberNeeded = true;
            }
        }
        if (isNewNumberNeeded) {
            createNewNumber();
        }
    }

    private void moveRight() {
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();

    }

    private void moveUp() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();

    }

    private void moveDown() {
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();

    }

    private void rotateClockwise() {
        int[][] result = new int[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                result[j][SIDE - 1 - i] = gameField[i][j];
            }
        }
        gameField = result;
    }

    private int getMaxTileValue() {
        int results = 0;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (results <= gameField[i][j]) results = gameField[i][j];
            }
        }
        return results;
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "YOU WIN", Color.CYAN, 100);
    }

    private boolean canUseMove() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (gameField[y][x] == 0) {
                    return true;
                } else if (y < SIDE - 1 && gameField[y][x] == gameField[y + 1][x]) {
                    return true;
                } else if ((x < SIDE - 1) && gameField[y][x] == gameField[y][x + 1]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.BROWN, "YOU LOSE", Color.YELLOWGREEN, 100);
    }
}
