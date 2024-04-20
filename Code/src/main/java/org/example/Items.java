package org.example;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.HashSet;

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
    public static ArrayList<ExtraLife> extraLives = new ArrayList<ExtraLife>();
    public static int extraLife_items = 2;
    public static ArrayList<Coin> coins = new ArrayList<Coin>();
    public static int coinUnderRock_items = 20;
    public static ArrayList<Coin> coinsInEmptySpace = new ArrayList<Coin>();
    public static int coinInEmptySpace_items = 10;
    public static HashSet<Integer> chosenIndexes = new HashSet<>();


    public static boolean isDoorOrKeyAt(int col, int row) {
        //When the map modified, the conversion between (col,row) and (x,y)should be modified 
        return (Items.door.x == col*tile+15 && Items.door.y == row*tile+75) ||
                (Items.doorKey.x == col*tile+15 && Items.doorKey.y == row*tile+75);
    }

    public static boolean checkAndHandleBreakable(int col, int row) {
        boolean handled = false;
        ArrayList<BreakableRock> theRocks = new ArrayList<>();
        if(PVP){
            theRocks = BreakableRock.lessRocks;
        }else{
            theRocks = BreakableRock.rocks;
        }
        for (BreakableRock rock : theRocks) {
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
                if(rock.hideExtraLife && rock.hiddenExtraLife != null) {
                    rock.hiddenExtraLife.setVisible(true);
                    rock.hideExtraLife = false;
                }
                if(rock.hideCoin && rock.hiddenCoin != null) {
                    rock.hiddenCoin.setVisible(true);
                    rock.hideCoin = false;
                }
                break;
            }
        }
        return handled;
    }

    public static void checkAndHandleBreakableUltimate() {
        ArrayList<BreakableRock> theRocks = new ArrayList<>();
        theRocks = BreakableRock.rocks;
        for (BreakableRock rock : theRocks) {
            int rockCol = (rock.x() - 15) / tile;
            int rockRow = (rock.y() - 75) / tile;
            if ((ultimateFlames[rockCol][rockRow].showed || enemyNoHarmFlames[rockCol][rockRow].showed) && rock.rockExist) {
                // Mark the rock for removal instead of immediately removing it
                rock.markForRemoval();
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
                if(rock.hideExtraLife && rock.hiddenExtraLife != null) {
                    rock.hiddenExtraLife.setVisible(true);
                    rock.hideExtraLife = false;
                }
                if(rock.hideCoin && rock.hiddenCoin != null) {
                    rock.hiddenCoin.setVisible(true);
                    rock.hideCoin = false;
                }
                break;
            }
        }
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

        for (ExtraLife extraLife : extraLives) {
            int extraLifeCol = (extraLife.x - 15) / tile;
            int extraLifeRow = (extraLife.y - 75) / tile;
            if (extraLifeCol == col && extraLifeRow == row && extraLife.isVisible()) {
                extraLife.markForRemoval();
                itemDestroyed = true;
                break;
            }
        }

        for (Coin coin : coins) {
            int coinCol = (coin.x - 15) / tile;
            int coinRow = (coin.y - 75) / tile;
            if (coinCol == col && coinRow == row && coin.isVisible()) {
                coin.markForRemoval();
                itemDestroyed = true;
                break;
            }
        }

        for (Coin coinInEmptySpace : coinsInEmptySpace) {
            int coinInEmptySpaceCol = (coinInEmptySpace.x - 15) / tile;
            int coinInEmptySpaceRow = (coinInEmptySpace.y - 75) / tile;
            if (coinInEmptySpaceCol == col && coinInEmptySpaceRow == row && coinInEmptySpace.isVisible()) {
                coinInEmptySpace.markForRemoval();
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

        Iterator<ExtraLife> extraLifeIterator = extraLives.iterator();
        while (extraLifeIterator.hasNext()) {
            ExtraLife extraLife = extraLifeIterator.next();
            if (extraLife.isMarkedForRemoval()) {
                extraLifeIterator.remove();
            }
        }

        Iterator<Coin> coinIterator = coins.iterator();
        while (coinIterator.hasNext()) {
            Coin coin = coinIterator.next();
            if (coin.isMarkedForRemoval()) {
                coinIterator.remove();
            }
        }

        Iterator<Coin> coinInEmptySpaceIterator = coinsInEmptySpace.iterator();
        while (coinInEmptySpaceIterator.hasNext()) {
            Coin coinInEmptySpace = coinInEmptySpaceIterator.next();
            if (coinInEmptySpace.isMarkedForRemoval()) {
                coinInEmptySpaceIterator.remove();
            }
        }

        // 如果有其他類型的物件也需要被移除，繼續在這裡添加相應的邏輯
    }
}
