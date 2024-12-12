package Racer;

import com.javarush.engine.cell.*;

public class GameObject {
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x;
    public int y;
    public int height;
    public int width;
    public int[][] matrix;

    public GameObject(int x, int y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        this.height = matrix.length;
        this.width = matrix[0].length;
    }

    public void draw(Game game) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                game.setCellColor(x + j, y + i, Color.values()[matrix[i][j]]);
            }
        }
    }

    public boolean isCollisionPossible(GameObject otherGameObject) {
        if (x > otherGameObject.x + otherGameObject.width || x + width < otherGameObject.x) {
            return false;
        }

        if (y > otherGameObject.y + otherGameObject.height || y + height < otherGameObject.y) {
            return false;
        }
        return true;
    }

    public boolean isCollision(GameObject gameObject) {
        if (!isCollisionPossible(gameObject)) {
            return false;
        }

        for (int carX = 0; carX < gameObject.width; carX++) {
            for (int carY = 0; carY < gameObject.height; carY++) {
                if (gameObject.matrix[carY][carX] != 0) {
                    if (isCollision(carX + gameObject.x, carY + gameObject.y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private boolean isCollision(int x, int y) {
        for (int matrixX = 0; matrixX < width; matrixX++) {
            for (int matrixY = 0; matrixY < height; matrixY++) {
                if (matrix[matrixY][matrixX] != 0 && matrixX + this.x == x && matrixY + this.y == y) {
                    return true;
                }
            }
        }
        return false;
    }
}
