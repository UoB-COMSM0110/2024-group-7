package org.example;

import java.util.ArrayList;
import processing.core.PApplet;

public class Obstacle extends GameLoop{
    /*public static ArrayList<Obstacle> obstacle = new ArrayList<>();*/
    PApplet parent;
    int px;
    int py;
    public static ArrayList<Wall> walls;
    public static ArrayList<BreakableRock> rocks;
    public static boolean[][]obstacleGrid = new boolean[50][50];//need to be fixed!!!!!

    public static void initializeObstacleGrid(int rows, int cols) {
        /*obstacleGrid = new boolean[50][50];//need to be fixed!!!!!*/
        for (BreakableRock rock : Obstacle.rocks) {
            int gridX = (rock.x() - 15) / tile;
            int gridY = (rock.y() - 75) / tile;
            obstacleGrid[gridX][gridY] = true;
        }
        for (Wall wall : Obstacle.walls) {
            int gridX = (wall.x() - 15) / tile;
            int gridY = (wall.y() - 75) / tile;
            obstacleGrid[gridX][gridY] = true;
        }
    }

    public static void removeRockFromObstacleGrid(BreakableRock rock) {
        int gridX = (rock.x() - 15) / tile;
        int gridY = (rock.y() - 75) / tile;

        // 确保坐标在辅助数组的范围内
        /*if (gridX >= 0 && gridX < obstacleGrid.length && gridY >= 0 && gridY < obstacleGrid[gridX].length) {*/
            obstacleGrid[gridX][gridY] = false; // 移除rock
        /*}*/
    }

    public static boolean areThereRocks(int x, int y) {
        int gridX = (x - 15) / tile;
        int gridY = (y - 75) / tile;
        return obstacleGrid[gridX][gridY];
    }
    int x(){return px;}
    int y(){return py;}
}
