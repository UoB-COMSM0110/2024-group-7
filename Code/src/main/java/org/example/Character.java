package org.example;

import processing.core.PApplet;

import java.util.ArrayList;

public class Character extends GameLoop{
    PApplet parent;
    int px, py;
    int health;
    int speed;
    public static ArrayList<Player> players;

    public static ArrayList<Enemy> enemies;

    public boolean collisionDetect() {
        int x = 0, y = 0;
        if (up) {
            x = this.x() + tile / 2;
            y = this.y() + tile / 2 - this.getSpeed();
        }
        if (down) {
            x = this.x() + tile / 2;
            y = this.y() + tile / 2 + this.getSpeed();
        }
        if (left) {
            x = this.x() + tile / 2 - this.getSpeed();
            y = this.y() + tile / 2;
        }
        if (right) {
            x = this.x() + tile / 2 + this.getSpeed();
            y = this.y() + tile / 2;
        }

        int width = tile;
        int height = tile;

        int leftX = x - width / 2;
        int rightX = x + width / 2 - 1;
        int topY = y - height / 2;
        int bottomY = y + height / 2 - 1;

        int topLeftGridX = (leftX - 15) / tile;
        int topLeftGridY = (topY - 75) / tile;
        int topRightGridX = (rightX - 15) / tile;
        int bottomLeftGridY = (bottomY - 75) / tile;

        if (Obstacle.obstacleGrid[topLeftGridX][topLeftGridY] ||
                Obstacle.obstacleGrid[topRightGridX][topLeftGridY] ||
                Obstacle.obstacleGrid[topLeftGridX][bottomLeftGridY] ||
                Obstacle.obstacleGrid[topRightGridX][bottomLeftGridY]) {
            move = false;
            return false;
        }
        /*for (int i = 0; i < enemyNumber; i++) {
            if (i < totEnemies && dist(x, y, enemies.get(i).x() + (float) tile / 2, enemies.get(i).y() + (float) tile / 2) < tile) {
                collision = true;
            }
        }*/
        for (Bomb[] row : bombs) {
            for (Bomb bomb : row) {
                if (bomb.showed && !bomb.bombActive && dist(x, y, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) >= tile) {
                    bomb.bombActive = true;
                }
                if (bomb.showed && bomb.bombActive && dist(x, y, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) < tile) {
                    return false;
                }
            }
        }
        return true;
    }

    int x(){return px;}
    int y(){return py;}

    public int getSpeed() {
        return this.speed;
    }

}
