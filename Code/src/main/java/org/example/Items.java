package org.example;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Iterator;

public class Items extends GameLoop{

    int x, y;
    boolean visible = false;

    PApplet parent;

    public static DoorKey doorKey;
    public static Door door;

    public static ArrayList<BombPowerUp> powerUps = new ArrayList<>();
    public static int powerUp_items = 5;

    public static ArrayList<ExtraBomb> extraBombs = new ArrayList<>();
    public static int extraBomb_items = 5;

    public static ArrayList<SpeedUp> speedUps = new ArrayList<>();
    public static int speedUp_items = 5;

    public static void removeMarkedObjects() {
        // 移除被標記的岩石
        Iterator<BreakableRock> rockIterator = Obstacle.rocks.iterator();
        while (rockIterator.hasNext()) {
            BreakableRock rock = rockIterator.next();
            if (rock.isMarkedForRemoval()) {
                Obstacle.removeRockFromObstacleGrid(rock);
                rockIterator.remove();
            }
        }

        // 移除被標記的增強道具
        Iterator<BombPowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            BombPowerUp powerUp = powerUpIterator.next();
            if (powerUp.isMarkedForRemoval()) {
                powerUpIterator.remove();
            }
        }

        // 移除被標記的額外炸彈
        Iterator<ExtraBomb> extraBombIterator = extraBombs.iterator();
        while (extraBombIterator.hasNext()) {
            ExtraBomb extraBomb = extraBombIterator.next();
            if (extraBomb.isMarkedForRemoval()) {
                extraBombIterator.remove();
            }
        }

        Iterator<SpeedUp> speedUpIterator = speedUps.iterator();
        while (speedUpIterator.hasNext()) {
            SpeedUp speedUp = speedUpIterator.next();
            if (speedUp.isMarkedForRemoval()) {
                speedUpIterator.remove();
            }
        }

        // 如果有其他類型的物件也需要被移除，繼續在這裡添加相應的邏輯
    }
}
