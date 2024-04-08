package org.example;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Iterator;

public class Items extends Objects{
    boolean visible = false;
    public static DoorKey doorKey;
    public static Door door;
    public static ArrayList<BombPowerUp> powerUps = new ArrayList<>();
    public static int powerUp_items = 5;
    public static ArrayList<ExtraBomb> extraBombs = new ArrayList<>();
    public static int extraBomb_items = 5;
    public static ArrayList<SpeedUp> speedUps = new ArrayList<>();
    public static int speedUp_items = 5;

    public static boolean isDoorOrKeyAt(int col, int row) {
        return (Items.door.x == col*tile+15 && Items.door.y == row*tile+75) ||
                (Items.doorKey.x == col*tile+15 && Items.doorKey.y == row*tile+75);
    }

    public static boolean checkAndHandleBreakable(int col, int row) {
        boolean handled = false;

        for (BreakableRock rock : Obstacle.rocks) {
            int rockCol = (rock.x() - 15) / tile;
            int rockRow = (rock.y() - 75) / tile;
            if (rockCol == col && rockRow == row && rock.rockExist) {
                // Mark the rock for removal instead of immediately removing it
                rock.markForRemoval();
                handled = true;
                // Reveal any hidden items under the rock
                if (rock.hideDoor) {
                    Items.door.setVisible(true); // 設置門為可見
                    rock.hideDoor = false; // 更新岩石的狀態，表示門已經不再隱藏
                }
                if (rock.hideKey) {
                    Items.doorKey.setVisible(true); // 設置鑰匙為可見
                    rock.hideKey = false; // 更新岩石的狀態，表示鑰匙已經不再隱藏
                }
                if (rock.hidePowerUp && rock.hiddenPowerUp != null) {
                    rock.hiddenPowerUp.setVisible(true);
                    rock.hidePowerUp = false;
                }
                if (rock.hideExtraBomb && rock.hiddenExtraBomb != null) {
                    rock.hiddenExtraBomb.setVisible(true);
                    rock.hideExtraBomb = false;
                }
                if (rock.hideSpeedUp && rock.hiddenSpeedUp != null) {
                    rock.hiddenSpeedUp.setVisible(true);
                    rock.hideSpeedUp = false;
                }
                break;
            }
        }
        return handled;
    }

    public static boolean checkAndDestroyExposedItems(int col, int row) {
        boolean itemDestroyed = false;

        // Check and mark power-ups for removal
        for (BombPowerUp powerUp : Items.powerUps) {
            int powerUpCol = (powerUp.x - 15) / tile;
            int powerUpRow = (powerUp.y - 75) / tile;
            if (powerUpCol == col && powerUpRow == row && powerUp.isVisible()) {
                powerUp.markForRemoval();
                itemDestroyed = true;
                break;
            }
        }

        for (SpeedUp speedUp : Items.speedUps) {
            int speedUpCol = (speedUp.x - 15) / tile;
            int speedUpRow = (speedUp.y - 75) / tile;
            if (speedUpCol == col && speedUpRow == row && speedUp.isVisible()) {
                speedUp.markForRemoval();
                itemDestroyed = true;
                break;
            }
        }

        // Check and mark extra bombs for removal
        if (!itemDestroyed) {
            for (ExtraBomb extraBomb : Items.extraBombs) {
                int extraBombCol = (extraBomb.x - 15) / tile;
                int extraBombRow = (extraBomb.y - 75) / tile;
                if (extraBombCol == col && extraBombRow == row && extraBomb.isVisible()) {
                    extraBomb.markForRemoval();
                    itemDestroyed = true;
                    break;
                }
            }
        }
        return itemDestroyed;
    }

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
